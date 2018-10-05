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
import org.zalando.intellij.swagger.traversal.path.PathFinder;

import java.util.List;
import java.util.Optional;

public abstract class ZallyYamlFileValidator extends LocalInspectionTool {

    private final static Logger LOG = Logger.getInstance(ZallyYamlFileValidator.class);

    private final ZallyService zallyService;
    protected final PathFinder pathFinder;

    ZallyYamlFileValidator(ZallyService zallyService, PathFinder pathFinder) {
        this.zallyService = zallyService;
        this.pathFinder = pathFinder;
    }

    abstract boolean supportsFile(PsiFile file);

    abstract Optional<PsiElement> getRootElement(PsiFile file);

    ProblemDescriptor[] checkViolations(final PsiFile file,
                                        final InspectionManager manager,
                                        final boolean isOnTheFly) {
        if (canLint(file)) {
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

    private boolean canLint(final PsiFile file) {
        return hasZallyUrl() && supportsFile(file);
    }

    private boolean hasZallyUrl() {
        final ZallySettings zallySettings = ServiceManager.getService(ZallySettings.class);

        return zallySettings.getZallyUrl() != null && !zallySettings.getZallyUrl().isEmpty();
    }

    @NotNull
    private ProblemDescriptor[] createProblems(final InspectionManager manager,
                                               final boolean isOnTheFly,
                                               final LintingResponse lintingResponse,
                                               final PsiFile file) {
        final List<ProblemDescriptor> problems = Lists.newArrayList();

        final List<Violation> violations = lintingResponse.getViolations();

        for (Violation violation : violations) {
            final Optional<YAMLKeyValue> psiElement = getPsiElement(violation.getPointer(), file);

            psiElement.ifPresent(el -> {
                final String descriptionTemplate = String.format("[%s] %s. %s",
                        violation.getViolationType(),
                        violation.getTitle(),
                        violation.getDescription());

                final PsiElement key = el.getKey();

                if (key != null) {
                    problems.add(manager.createProblemDescriptor(key, descriptionTemplate,
                            isOnTheFly, LocalQuickFix.EMPTY_ARRAY, ProblemHighlightType.GENERIC_ERROR));
                }
            });
        }

        return problems.toArray(ProblemDescriptor.EMPTY_ARRAY);
    }

    private Optional<YAMLKeyValue> getPsiElement(final String jsonPointer, final PsiFile file) {
        Optional<Optional<PsiElement>> psiElement = Optional.ofNullable(jsonPointer)
                .map(pointer -> pointer
                        // Unescape JSON pointer (https://tools.ietf.org/html/rfc6901)
                        .replace("/", ".")
                        .replace("~1", "/")
                        .replace("~0", "~"))
                .map(path -> pathFinder.findByPathFrom("$" + path, file));

        return psiElement
                .orElse(getRootElement(file))
                .filter(el -> el instanceof YAMLKeyValue)
                .map(YAMLKeyValue.class::cast);
    }
}
