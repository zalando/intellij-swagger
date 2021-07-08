package org.zalando.intellij.swagger.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.RecursionGuard;
import com.intellij.openapi.util.RecursionManager;
import com.intellij.openapi.vfs.VirtualFile;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import org.apache.commons.lang.StringUtils;
import org.zalando.intellij.swagger.reference.SwaggerConstants;

class JsonBuilderService {

  private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

  /*
   * We need to use a recursion guard because of circular references;
   * traversing them would result in a stack overflow.
   */
  private static final RecursionGuard recursionGuard =
      RecursionManager.createGuard("JsonBuilderService.buildRecursive");

  // It is safe to use a cache for already resolved references.
  private static final boolean memoize = true;

  private static final String CIRCULAR_REFERENCE = "Circular reference!";

  JsonNode buildFromSpec(final JsonNode root, final VirtualFile specFile) {
    return buildRecursive(root, specFile, specFile);
  }

  private JsonNode buildRecursive(
      final JsonNode root, final VirtualFile containingFile, final VirtualFile specFile) {
    if (root.isObject()) {
      handleObjectNode((ObjectNode) root, containingFile, specFile);
    } else if (root.isArray()) {
      handleArrayNode((ArrayNode) root, containingFile, specFile);
    }

    return root;
  }

  private void handleObjectNode(
      final ObjectNode root, final VirtualFile containingFile, final VirtualFile specFile) {
    Iterator<Map.Entry<String, JsonNode>> iterator = root.fields();

    while (iterator.hasNext()) {
      final Map.Entry<String, JsonNode> current = iterator.next();
      final JsonNode ref = current.getValue().get(SwaggerConstants.REF_KEY);

      if (ref != null && ref.isTextual()) {
        consumeRef(
            (TextNode) ref,
            containingFile,
            new ObjectNodeConsumer(root, current.getKey()),
            specFile);
      } else {
        buildRecursive(current.getValue(), containingFile, specFile);
      }
    }
  }

  private void handleArrayNode(
      final ArrayNode root, final VirtualFile containingFile, final VirtualFile specFile) {
    final Iterator<JsonNode> iterator = root.iterator();
    int index = 0;

    while (iterator.hasNext()) {
      final JsonNode current = iterator.next();
      final JsonNode ref = current.get(SwaggerConstants.REF_KEY);

      if (ref != null && ref.isTextual()) {
        consumeRef((TextNode) ref, containingFile, new ArrayNodeConsumer(root, index), specFile);
      }
      index++;
    }
  }

  private void consumeRef(
      final TextNode ref,
      final VirtualFile containingFile,
      final Consumer<ResolvedRef> refConsumer,
      final VirtualFile specFile) {
    if (SwaggerFilesUtils.isFileReference(ref.asText())) {
      consumeFileReference(ref, containingFile, refConsumer, specFile);
    } else {
      consumeLocalReference(ref, containingFile, refConsumer, specFile);
    }
  }

  private void consumeFileReference(
      final TextNode ref,
      final VirtualFile containingFile,
      Consumer<ResolvedRef> refConsumer,
      final VirtualFile specFile) {
    final ResolvedRef resolvedRef = resolveFileRef(ref.asText(), containingFile);

    consumeResolvedRef(refConsumer, specFile, resolvedRef);
  }

  private void consumeLocalReference(
      final TextNode ref,
      final VirtualFile containingFile,
      Consumer<ResolvedRef> refConsumer,
      final VirtualFile specFile) {

    // We only need to inline references if we are in a referenced file.
    if (!containingFile.equals(specFile)) {
      final String jsonPointer = StringUtils.substringAfter(ref.asText(), "#");

      final ResolvedRef resolvedRef = resolveJsonPointer(jsonPointer, containingFile);

      consumeResolvedRef(refConsumer, specFile, resolvedRef);
    }
  }

  private void consumeResolvedRef(
      final Consumer<ResolvedRef> refConsumer,
      final VirtualFile specFile,
      final ResolvedRef resolvedRef) {
    if (resolvedRef.isValid()) {
      final JsonNode node =
          (JsonNode)
              recursionGuard.doPreventingRecursion(
                  Pair.create(resolvedRef.getJsonNode(), resolvedRef.getContainingFile()),
                  memoize,
                  (Computable<JsonNode>)
                      () ->
                          buildRecursive(
                              resolvedRef.getJsonNode(),
                              resolvedRef.getContainingFile(),
                              specFile));

      // "doPreventingRecursion" returns null in case of a circular reference
      if (node != null) {
        refConsumer.accept(resolvedRef);
      } else {
        refConsumer.accept(CircularRef.getInstance());
      }

    } else {
      refConsumer.accept(resolvedRef);
    }
  }

