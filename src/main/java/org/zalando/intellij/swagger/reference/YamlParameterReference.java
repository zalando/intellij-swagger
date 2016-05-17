package org.zalando.intellij.swagger.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLPsiElement;
import org.jetbrains.yaml.psi.YAMLQuotedText;
import org.zalando.intellij.swagger.completion.traversal.YamlTraversal;

import java.util.List;

public class YamlParameterReference extends PsiReferenceBase<PsiElement> {

    private final String targetParameterKey;
    private final YamlTraversal yamlTraversal;

    public YamlParameterReference(@NotNull final YAMLQuotedText selectedElement,
                                  @NotNull String targetParameterKey,
                                  @NotNull final YamlTraversal yamlTraversal) {
        super(selectedElement);
        this.targetParameterKey = targetParameterKey;
        this.yamlTraversal = yamlTraversal;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        final List<YAMLPsiElement> parametersChildren = getParametersChildren();
        return parametersChildren.stream()
                .filter(yamlPsiElement -> targetParameterKey.equals(yamlPsiElement.getName()))
                .findFirst()
                .orElse(null);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

    private List<YAMLPsiElement> getParametersChildren() {
        return yamlTraversal.getChildPropertiesByName(getYamlDocument(getElement())
                .getYAMLElements().get(0), "parameters");
    }

    private YAMLDocument getYamlDocument(final PsiElement psiElement) {
        if (psiElement == null) {
            return null;
        } else if (psiElement instanceof YAMLDocument) {
            return (YAMLDocument) psiElement;
        }

        return getYamlDocument(psiElement.getParent());
    }

}
