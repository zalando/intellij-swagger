package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally;

import com.google.common.collect.Lists;
import com.intellij.codeInspection.*;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.ZallySettings;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.LintingResponse;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.Violation;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

public class ZallyYamlFileValidator extends LocalInspectionTool {

  private static final Logger LOG = Logger.getInstance(ZallyYamlFileValidator.class);

  private final ZallyService zallyService;
  private final PathFinder pathFinder;
  private final FileDetector fileDetector;

  public ZallyYamlFileValidator() {
    this(ServiceManager.getService(ZallyService.class), new PathFinder(), new FileDetector());
  }

  private ZallyYamlFileValidator(
      final ZallyService zallyService,
      final PathFinder pathFinder,
      final FileDetector fileDetector) {
    this.zallyService = zallyService;
    this.pathFinder = pathFinder;
    this.fileDetector = fileDetector;
  }

  @Override
  public ProblemDescriptor[] checkFile(
      @NotNull PsiFile file, @NotNull InspectionManager manager, boolean isOnTheFly) {
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

  private boolean supportsFile(final PsiFile file) {
    return fileDetector.isMainOpenApiYamlFile(file) || fileDetector.isMainSwaggerFile(file);
  }

  private boolean hasZallyUrl() {
    final ZallySettings zallySettings = ServiceManager.getService(ZallySettings.class);

    return zallySettings.getZallyUrl() != null && !zallySettings.getZallyUrl().isEmpty();
  }

  @NotNull
  private ProblemDescriptor[] createProblems(
      final InspectionManager manager,
      final boolean isOnTheFly,
      final LintingResponse lintingResponse,
      final PsiFile file) {
    final List<ProblemDescriptor> problems = Lists.newArrayList();

    final List<Violation> violations = lintingResponse.getViolations();

    for (Violation violation : violations) {
      final Optional<YAMLKeyValue> psiElement = getYAMLKeyValue(violation.getPointer(), file);

      psiElement.ifPresent(
          el -> {
            final String descriptionTemplate =
                String.format(
                    "[%s] %s. %s",
                    violation.getViolationType(), violation.getTitle(), violation.getDescription());

            final PsiElement key = el.getKey();

            if (key != null) {
              problems.add(
                  manager.createProblemDescriptor(
                      key,
                      descriptionTemplate,
                      isOnTheFly,
                      LocalQuickFix.EMPTY_ARRAY,
                      ProblemHighlightType.GENERIC_ERROR));
            }
          });
    }

    return problems.toArray(ProblemDescriptor.EMPTY_ARRAY);
  }

  private Optional<YAMLKeyValue> getYAMLKeyValue(final String jsonPointer, final PsiFile file) {
    return getPsiElement(jsonPointer, file)
        .filter(el -> el instanceof YAMLKeyValue)
        .map(YAMLKeyValue.class::cast);
  }

  private Optional<PsiElement> getPsiElement(final String jsonPointer, final PsiFile file) {
    Optional<PsiElement> psiElement =
        Optional.ofNullable(jsonPointer)
            .map(
                pointer ->
                    pointer
                        // Unescape JSON pointer (https://tools.ietf.org/html/rfc6901)
                        .replace("/", ".")
                        .replace("~1", "/")
                        .replace("~0", "~"))
            .flatMap(path -> pathFinder.findByPathFrom("$" + path, file));

    if (psiElement.isPresent()) {
      return psiElement;
    }

    return getRootElement(file);
  }

  private Optional<PsiElement> getRootElement(final PsiFile file) {
    if (fileDetector.isMainOpenApiFile(file)) {
      return pathFinder.findByPathFrom("$.openapi", file);
    } else {
      return pathFinder.findByPathFrom("$.swagger", file);
    }
  }
}
