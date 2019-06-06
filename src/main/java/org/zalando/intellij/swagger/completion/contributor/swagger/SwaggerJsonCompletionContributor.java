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
import org.zalando.intellij.swagger.traversal.JsonTraversal;
import org.zalando.intellij.swagger.traversal.path.swagger.PathResolver;
import org.zalando.intellij.swagger.traversal.path.swagger.PathResolverFactory;

public class SwaggerJsonCompletionContributor extends CompletionContributor {

  private final JsonTraversal jsonTraversal;
  private final SwaggerIndexService swaggerIndexService;

  public SwaggerJsonCompletionContributor() {
    this(new JsonTraversal(), new SwaggerIndexService());
  }

  private SwaggerJsonCompletionContributor(
      final JsonTraversal jsonTraversal, final SwaggerIndexService swaggerIndexService) {
    this.jsonTraversal = jsonTraversal;
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
                  psiElement, jsonTraversal, pathResolver, swaggerIndexService);

          if (jsonTraversal.isKey(psiElement)) {
            SwaggerFieldCompletionFactory.from(completionHelper, result)
                .ifPresent(FieldCompletion::fill);
            for (SwaggerCustomFieldCompletionFactory ep :
                SwaggerCustomFieldCompletionFactory.EP_NAME.getExtensions()) {
              ep.from(completionHelper, result).ifPresent(FieldCompletion::fill);
            }
          } else {
            SwaggerValueCompletionFactory.from(
                    completionHelper, CompletionResultSetFactory.forValue(parameters, result))
                .ifPresent(ValueCompletion::fill);
            for (SwaggerCustomValueCompletionFactory ep :
                SwaggerCustomValueCompletionFactory.EP_NAME.getExtensions()) {
              ep.from(completionHelper, result).ifPresent(ValueCompletion::fill);
            }
          }

          result.stopHere();
        });
  }
}
