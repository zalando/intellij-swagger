package org.zalando.intellij.swagger.reference;

import com.intellij.json.psi.JsonLiteral;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class JsonFileReference extends PsiReferenceBase<PsiElement> {

    private final String filePath;

    public JsonFileReference(@NotNull final JsonLiteral selectedElement,
                             @NotNull final String filePath) {
        super(selectedElement);
        this.filePath = filePath;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        final VirtualFile baseDir = getElement().getContainingFile().getVirtualFile().getParent();
        final Optional<VirtualFile> file = Optional.ofNullable(baseDir.findFileByRelativePath(filePath));

        return file.map(this::getPsiFile).orElse(null);
    }

    private PsiFile getPsiFile(VirtualFile file) {
        return PsiManager.getInstance(getElement().getProject()).findFile(file);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        final PsiElement resolved = resolve();

        if (resolved == null) {
            return null;
        }

        final String originalFileName = resolved.getContainingFile().getName();
        final String newFileNameWithPath = filePath.replace(originalFileName, newElementName);

        return super.handleElementRename(newFileNameWithPath);
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        return null;
    }
}
