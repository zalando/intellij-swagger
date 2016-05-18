package org.zalando.intellij.swagger;

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.core.IsNot;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class SwaggerCodeInsightFixtureTestCase extends LightCodeInsightFixtureTestCase {

    protected static final String TEST_DATA_ROOT = "testData";

    @Override
    public final String getBasePath() {
        throw new UnsupportedOperationException("Should not be called, getTestDataPath overridden");
    }

    protected String getTestDataFolder() {
        return TEST_DATA_ROOT;
    }

    @Override
    protected String getTestDataPath() {
        String folderName = getTestDataFolder();
        URL url = ClassLoader.getSystemClassLoader().getResource(folderName);
        if (url == null) {
            throw new IllegalStateException("Can't locate: " + folderName);
        }
        try {
            return new File(url.toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Should not happen: folderName: " + folderName + ", url: " + url, e);
        }
    }

    protected static class AssertableList<T> {
        private final List<T> myActualList;

        public AssertableList(List<T> list) {
            myActualList = new ArrayList<>(list);
        }

        public AssertableList<T> assertContains(T... elements) {
            for (T next : elements) {
                MatcherAssert.assertThat(myActualList, IsCollectionContaining.hasItem(next));
            }
            return this;
        }

        public AssertableList<T> assertNotContains(T... badElements) {
            for (T nextBad : badElements) {
                MatcherAssert.assertThat(myActualList, IsNot.not(IsCollectionContaining.hasItem(nextBad)));
            }
            return this;
        }
    }

}
