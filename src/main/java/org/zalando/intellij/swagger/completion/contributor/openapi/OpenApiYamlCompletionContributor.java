package org.zalando.intellij.swagger.completion.contributor.openapi;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.OpenApiCompletionHelper;
import org.zalando.intellij.swagger.completion.contributor.CompletionResultSetFactory;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.completion.openapi.OpenApiFieldCompletionFactory;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.completion.openapi.OpenApiValueCompletionFactory;
import org.zalando.intellij.swagger.file.OpenApiFileType;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.traversal.YamlTraversal;
import org.zalando.intellij.swagger.traversal.path.openapi.PathResolver;
import org.zalando.intellij.swagger.traversal.path.openapi.PathResolverFactory;

public class OpenApiYamlCompletionContributor extends CompletionContributor {

  private final YamlTraversal yamlTraversal;
  private final OpenApiIndexService openApiIndexService;

  public OpenApiYamlCompletionContributor() {
    this(new YamlTraversal(), new OpenApiIndexService());
  }

  private OpenApiYamlCompletionContributor(
      final YamlTraversal yamlTraversal, final OpenApiIndexService openApiIndexService) {
    this.yamlTraversal = yamlTraversal;
    this.openApiIndexService = openApiIndexService;
  }

  @Override
  public void fillCompletionVariants(
      @NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {

    final Optional<OpenApiFileType> maybeFileType = openApiIndexService.getFileType(parameters);

    maybeFileType.ifPresent(
        fileType -> {
          final PathResolver pathResolver = PathResolverFactory.fromOpenApiFileType(fileType);

          final PsiElement psiElement = parameters.getPosition();

          final OpenApiCompletionHelper completionHelper =
              new OpenApiCompletionHelper(
                  psiElement, yamlTraversal, pathResolver, openApiIndexService);

          OpenApiFieldCompletionFactory.from(completionHelper, result)
              .ifPresent(FieldCompletion::fill);

          OpenApiValueCompletionFactory.from(
                  completionHelper, CompletionResultSetFactory.forValue(parameters, result))
              .ifPresent(ValueCompletion::fill);

          result.stopHere();
        });
  }
}
