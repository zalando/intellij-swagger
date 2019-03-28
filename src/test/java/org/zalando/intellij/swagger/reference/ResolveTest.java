package org.zalando.intellij.swagger.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class ResolveTest extends SwaggerLightCodeInsightFixtureTestCase {

  public void testReferenceWithSlashWorks() {
    final PsiFile psiFile = myFixture.configureByFile("reference/slashes.yaml");

    final PsiElement originalElement =
        psiFile.findElementAt(myFixture.getEditor().getCaretModel().getOffset()).getParent();

    final PsiElement targetElement = originalElement.getReferences()[0].resolve();

    assertEquals("/bar", ((YAMLKeyValue) targetElement).getKeyText());
  }
}
