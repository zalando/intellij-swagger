package org.zalando.intellij.swagger.completion;

import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.CodeInsightTestFixture;
import com.intellij.testFramework.fixtures.IdeaProjectTestFixture;
import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory;
import com.intellij.testFramework.fixtures.TestFixtureBuilder;
import com.intellij.testFramework.fixtures.impl.LightTempDirTestFixtureImpl;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SwaggerFixture {
    private final CodeInsightTestFixture myCodeInsightFixture;

    @NotNull
    public static SwaggerFixture forResourceFolder(@NotNull String resourceBasedPath) throws Exception {
        String absolutePath = toAbsolutePath(resourceBasedPath);
        return new SwaggerFixture(absolutePath);
    }

    public SwaggerFixture(@NotNull String folderAbsolutePath) throws Exception {
        IdeaTestFixtureFactory factory = IdeaTestFixtureFactory.getFixtureFactory();
        LightProjectDescriptor projectDescriptor = LightProjectDescriptor.EMPTY_PROJECT_DESCRIPTOR;
        TestFixtureBuilder<IdeaProjectTestFixture> fixtureBuilder = factory.createLightFixtureBuilder(projectDescriptor);
        IdeaProjectTestFixture fixture = fixtureBuilder.getFixture();

        myCodeInsightFixture = factory.createCodeInsightFixture(fixture, new LightTempDirTestFixtureImpl(true));
        myCodeInsightFixture.setUp();
        myCodeInsightFixture.setTestDataPath(folderAbsolutePath);
    }

    public void tearDown() throws Exception {
        myCodeInsightFixture.tearDown();
    }

    @NotNull
    public AssertableList<String> getCompletions(@NotNull String caretFileName) {
        List<String> results = myCodeInsightFixture.getCompletionVariants(caretFileName);
        Assert.assertThat(results, IsNull.notNullValue());
        return new AssertableList<>(results);
    }

    @NotNull
    public AssertableList<String> getCompletions(@NotNull String caretFileNoExt, @NotNull JsonOrYaml fileKind) {
        String fullName = fileKind.getFileName(caretFileNoExt);
        return getCompletions(fullName);
    }

    public enum JsonOrYaml {
        JSON("json"),
        YAML("yaml");

        private final String myFileExtension;

        JsonOrYaml(String fileExtension) {
            myFileExtension = fileExtension;
        }

        @NotNull
        public String getFileName(@NotNull String base) {
            return base.endsWith(".") ? base + myFileExtension : base + "." + myFileExtension;
        }
    }

    public static class AssertableList<T> {
        private final List<T> myActualList;

        public AssertableList(List<T> list) {
            myActualList = new ArrayList<>(list);
        }

        public AssertableList<T> assertContains(Iterable<T> elements) {
            for (T next : elements) {
                assertContainsOne(next);
            }
            return this;
        }

        public AssertableList<T> assertContains(T... elements) {
            for (T next : elements) {
                assertContainsOne(next);
            }
            return this;
        }

        public AssertableList<T> assertContainsOne(T element) {
            Assert.assertThat(myActualList, IsCollectionContaining.hasItem(element));
            return this;
        }

        public AssertableList<T> assertNotContains(T... badElements) {
            for (T nextBad : badElements) {
                Assert.assertThat(myActualList, IsNot.not(IsCollectionContaining.hasItem(nextBad)));
            }
            return this;
        }
    }

    @NotNull
    private static String toAbsolutePath(@NotNull String resourcesRelatedPath) {
        URL url = ClassLoader.getSystemClassLoader().getResource(resourcesRelatedPath);
        if (url == null) {
            throw new IllegalStateException("Can't locate: " + resourcesRelatedPath);
        }
        try {
            return new File(url.toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Should not happen: path: " + resourcesRelatedPath + ", url: " + url, e);
        }
    }

}
