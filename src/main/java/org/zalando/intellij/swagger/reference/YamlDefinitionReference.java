package org.zalando.intellij.swagger.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLPsiElement;
import org.jetbrains.yaml.psi.YAMLQuotedText;
import org.zalando.intellij.swagger.completion.traversal.YamlTraversal;

import java.util.List;
import java.util.stream.Collectors;

public class YamlDefinitionReference extends PsiReferenceBase<PsiElement> {

    private final String targetDefinitionKey;
    private final YamlTraversal yamlTraversal;

    public YamlDefinitionReference(@NotNull final YAMLQuotedText selectedElement,
                                   @NotNull String targetDefinitionKey,
                                   @NotNull final YamlTraversal yamlTraversal) {
        super(selectedElement);
        this.targetDefinitionKey = targetDefinitionKey;
        this.yamlTraversal = yamlTraversal;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        final List<YAMLPsiElement> definitionsChildren = getDefinitionsChildren();
        return definitionsChildren.stream()
                .filter(yamlPsiElement -> targetDefinitionKey.equals(yamlPsiElement.getName()))
                .findFirst()
                .orElse(null);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

    private List<YAMLPsiElement> getDefinitionsChildren() {
        return yamlTraversal.getChildrenOf("definitions", getElement().getContainingFile()).stream()
                .filter(psiElement -> psiElement instanceof YAMLPsiElement)
                .map(YAMLPsiElement.class::cast)
                .collect(Collectors.toList());
    }

}
