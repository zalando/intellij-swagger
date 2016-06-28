package org.zalando.intellij.swagger.ui;

import com.intellij.ide.browsers.WebBrowser;
import com.intellij.ide.browsers.WebBrowserManager;
import com.intellij.ide.browsers.actions.BaseOpenInBrowserAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class BaseWebBrowserAction extends BaseOpenInBrowserAction {
    private final WebBrowser browser;

    BaseWebBrowserAction(@NotNull WebBrowser browser) {
        super(browser);
        this.browser = browser;
    }

    @Nullable
    @Override
    protected WebBrowser getBrowser(@NotNull AnActionEvent event) {
        return WebBrowserManager.getInstance().isActive(browser) && browser.getPath() != null ? browser : null;
    }
}