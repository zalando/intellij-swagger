package org.zalando.intellij.swagger.index.swagger;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.file.SwaggerFileType;
import org.zalando.intellij.swagger.index.SpecIndexer;
import org.zalando.intellij.swagger.reference.SwaggerConstants;
import org.zalando.intellij.swagger.traversal.path.swagger.MainPathResolver;
import org.zalando.intellij.swagger.traversal.path.swagger.PathResolver;

class SwaggerDataIndexer extends SpecIndexer {

  static final String DELIMITER = "-";

  private final FileDetector fileDetector = new FileDetector();
  private final PathResolver pathResolver = new MainPathResolver();

  public SwaggerDataIndexer() {
    super(SwaggerFileType.MAIN);
  }

  @Override
  protected boolean shouldIndex(final PsiFile file) {
    return fileDetector.isMainSwaggerFile(file);
  }

  @Override
  protected String getFileType(final PsiElement psiElement, final String refValue) {
    if (pathResolver.isDefinitionRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue,
          SwaggerFileType.DEFINITIONS,
          SwaggerFileType.DEFINITIONS_MULTIPLE_IN_ROOT,
          SwaggerFileType.DEFINITIONS_MULTIPLE_NOT_IN_ROOT);
    } else if (pathResolver.isParameterRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue,
          SwaggerFileType.PARAMETERS,
          SwaggerFileType.PARAMETERS_MULTIPLE_IN_ROOT,
          SwaggerFileType.PARAMETERS_MULTIPLE_NOT_IN_ROOT);
    } else if (pathResolver.isResponseRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue,
          SwaggerFileType.RESPONSES,
          SwaggerFileType.RESPONSES_MULTIPLE_IN_ROOT,
          SwaggerFileType.RESPONSES_MULTIPLE_NOT_IN_ROOT);
    } else if (pathResolver.isPathRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue,
          SwaggerFileType.PATHS,
          SwaggerFileType.PATHS_MULTIPLE_IN_ROOT,
          SwaggerFileType.PATHS_MULTIPLE_NOT_IN_ROOT);
    }
    return SwaggerFileType.UNDEFINED.toString();
  }

  @NotNull
  private String getFileTypeFromRefValue(
      final String refValue,
      final SwaggerFileType singleDefinitionInFile,
      final SwaggerFileType multipleDefinitionsInRootFile,
      final SwaggerFileType multipleDefinitionsNotInRootFile) {
    if (refValue.contains(SwaggerConstants.REFERENCE_PREFIX)) {
      final String definitionPath =
          org.apache.commons.lang.StringUtils.substringAfterLast(refValue, SwaggerConstants.HASH);
      int slashCount =
          org.apache.commons.lang.StringUtils.countMatches(definitionPath, SwaggerConstants.SLASH);

      return slashCount == 1
          ? multipleDefinitionsInRootFile.toString()
          : multipleDefinitionsNotInRootFile.toString();
    }

    return singleDefinitionInFile.toString();
  }
}
