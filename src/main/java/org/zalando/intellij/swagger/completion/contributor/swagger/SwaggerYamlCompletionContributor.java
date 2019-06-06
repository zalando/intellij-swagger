package org.zalando.intellij.swagger.completion.contributor.swagger;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;
import org.zalando.intellij.swagger.completion.contributor.CompletionResultSetFactory;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.completion.swagger.SwaggerFieldCompletionFactory;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.completion.swagger.SwaggerValueCompletionFactory;
import org.zalando.intellij.swagger.extensions.completion.swagger.SwaggerCustomFieldCompletionFactory;
import org.zalando.intellij.swagger.extensions.completion.swagger.SwaggerCustomValueCompletionFactory;
import org.zalando.intellij.swagger.file.SwaggerFileType;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;
import org.zalando.intellij.swagger.traversal.YamlTraversal;
import org.zalando.intellij.swagger.traversal.path.swagger.PathResolver;
import org.zalando.intellij.swagger.traversal.path.swagger.PathResolverFactory;

public class SwaggerYamlCompletionContributor extends CompletionContributor {

  private final YamlTraversal yamlTraversal;
  private final SwaggerIndexService swaggerIndexService;

  public SwaggerYamlCompletionContributor() {
    this(new YamlTraversal(), new SwaggerIndexService());
  }

  private SwaggerYamlCompletionContributor(
      final YamlTraversal yamlTraversal, final SwaggerIndexService swaggerIndexService) {
    this.yamlTraversal = yamlTraversal;
    this.swaggerIndexService = swaggerIndexService;
  }

  @Override
  public void fillCompletionVariants(
      @NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {

    final Optional<SwaggerFileType> maybeFileType = swaggerIndexService.getFileType(parameters);

    maybeFileType.ifPresent(
        fileType -> {
          final PathResolver pathResolver = PathResolverFactory.fromSwaggerFileType(fileType);

          final PsiElement psiElement = parameters.getPosition();

          final SwaggerCompletionHelper completionHelper =
              new SwaggerCompletionHelper(
                  psiElement, yamlTraversal, pathResolver, swaggerIndexService);

          SwaggerFieldCompletionFactory.from(completionHelper, result)
              .ifPresent(FieldCompletion::fill);

          SwaggerValueCompletionFactory.from(
                  completionHelper, CompletionResultSetFactory.forValue(parameters, result))
              .ifPresent(ValueCompletion::fill);

          for (SwaggerCustomFieldCompletionFactory ep :
              SwaggerCustomFieldCompletionFactory.EP_NAME.getExtensions()) {
            ep.from(completionHelper, result).ifPresent(FieldCompletion::fill);
          }

          for (SwaggerCustomValueCompletionFactory ep :
              SwaggerCustomValueCompletionFactory.EP_NAME.getExtensions()) {
            ep.from(completionHelper, result).ifPresent(ValueCompletion::fill);
          }

          result.stopHere();
        });
  }
}
