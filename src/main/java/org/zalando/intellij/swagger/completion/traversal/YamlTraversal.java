package org.zalando.intellij.swagger.completion.traversal;

import com.intellij.psi.PsiElement;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;
import org.jetbrains.yaml.psi.YAMLSequenceItem;

import java.util.Optional;

public class YamlTraversal implements Traversal {

    @Override
    public boolean isRoot(final PsiElement psiElement) {
        return psiElement.getParent().getParent() instanceof YAMLFile ||
                psiElement.getParent().getParent().getParent().getParent() instanceof YAMLFile;
    }

    @Override
    public boolean isKey(final PsiElement psiElement) {
        return Optional.ofNullable(psiElement)
                .map(PsiElement::getParent)
                .map(PsiElement::getParent)
                .filter(element -> element instanceof YAMLMapping || element instanceof YAMLKeyValue)
                .isPresent();
    }

    @Override
    public boolean isInfo(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 1))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("info"))
                .isPresent();
    }

    @Override
    public boolean isContact(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 1))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("contact"))
                .isPresent();
    }

    @Override
    public boolean isLicense(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 1))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("license"))
                .isPresent();
    }

    @Override
    public boolean isPath(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 2))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("paths"))
                .isPresent();
    }

    @Override
    public boolean isOperation(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 3))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("paths"))
                .isPresent();
    }

    @Override
    public boolean isExternalDocs(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 1))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("externalDocs"))
                .isPresent();
    }

    @Override
    public boolean isParameters(final PsiElement psiElement) {
        final boolean insideParameters = Optional.ofNullable(getNthYamlKeyValue(psiElement, 1))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("parameters"))
                .isPresent();
        return insideParameters && hasSequenceItemAsParent(psiElement);
    }

    private boolean hasSequenceItemAsParent(final PsiElement psiElement) {
        return psiElement != null &&
                (psiElement instanceof YAMLSequenceItem || hasSequenceItemAsParent(psiElement.getParent()));
    }

    @Override
    public boolean isItems(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 1))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("items"))
                .isPresent();
    }

    @Override
    public boolean isResponses(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 1))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("responses"))
                .isPresent();
    }

    @Override
    public boolean isResponse(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 2))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("responses"))
                .isPresent();
    }

    @Override
    public boolean isHeader(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 2))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("headers"))
                .isPresent();
    }

    @Override
    public boolean isTag(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 1))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("tags"))
                .isPresent();
    }

    @Override
    public boolean isSecurityDefinition(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 2))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("securityDefinitions"))
                .isPresent();
    }

    @Override
    public boolean isSchema(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 1))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("schema"))
                .isPresent();
    }

    @Override
    public boolean isXml(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 1))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("xml"))
                .isPresent();
    }

    @Override
    public boolean isDefinitions(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 2))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("definitions"))
                .isPresent();
    }

    @Override
    public boolean isParameterDefinition(final PsiElement psiElement) {
        return Optional.ofNullable(getNthYamlKeyValue(psiElement, 2))
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("parameters"))
                .isPresent();
    }

    private YAMLKeyValue getNthYamlKeyValue(final PsiElement psiElement, int nth) {
        if (psiElement == null) {
            return null;
        } else if (psiElement instanceof YAMLKeyValue) {
            if (nth == 1) {
                return (YAMLKeyValue) psiElement;
            } else {
                nth--;
            }
        }

        return getNthYamlKeyValue(psiElement.getParent(), nth);
    }

}
