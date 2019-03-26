package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally;

import com.google.common.collect.Lists;
import com.intellij.codeInspection.*;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.ZallySettingsGui;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.LintingResponse;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.Violation;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

import java.util.List;
import java.util.Optional;

public class ZallyYamlFileValidator extends LocalInspectionTool {

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
    if (canLint(file) && !isOnTheFly) {
      return lint(file).map(response -> createProblems(manager, response, file)).orElse(null);
    }

    return null;
  }

  private Optional<LintingResponse> lint(final PsiFile file) {
    try {
      return Optional.of(zallyService.lint(file.getText()));
    } catch (final Exception e) {
      Notification notification =
          new Notification(
              "Zally", "Could not lint API spec", e.getMessage(), NotificationType.ERROR);

      Notifications.Bus.notify(notification);

      notification.addAction(
          new NotificationAction("Configure Zally") {
            @Override
            public void actionPerformed(
                @NotNull AnActionEvent anActionEvent, @NotNull Notification notification) {
              final DataContext dataContext = anActionEvent.getDataContext();
              final Project project = PlatformDataKeys.PROJECT.getData(dataContext);
              ShowSettingsUtil.getInstance().showSettingsDialog(project, ZallySettingsGui.class);
            }
          });

      return Optional.empty();
    }
  }

  private boolean canLint(final PsiFile file) {
    return supportsFile(file);
  }

  private boolean supportsFile(final PsiFile file) {
    return fileDetector.isMainOpenApiYamlFile(file) || fileDetector.isMainSwaggerFile(file);
  }

  @NotNull
  private ProblemDescriptor[] createProblems(
      final InspectionManager manager, final LintingResponse lintingResponse, final PsiFile file) {
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
                      false,
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
