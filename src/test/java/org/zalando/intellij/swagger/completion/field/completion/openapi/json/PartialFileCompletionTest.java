package org.zalando.intellij.swagger.completion.field.completion.openapi.json;

import com.intellij.openapi.vfs.VirtualFile;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public abstract class PartialFileCompletionTest extends SwaggerLightCodeInsightFixtureTestCase {

  private static final String PARTIAL_FILES_PATH = "completion/field/openapi/partial/";

  void withSpecFiles(final String refFile, final String specFile) {
    myFixture.copyFileToProject(PARTIAL_FILES_PATH + refFile, refFile);

    final VirtualFile specs =
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + specFile, FilenameUtils.getName(specFile));

    myFixture.configureFromExistingVirtualFile(specs);
  }

  List<String> geCompletions(final String fileName) {
    return myFixture.getCompletionVariants(fileName);
  }
}
