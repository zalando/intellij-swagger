package org.zalando.intellij.swagger.reference.usage;

import com.intellij.json.psi.JsonProperty;
import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Processor;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.zalando.intellij.swagger.index.IndexFacade;
import org.zalando.intellij.swagger.traversal.path.PathExpressionUtil;

public class SpecReferenceSearch
    extends QueryExecutorBase<PsiReference, ReferencesSearch.SearchParameters> {

  private static final boolean CASE_SENSITIVE = false;
  private static final boolean REQUIRE_READ_ACTION = true;
  private IndexFacade indexFacade;

  public SpecReferenceSearch(final IndexFacade indexFacade) {
    super(REQUIRE_READ_ACTION);

    this.indexFacade = indexFacade;
  }

  @Override
  public void processQuery(
      @NotNull final ReferencesSearch.SearchParameters queryParameters,
      @NotNull final Processor<? super PsiReference> consumer) {
    final PsiElement elementToSearch = queryParameters.getElementToSearch();

    final Project project = queryParameters.getProject();

    if (indexFacade.isIndexReady(project)) {
      if (isSpec(elementToSearch, project)) {
        process(queryParameters, elementToSearch, project);
      }
    }
  }

  private void process(
      final ReferencesSearch.SearchParameters queryParameters,
      final PsiElement elementToSearch,
      final Project project) {
    Optional.ofNullable(((PsiNamedElement) elementToSearch).getName())
        .ifPresent(
            word -> {
              final String escaped = PathExpressionUtil.escapeJsonPointer(word);

              if (!escaped.equals(word)) {
                queryParameters
                    .getOptimizer()
                    .searchWord(
                        escaped,
                        GlobalSearchScope.allScope(project),
                        CASE_SENSITIVE,
                        elementToSearch);
              }
            });
  }

  private boolean isSpec(final PsiElement elementToSearch, final Project project) {
    if (elementToSearch instanceof YAMLKeyValue || elementToSearch instanceof JsonProperty) {
      final VirtualFile virtualFile = elementToSearch.getContainingFile().getVirtualFile();

      return indexFacade.isMainSpecFile(virtualFile, project)
          || indexFacade.isPartialSpecFile(virtualFile, project);
    }

    return false;
  }
}
