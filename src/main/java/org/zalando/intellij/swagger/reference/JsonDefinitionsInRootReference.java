package org.zalando.intellij.swagger.reference;

import com.intellij.json.psi.JsonLiteral;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

import java.util.Optional;

import static org.zalando.intellij.swagger.reference.SwaggerConstants.REFERENCE_PREFIX;

public class JsonDefinitionsInRootReference extends PsiReferenceBase<PsiElement> {

    private final String originalRefValue;
    private final JsonTraversal jsonTraversal;

    public JsonDefinitionsInRootReference(@NotNull final JsonLiteral selectedElement,
                                          @NotNull final String originalRefValue,
                                          @NotNull final JsonTraversal jsonTraversal) {
        super(selectedElement);
        this.originalRefValue = originalRefValue;
        this.jsonTraversal = jsonTraversal;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        final PsiFile referencedFile = getReferencedFile();

        if (referencedFile == null) {
            return null;
        }

        return jsonTraversal.getRootChildByName(extractDefinitionName(), referencedFile).orElse(null);
    }

    private PsiFile getReferencedFile() {
        final String filePath = getFilePath();
        final VirtualFile baseDir = getElement().getContainingFile().getVirtualFile().getParent();
        return Optional.ofNullable(baseDir.findFileByRelativePath(filePath))
                .map(f -> PsiManager.getInstance(getElement().getProject()).findFile(f))
                .orElse(null);
    }

    private String getFilePath() {
        return StringUtils.substringBefore(originalRefValue, REFERENCE_PREFIX);
    }

    private String extractDefinitionName() {
        return StringUtils.substringAfterLast(originalRefValue, REFERENCE_PREFIX);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        final String filePath = getFilePath();
        return super.handleElementRename(filePath + REFERENCE_PREFIX + newElementName);
    }

}
