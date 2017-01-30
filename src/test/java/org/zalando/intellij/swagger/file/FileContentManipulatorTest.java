package org.zalando.intellij.swagger.file;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class FileContentManipulatorTest {
    private FileContentManipulator fileContentManipulator;
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();


    @Before
    public void setUp() throws Exception {
        fileContentManipulator = new FileContentManipulator();
    }

    @Test
    public void itCanSetPlaceholderValueInANewFile() throws Exception {
        File targetFile = copyResourceTo("with_placeholder.html", "index.html");
        File expectedOutputFile = copyResourceTo("with_placeholder_replaced.html", "expected.html");

        fileContentManipulator.setPlaceholderValue("${swaggerSpecification}", "http://petstore.swagger.io/v2/swagger.json", targetFile);

        assertTrue("The files differ!", FileUtils.contentEquals(targetFile, expectedOutputFile));

    }

    @NotNull
    private File copyResourceTo(String resourceName, String fileName) throws IOException {
        URL inputUrl = getClass().getResource("/testing/file/" + resourceName);
        File targetFile = tempFolder.newFile(fileName);
        FileUtils.copyURLToFile(inputUrl, targetFile);
        return targetFile;
    }
}
