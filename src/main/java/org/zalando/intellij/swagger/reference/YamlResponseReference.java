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

public class YamlResponseReference extends PsiReferenceBase<PsiElement> {

    private final String targetResponseKey;
    private final YamlTraversal yamlTraversal;

    public YamlResponseReference(@NotNull final YAMLQuotedText selectedElement,
                                 @NotNull String targetResponseKey,
                                 @NotNull final YamlTraversal yamlTraversal) {
        super(selectedElement);
        this.targetResponseKey = targetResponseKey;
        this.yamlTraversal = yamlTraversal;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        final List<YAMLPsiElement> parametersChildren = getResponsesChildren();
        return parametersChildren.stream()
                .filter(yamlPsiElement -> targetResponseKey.equals(yamlPsiElement.getName()))
                .findFirst()
                .orElse(null);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

    private List<YAMLPsiElement> getResponsesChildren() {
        return yamlTraversal.getChildrenOfDefinition("responses", getElement().getContainingFile()).stream()
                .filter(psiElement -> psiElement instanceof YAMLPsiElement)
                .map(YAMLPsiElement.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        final PsiElement newValue = SwaggerYamlElementGenerator.createSingleQuotedValue(getElement().getProject(), "#/responses/" + newElementName);
        return getElement().replace(newValue);
    }
}
