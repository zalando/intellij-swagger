package org.zalando.intellij.swagger.index.openapi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.file.OpenApiFileType;
import org.zalando.intellij.swagger.index.SpecIndexer;
import org.zalando.intellij.swagger.reference.OpenApiConstants;
import org.zalando.intellij.swagger.traversal.path.openapi.MainPathResolver;

class OpenApiDataIndexer extends SpecIndexer {

  private final FileDetector fileDetector = new FileDetector();
  private final MainPathResolver mainPathResolver = new MainPathResolver();

  public OpenApiDataIndexer() {
    super(OpenApiFileType.MAIN);
  }

  @Override
  public boolean shouldIndex(final PsiFile file) {
    return fileDetector.isMainOpenApiFile(file);
  }

  public String getFileType(final PsiElement psiElement, final String refValue) {
    if (mainPathResolver.isSchemaRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_SCHEMA, OpenApiFileType.MULTIPLE_SCHEMAS);
    } else if (mainPathResolver.isResponseRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_RESPONSE, OpenApiFileType.MULTIPLE_RESPONSES);
    } else if (mainPathResolver.isParameterRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_PARAMETER, OpenApiFileType.MULTIPLE_PARAMETERS);
    } else if (mainPathResolver.isExampleRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_EXAMPLE, OpenApiFileType.MULTIPLE_EXAMPLES);
    } else if (mainPathResolver.isRequestBodyRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_REQUEST_BODY, OpenApiFileType.MULTIPLE_REQUEST_BODIES);
    } else if (mainPathResolver.isHeaderRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_HEADER, OpenApiFileType.MULTIPLE_HEADERS);
    } else if (mainPathResolver.isLinkRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_LINK, OpenApiFileType.MULTIPLE_LINKS);
    } else if (mainPathResolver.isCallbackRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_CALLBACK, OpenApiFileType.MULTIPLE_CALLBACKS);
    }

    return OpenApiFileType.UNDEFINED.toString();
  }

  private String getFileTypeFromRefValue(
      final String refValue,
      final OpenApiFileType singleDefinitionInFile,
      final OpenApiFileType multipleDefinitionsInFile) {
    return refValue.contains(OpenApiConstants.HASH + OpenApiConstants.SLASH)
        ? multipleDefinitionsInFile.toString()
        : singleDefinitionInFile.toString();
  }
}
