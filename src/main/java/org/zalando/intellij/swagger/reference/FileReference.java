package org.zalando.intellij.swagger.reference;

import static org.zalando.intellij.swagger.reference.SwaggerConstants.SLASH;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zalando.intellij.swagger.traversal.path.PathExpressionUtil;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

public class FileReference extends PsiReferenceBase<PsiElement> {

  private final String originalRefValue;

  public FileReference(@NotNull final PsiElement element, @NotNull final String originalRefValue) {
    super(element);
    this.originalRefValue = originalRefValue;
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    final String relativePath = StringUtils.substringBefore(originalRefValue, "#/");

    if (relativePath.equals(originalRefValue)) {
      return resolveExactFileReference(relativePath);
    }

    return resolveFileReferenceWithSubPath(relativePath);
  }

  @Nullable
  private PsiElement resolveFileReferenceWithSubPath(String relativePath) {
    String textAfterRelativePath = StringUtils.substringAfter(originalRefValue, relativePath);
    final String pathExpression =
        Arrays.stream(textAfterRelativePath.substring(2).split("/"))
            .map(PathExpressionUtil::escape)
            .collect(Collectors.joining(".", "$.", ""));

    return getReferencedFile(relativePath)
        .flatMap(referencedFile -> new PathFinder().findByPathFrom(pathExpression, referencedFile))
        .orElse(null);
  }

  @Nullable
  private PsiElement resolveExactFileReference(String relativePath) {
    return getReferencedFile(relativePath)
        .flatMap(referencedFile -> new PathFinder().findByPathFrom("$", referencedFile))
        .orElse(null);
  }

  private Optional<PsiFile> getReferencedFile(final String relativePath) {

    final Optional<VirtualFile> baseDir =
        Optional.ofNullable(getElement())
            .map(PsiElement::getContainingFile)
            .map(PsiFile::getVirtualFile)
            .map(VirtualFile::getParent);

    final PsiManager psiManager = PsiManager.getInstance(getElement().getProject());

    return baseDir.map(dir -> dir.findFileByRelativePath(relativePath)).map(psiManager::findFile);
  }

  @NotNull
  @Override
  public Object[] getVariants() {
    return new Object[0];
  }

  @Override
  public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
    final String referencePrefix = StringUtils.substringBeforeLast(originalRefValue, "/");

    if (referencePrefix.equals(originalRefValue)) {
      return super.handleElementRename(newElementName);
    }
    return super.handleElementRename(referencePrefix + SLASH + newElementName);
  }

  @Override
  public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
    return null;
  }
}
