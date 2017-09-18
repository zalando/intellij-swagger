package org.zalando.intellij.swagger.reference.swagger;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

import java.util.Optional;

import static org.zalando.intellij.swagger.reference.swagger.SwaggerConstants.REFERENCE_PREFIX;

public class DefinitionsInRootReference extends PsiReferenceBase<PsiElement> {

    private final String originalRefValue;

    public DefinitionsInRootReference(@NotNull final PsiElement element,
                                      @NotNull final String originalRefValue) {
        super(element);
        this.originalRefValue = originalRefValue;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        final PsiFile referencedFile = getReferencedFile();

        if (referencedFile == null) {
            return null;
        }

        final String referencedValue = ReferenceValueExtractor.extractValue(originalRefValue);
        final String pathExpression = String.format("$.%s", referencedValue);

        return new PathFinder().findByPathFrom(pathExpression, referencedFile)
                .orElse(null);
    }

    private PsiFile getReferencedFile() {
        final String filePath = ReferenceValueExtractor.extractFilePath(originalRefValue);
        final VirtualFile baseDir = getElement().getContainingFile().getVirtualFile().getParent();

        return Optional.ofNullable(baseDir.findFileByRelativePath(filePath))
                .map(f -> PsiManager.getInstance(getElement().getProject()).findFile(f))
                .orElse(null);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        final String filePath = ReferenceValueExtractor.extractFilePath(originalRefValue);
        return super.handleElementRename(filePath + REFERENCE_PREFIX + newElementName);
    }

}
