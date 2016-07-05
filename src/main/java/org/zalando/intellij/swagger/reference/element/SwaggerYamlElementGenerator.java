package org.zalando.intellij.swagger.reference.element;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.yaml.YAMLElementGenerator;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLPsiElement;

public class SwaggerYamlElementGenerator {

    public static PsiElement createSingleQuotedValue(final Project project, final String value) {
        final PsiFile file = new YAMLElementGenerator(project).createDummyYamlWithText("key: '" + value + "'");
        final YAMLPsiElement yamlPsiElement = ((YAMLDocument) file.getFirstChild()).getYAMLElements().get(0);
        return ((YAMLKeyValue) yamlPsiElement.getYAMLElements().get(0)).getValue();
    }
}
