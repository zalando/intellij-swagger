package org.zalando.intellij.swagger.reference;

import com.intellij.json.psi.JsonLiteral;
import com.intellij.json.psi.JsonProperty;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

public class JsonLocalDefinitionReference extends PsiReferenceBase<PsiElement> {

    private final String originalRefValue;
    private final JsonTraversal jsonTraversal;

    public JsonLocalDefinitionReference(@NotNull final JsonLiteral selectedElement,
                                        @NotNull final String originalRefValue,
                                        @NotNull final JsonTraversal jsonTraversal) {
        super(selectedElement);
        this.originalRefValue = originalRefValue;
        this.jsonTraversal = jsonTraversal;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        final String definitionName = extractDefinitionName();

        return jsonTraversal
                .getChildrenOfRootProperty("definitions", getElement().getContainingFile()).stream()
                .filter(psiElement -> psiElement instanceof JsonProperty)
                .map(JsonProperty.class::cast)
                .filter(jsonProperty -> definitionName.equals(jsonProperty.getName()))
                .findFirst()
                .orElse(null);
    }

    private String extractDefinitionName() {
        return StringUtils.substringAfterLast(originalRefValue, "/");
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return super.handleElementRename("#/definitions/" + newElementName);
    }

}
