package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally;

import com.intellij.codeInspection.ex.LocalInspectionToolWrapper;
import org.zalando.intellij.swagger.examples.extensions.zalando.SwaggerLightCodeInsightFixtureTestCase;

public class SwaggerYamlFileValidatorTest extends SwaggerLightCodeInsightFixtureTestCase {

  public void testZallyViolationsAreReported() {
    final ZallyYamlFileValidator swaggerYamlFileValidator = new ZallyYamlFileValidator();

    myFixture.enableInspections(swaggerYamlFileValidator);

    final LocalInspectionToolWrapper toolWrapper =
        new LocalInspectionToolWrapper(swaggerYamlFileValidator);
    myFixture.testInspection("zally/yaml/", toolWrapper);
  }
}
