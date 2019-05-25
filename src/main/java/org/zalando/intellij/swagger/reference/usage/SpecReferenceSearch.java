package org.zalando.intellij.swagger.reference.usage;

import com.intellij.json.psi.JsonProperty;
import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Processor;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.zalando.intellij.swagger.traversal.path.PathExpressionUtil;

public class SpecReferenceSearch
    extends QueryExecutorBase<PsiReference, ReferencesSearch.SearchParameters> {

  private static final boolean CASE_SENSITIVE = false;
  private static final boolean REQUIRE_READ_ACTION = true;

  public SpecReferenceSearch() {
    super(REQUIRE_READ_ACTION);
  }

  @Override
  public void processQuery(
      @NotNull final ReferencesSearch.SearchParameters queryParameters,
      @NotNull final Processor<? super PsiReference> consumer) {
    final PsiElement elementToSearch = queryParameters.getElementToSearch();

    if (elementToSearch instanceof YAMLKeyValue || elementToSearch instanceof JsonProperty) {
      Optional.ofNullable(((PsiNamedElement) elementToSearch).getName())
          .ifPresent(
              word ->
                  queryParameters
                      .getOptimizer()
                      .searchWord(
                          PathExpressionUtil.escapeJsonPointer(word),
                          GlobalSearchScope.allScope(queryParameters.getProject()),
                          CASE_SENSITIVE,
                          elementToSearch));
    }
  }
}
