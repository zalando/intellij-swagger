package org.zalando.intellij.swagger.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLPsiElement;
import org.jetbrains.yaml.psi.YAMLQuotedText;
import org.zalando.intellij.swagger.reference.element.SwaggerYamlElementGenerator;
import org.zalando.intellij.swagger.traversal.YamlTraversal;

import java.util.List;
import java.util.stream.Collectors;

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
        return yamlTraversal.getChildrenOfDefinition("parameters", getElement().getContainingFile()).stream()
                .filter(psiElement -> psiElement instanceof YAMLPsiElement)
                .map(YAMLPsiElement.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        final PsiElement newValue = SwaggerYamlElementGenerator.createSingleQuotedValue(getElement().getProject(), "#/parameters/" + newElementName);
        return getElement().replace(newValue);
    }
}
