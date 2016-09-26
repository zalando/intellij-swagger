package org.zalando.intellij.swagger.reference;

import com.intellij.psi.PsiElement;
import org.junit.Assert;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class TagsReferenceTest extends SwaggerLightCodeInsightFixtureTestCase {

    private void doTest(final String fileName, final String referencedValue) {
        myFixture.configureByFile("reference/" + fileName);
        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        final PsiElement target = element.getReferences()[0].resolve();

        Assert.assertNotNull(target);
        Assert.assertEquals(referencedValue, target.getText());
    }

    public void testThatTagsCanBeNavigated() {
        doTest("tags.json", "\"tag1\"");
        doTest("tags.yaml", "tag1");
    }
}