  private ResolvedRef resolveFileRef(final String ref, final VirtualFile containingFile) {

    final VirtualFile referencedFile = getReferencedFile(ref, containingFile);

    if (referencedFile == null) return MissingElementRef.getInstance();

    final String jsonPointer = StringUtils.substringAfter(ref, "#");

    return resolveJsonPointer(jsonPointer, referencedFile);
  }

  private ResolvedRef resolveJsonPointer(
      final String jsonPointer, final VirtualFile containingFile) {
    final JsonNode tree;
    try {
      tree = mapper.readTree(new File(containingFile.getPath()));
    } catch (IOException e) {
      return MissingElementRef.getInstance();
    }

    final JsonNode at = tree.at(jsonPointer);

    if (at.isMissingNode()) {
      return MissingElementRef.getInstance();
    }

    return new ValidRef(at, containingFile);
  }

  private VirtualFile getReferencedFile(final String ref, final VirtualFile containingFile) {
    final String relativePath = StringUtils.substringBefore(ref, "#");

    return containingFile.getParent().findFileByRelativePath(relativePath);
  }

  private static class ResolvedRef {

    enum ResultType {
      VALID,
      CIRCULAR,
      MISSING_ELEMENT
    }

    private final ResultType resultType;

    ResolvedRef(final ResultType resultType) {
      this.resultType = resultType;
    }

    boolean isValid() {
      return resultType == ResultType.VALID;
    }

    boolean isCircular() {
      return resultType == ResultType.CIRCULAR;
    }

    JsonNode getJsonNode() {
      throw new UnsupportedOperationException("Invalid $ref");
    }

    VirtualFile getContainingFile() {
      throw new UnsupportedOperationException("Invalid $ref");
    }
  }

  private static class ValidRef extends ResolvedRef {

    private final JsonNode jsonNode;
    private final VirtualFile containingFile;

    private ValidRef(final JsonNode jsonNode, final VirtualFile containingFile) {
      super(ResultType.VALID);
      this.jsonNode = jsonNode;
      this.containingFile = containingFile;
    }

    boolean isValid() {
      return true;
    }

    @Override
    public JsonNode getJsonNode() {
      return jsonNode;
    }

    @Override
    public VirtualFile getContainingFile() {
      return containingFile;
    }
  }

  private static class MissingElementRef extends ResolvedRef {

    private static final MissingElementRef instance = new MissingElementRef();

    private MissingElementRef() {
      super(ResultType.MISSING_ELEMENT);
    }

    private static MissingElementRef getInstance() {
      return instance;
    }
  }

  private static class CircularRef extends ResolvedRef {

    private static final CircularRef instance = new CircularRef();

    private CircularRef() {
      super(ResultType.CIRCULAR);
    }

    private static CircularRef getInstance() {
      return instance;
    }
  }

  private static class ArrayNodeConsumer implements Consumer<ResolvedRef> {

    private final ArrayNode node;
    private final int index;

    private ArrayNodeConsumer(final ArrayNode node, final int index) {
      this.node = node;
      this.index = index;
    }

    public void accept(final ResolvedRef resolvedRef) {
      if (resolvedRef.isValid()) {
        node.set(index, resolvedRef.getJsonNode());
      } else if (resolvedRef.isCircular()) {
        node.set(
            index, mapper.createObjectNode().put(SwaggerConstants.REF_KEY, CIRCULAR_REFERENCE));
      }
    }
  }

  private static class ObjectNodeConsumer implements Consumer<ResolvedRef> {
    private final ObjectNode node;
    private final String key;

    private ObjectNodeConsumer(final ObjectNode node, final String key) {
      this.node = node;
      this.key = key;
    }

    public void accept(final ResolvedRef resolvedRef) {
      if (resolvedRef.isValid()) {
        node.replace(key, resolvedRef.getJsonNode());
      } else if (resolvedRef.isCircular()) {
        node.replace(
            key, mapper.createObjectNode().put(SwaggerConstants.REF_KEY, CIRCULAR_REFERENCE));
      }
    }
  }
}
