package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally;

import com.google.common.collect.Lists;
import com.intellij.codeInspection.*;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.ZallySettings;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.LintingResponse;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.Violation;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

import java.util.List;
import java.util.Optional;

public class YamlFileValidator extends LocalInspectionTool {

    private final static Logger LOG = Logger.getInstance(YamlFileValidator.class);

    private final PathFinder pathFinder = new PathFinder();
    private final FileDetector fileDetector = new FileDetector();
    private final ZallyService zallyService;

    public YamlFileValidator() {
        this(ServiceManager.getService(ZallyService.class));
    }

    public YamlFileValidator(ZallyService zallyService) {
        this.zallyService = zallyService;
    }

    @Override
    public ProblemDescriptor[] checkFile(@NotNull PsiFile file, @NotNull InspectionManager manager, boolean isOnTheFly) {
        if (shouldLint(file)) {
            final LintingResponse lintingResponse = lint(file);
            return createViolations(manager, isOnTheFly, lintingResponse, file);
        }

        return ProblemDescriptor.EMPTY_ARRAY;
    }

    private boolean shouldLint(final PsiFile file) {
        return hasZallyUrl() && isSwaggerFile(file);
    }

    private boolean isSwaggerFile(PsiFile file) {
        return fileDetector.isMainSwaggerYamlFile(file);
    }

    private boolean hasZallyUrl() {
        final ZallySettings zallySettings = ServiceManager.getService(ZallySettings.class);

        return zallySettings.zallyUrl != null && !zallySettings.zallyUrl.isEmpty();
    }

    private LintingResponse lint(final PsiFile file) {
        try {
            return zallyService.lint(file.getText());
        } catch (final Exception e) {
            LOG.error("Could not lint specification with Zally", e);

            throw new RuntimeException(e);
        }
    }

    @NotNull
    private ProblemDescriptor[] createViolations(final InspectionManager manager,
                                                 final boolean isOnTheFly,
                                                 final LintingResponse lintingResponse,
                                                 final PsiFile file) {
        final List<ProblemDescriptor> problems = Lists.newArrayList();

        final Optional<PsiElement> root = pathFinder.findByPathFrom("$.swagger", file);

        root.filter(el -> el instanceof YAMLKeyValue)
                .map(YAMLKeyValue.class::cast)
                .ifPresent((psiElement) -> {
                    final PsiElement key = psiElement.getKey();

                    if (key != null) {
                        for (Violation violation : lintingResponse.getViolations()) {
                            String descriptionTemplate = "[" + violation.getViolationType() + "] " + violation.getTitle();

                            problems.add(manager.createProblemDescriptor(psiElement.getKey(), descriptionTemplate,
                                    isOnTheFly, LocalQuickFix.EMPTY_ARRAY, ProblemHighlightType.GENERIC_ERROR));
                        }
                    }
                });

        return problems.toArray(ProblemDescriptor.EMPTY_ARRAY);
    }
}
