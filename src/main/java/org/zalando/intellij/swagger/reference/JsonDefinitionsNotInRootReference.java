package org.zalando.intellij.swagger.reference;

import com.intellij.json.psi.JsonLiteral;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

import java.util.Optional;

import static org.zalando.intellij.swagger.reference.SwaggerConstants.REFERENCE_PREFIX;
import static org.zalando.intellij.swagger.reference.SwaggerConstants.SLASH;

public class JsonDefinitionsNotInRootReference extends PsiReferenceBase<PsiElement> {

    private final String originalRefValue;
    private final JsonTraversal jsonTraversal;

    public JsonDefinitionsNotInRootReference(@NotNull final JsonLiteral selectedElement,
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

        final String rootElementName = getRootElementName();
        final String definitionName = getDefinitionName();

        return jsonTraversal.getChildrenOfRootProperty(rootElementName, referencedFile).stream()
                .filter(el -> el instanceof PsiNamedElement)
                .map(PsiNamedElement.class::cast)
                .filter(namedElement -> definitionName.equals(namedElement.getName()))
                .findFirst()
                .orElse(null);
    }

    private PsiFile getReferencedFile() {
        final String filePath = getFilePath();
        final VirtualFile baseDir = getElement().getContainingFile().getVirtualFile().getParent();
        return Optional.ofNullable(baseDir.findFileByRelativePath(filePath))
                .map(f -> PsiManager.getInstance(getElement().getProject()).findFile(f))
                .orElse(null);
    }

    private String getRootElementName() {
        final String definitionPath = StringUtils.substringAfter(originalRefValue, REFERENCE_PREFIX);
        return StringUtils.substringBefore(definitionPath, SLASH);
    }

    private String getDefinitionName() {
        return StringUtils.substringAfterLast(originalRefValue, SLASH);
    }

    private String getFilePath() {
        return StringUtils.substringBefore(originalRefValue, REFERENCE_PREFIX);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        final String filePath = getFilePath();
        final String rootElementName = getRootElementName();

        return super.handleElementRename(filePath + REFERENCE_PREFIX + rootElementName + SLASH + newElementName);
    }

}
