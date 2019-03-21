package org.zalando.intellij.swagger.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.RecursionGuard;
import com.intellij.openapi.util.RecursionManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

class JsonBuilderService {

  private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

  /*
   * We need to use a recursion guard because of circular references;
   * traversing them would result in a stack overflow.
   */
  private static final RecursionGuard recursionGuard =
      RecursionManager.createGuard("JsonBuilderService.buildWithResolvedReferences");

  /*
   * It is safe to use a cache for already resolved references.
   */
  private static final boolean memoize = true;

  JsonNode buildWithResolvedReferences(final JsonNode root, final VirtualFile containingFile) {

    if (root.isObject()) {

      Iterator<Map.Entry<String, JsonNode>> iterator = root.fields();

      while (iterator.hasNext()) {
        Map.Entry<String, JsonNode> current = iterator.next();

        final JsonNode ref = current.getValue().get("$ref");

        if (ref != null && ref.isTextual()) {
          final String text = ref.asText();
          if (isFileReference(text)) {
            final Pair<JsonNode, VirtualFile> referencedNode = refToJsonNode(text, containingFile);

            if (referencedNode != null && !referencedNode.first.isMissingNode()) {
              final JsonNode resolvedValue =
                  recursionGuard.doPreventingRecursion(
                      referencedNode,
                      memoize,
                      () ->
                          buildWithResolvedReferences(referencedNode.first, referencedNode.second));

              if (resolvedValue != null) {
                ((ObjectNode) root).replace(current.getKey(), resolvedValue);
              } else {
                ((ObjectNode) root)
                    .replace(
                        current.getKey(),
                        mapper.createObjectNode().put("$ref", "Circular reference!"));
              }
            }
          }
        } else {
          buildWithResolvedReferences(current.getValue(), containingFile);
        }
      }
    }

    return root;
  }

  private boolean isFileReference(final String text) {
    return text.endsWith(".json")
        || text.contains(".json#/")
        || text.endsWith(".yaml")
        || text.contains(".yaml#/")
        || text.endsWith(".yml")
        || text.contains(".yml#/");
  }

  private Pair<JsonNode, VirtualFile> refToJsonNode(
      final String ref, final VirtualFile containingFile) {

    final VirtualFile virtualFile = getVirtualFile(ref, containingFile);

    if (virtualFile == null) return null;

    final JsonNode tree;
    try {
      tree = mapper.readTree(new File(virtualFile.getPath()));
    } catch (IOException e) {
      return null;
    }

    final String s = StringUtils.substringAfter(ref, "#");

    return Pair.create(tree.at(s), virtualFile);
  }

  private VirtualFile getVirtualFile(final String ref, final VirtualFile containingFile) {
    final String relativePath = StringUtils.substringBefore(ref, "#");

    return containingFile.getParent().findFileByRelativePath(relativePath);
  }
}
