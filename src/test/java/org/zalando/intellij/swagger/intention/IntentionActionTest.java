package org.zalando.intellij.swagger.intention;

import com.google.common.base.Preconditions;
import com.intellij.codeInsight.daemon.LightIntentionActionTestCase;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class IntentionActionTest extends LightIntentionActionTestCase {

    @NotNull
    @Override
    protected String getTestDataPath() {
        try {
            return tryToGetAbsolutePath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private String tryToGetAbsolutePath() throws URISyntaxException {
        final URL url = ClassLoader.getSystemClassLoader().getResource("testing/");
        Preconditions.checkNotNull(url);
        return new File(url.toURI()).getAbsolutePath();
    }

}
