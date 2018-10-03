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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class ZallyYamlFileValidator extends LocalInspectionTool {

    private final static Logger LOG = Logger.getInstance(ZallyYamlFileValidator.class);

    private final ZallyService zallyService;

    ZallyYamlFileValidator(ZallyService zallyService) {
        this.zallyService = zallyService;
    }

    abstract boolean supportsFile(PsiFile file);

    abstract Optional<PsiElement> getRootElement(PsiFile file);

    ProblemDescriptor[] checkViolations(final PsiFile file,
                                        final InspectionManager manager,
                                        final boolean isOnTheFly) {
        if (shouldLint(file)) {
            final LintingResponse lintingResponse = lint(file);
            return createProblems(manager, isOnTheFly, lintingResponse, file);
        }

        return ProblemDescriptor.EMPTY_ARRAY;
    }

    private LintingResponse lint(final PsiFile file) {
        try {
            return zallyService.lint(file.getText());
        } catch (final Exception e) {
            LOG.error("Could not lint specification with Zally", e);

            throw new RuntimeException(e);
        }
    }

    public boolean shouldLint(final PsiFile file) {
        return hasZallyUrl() && supportsFile(file);
    }

    private boolean hasZallyUrl() {
        final ZallySettings zallySettings = ServiceManager.getService(ZallySettings.class);

        return zallySettings.getZallyUrl() != null && !zallySettings.getZallyUrl().isEmpty();
    }

    @NotNull
    ProblemDescriptor[] createProblems(final InspectionManager manager,
                                       final boolean isOnTheFly,
                                       final LintingResponse lintingResponse,
                                       final PsiFile file) {
        final List<ProblemDescriptor> problems = Lists.newArrayList();

        final Optional<PsiElement> root = getRootElement(file);

        root.filter(el -> el instanceof YAMLKeyValue)
                .map(YAMLKeyValue.class::cast)
                .ifPresent((psiElement) -> {
                    final PsiElement key = psiElement.getKey();

                    if (key != null) {
                        final List<Violation> violations = lintingResponse.getViolations();

                        for (Violation violation : violations) {
                            final String descriptionTemplate = String.format("[%s] %s Location: (%s)",
                                    violation.getViolationType(),
                                    violation.getTitle(),
                                    violation.getPaths().stream().collect(Collectors.joining(", ")));

                            problems.add(manager.createProblemDescriptor(psiElement.getKey(), descriptionTemplate,
                                    isOnTheFly, LocalQuickFix.EMPTY_ARRAY, ProblemHighlightType.GENERIC_ERROR));
                        }
                    }
                });

        return problems.toArray(ProblemDescriptor.EMPTY_ARRAY);
    }


}
