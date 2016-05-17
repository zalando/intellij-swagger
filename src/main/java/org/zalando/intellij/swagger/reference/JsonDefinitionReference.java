package org.zalando.intellij.swagger.reference;

import com.intellij.json.psi.JsonLiteral;
import com.intellij.json.psi.JsonProperty;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zalando.intellij.swagger.completion.traversal.JsonTraversal;

import java.util.List;

public class JsonDefinitionReference extends PsiReferenceBase<PsiElement> {

    private final String targetDefinitionKey;
    private final JsonTraversal jsonTraversal;

    public JsonDefinitionReference(@NotNull final JsonLiteral selectedElement,
                                   @NotNull final String targetDefinitionKey,
                                   @NotNull final JsonTraversal jsonTraversal) {
        super(selectedElement);
        this.targetDefinitionKey = targetDefinitionKey;
        this.jsonTraversal = jsonTraversal;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        return getDefinitionsChildren().stream()
                .filter(jsonProperty -> targetDefinitionKey.equals(jsonProperty.getName()))
                .findFirst()
                .orElse(null);
    }

    private List<JsonProperty> getDefinitionsChildren() {
        return jsonTraversal.getChildPropertiesByName(
                getElement().getContainingFile().getChildren()[0], "definitions");
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }
}
