package org.zalando.intellij.swagger.reference;

import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLValue;
import org.zalando.intellij.swagger.traversal.JsonTraversal;
import org.zalando.intellij.swagger.traversal.YamlTraversal;

public class YamlTagReference extends PsiReferenceBase<PsiElement> {

    private final String tagName;
    private final YamlTraversal yamlTraversal;

    public YamlTagReference(@NotNull final YAMLValue selectedElement,
                            @NotNull final String tagName,
                            @NotNull final YamlTraversal yamlTraversal) {
        super(selectedElement);
        this.tagName = tagName;
        this.yamlTraversal = yamlTraversal;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        return yamlTraversal.getTags(getElement().getContainingFile()).stream()
                .filter(el -> el instanceof YAMLKeyValue)
                .map(YAMLKeyValue.class::cast)
                .filter(yamlKeyValue -> yamlKeyValue.getValue() != null)
                .map(YAMLKeyValue::getValue)
                .filter(yamlValue -> tagName.equals(yamlValue.getText()))
                .findFirst()
                .orElse(null);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

}
