package org.zalando.intellij.swagger.reference.element;

import com.intellij.json.psi.JsonElementGenerator;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

public class SwaggerJsonElementGenerator {

    public static PsiElement createEmptyObject(final Project project, final String keyName) {
        return new JsonElementGenerator(project).createProperty(keyName, "{}");
    }
}
