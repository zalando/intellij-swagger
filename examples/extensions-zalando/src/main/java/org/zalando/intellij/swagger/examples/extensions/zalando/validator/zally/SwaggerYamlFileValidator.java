package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

import java.util.Optional;

public class SwaggerYamlFileValidator extends ZallyYamlFileValidator {

    private final FileDetector fileDetector = new FileDetector();
    private final PathFinder pathFinder = new PathFinder();

    public SwaggerYamlFileValidator() {
        this(ServiceManager.getService(ZallyService.class));
    }

    public SwaggerYamlFileValidator(ZallyService zallyService) {
        super(zallyService);
    }

    @Override
    public ProblemDescriptor[] checkFile(@NotNull PsiFile file, @NotNull InspectionManager manager, boolean isOnTheFly) {
        return checkViolations(file, manager, isOnTheFly);
    }

    public boolean supportsFile(final PsiFile file) {
        return fileDetector.isMainSwaggerYamlFile(file);
    }

    @Override
    Optional<PsiElement> getRootElement(final PsiFile file) {
        return pathFinder.findByPathFrom("$.swagger", file);
    }

}
