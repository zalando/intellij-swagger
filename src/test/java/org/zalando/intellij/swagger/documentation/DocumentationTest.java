package org.zalando.intellij.swagger.documentation;

import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public abstract class DocumentationTest extends SwaggerLightCodeInsightFixtureTestCase {

  private final String filesPath;

  public DocumentationTest(final String filesPath) {
    this.filesPath = filesPath;
  }

  protected void testQuickDocumentation(final String fileName, final String expectedDocumentation) {
    final PsiFile psiFile = myFixture.configureByFile(filesPath + fileName);

    final PsiElement originalElement =
        psiFile.findElementAt(myFixture.getEditor().getCaretModel().getOffset()).getParent();

    final PsiElement targetElement = originalElement.getReferences()[0].resolve();

    final DocumentationProvider documentationProvider =
        DocumentationManager.getProviderFromElement(targetElement);

    final String quickNavigateInfo =
        documentationProvider.getQuickNavigateInfo(targetElement, originalElement);

    assertEquals(expectedDocumentation, quickNavigateInfo);
  }
}
