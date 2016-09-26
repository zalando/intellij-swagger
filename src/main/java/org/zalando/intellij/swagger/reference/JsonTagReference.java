package org.zalando.intellij.swagger.reference;

import com.intellij.json.psi.JsonLiteral;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

public class JsonTagReference extends PsiReferenceBase<PsiElement> {

    private final String tagName;
    private final JsonTraversal jsonTraversal;

    public JsonTagReference(@NotNull final JsonValue selectedElement,
                            @NotNull final String tagName,
                            @NotNull final JsonTraversal jsonTraversal) {
        super(selectedElement);
        this.tagName = tagName;
        this.jsonTraversal = jsonTraversal;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        return jsonTraversal.getTags(getElement().getContainingFile()).stream()
                .filter(el -> el instanceof JsonProperty)
                .map(JsonProperty.class::cast)
                .filter(jsonProperty -> jsonProperty.getValue() != null)
                .map(JsonProperty::getValue)
                .filter(jsonValue -> tagName.equals(jsonValue.getText()))
                .findFirst()
                .orElse(null);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

}
