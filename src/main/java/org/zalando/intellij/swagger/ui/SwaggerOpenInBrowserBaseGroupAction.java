package org.zalando.intellij.swagger.ui;

import com.intellij.icons.AllIcons;
import com.intellij.ide.browsers.WebBrowser;
import com.intellij.ide.browsers.WebBrowserManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SwaggerOpenInBrowserBaseGroupAction extends ComputableActionGroup {

    public SwaggerOpenInBrowserBaseGroupAction() {
        super(false);

        Presentation p = getTemplatePresentation();
        p.setText("Open in _Browser");
        p.setDescription("Open selected file in browser");
        p.setIcon(AllIcons.Nodes.PpWeb);
    }

    @NotNull
    @Override
    protected final CachedValueProvider<AnAction[]> createChildrenProvider(@NotNull final ActionManager actionManager) {
        return () -> {
            List<WebBrowser> browsers = WebBrowserManager.getInstance().getBrowsers();

            AnAction[] actions = browsers.stream()
                    .map(BaseWebBrowserAction::new)
                    .toArray(AnAction[]::new);

            return CachedValueProvider.Result.create(actions, WebBrowserManager.getInstance());
        };
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        final WebBrowserManager browserManager = WebBrowserManager.getInstance();
        e.getPresentation().setVisible(browserManager.isShowBrowserHover() && !browserManager.getActiveBrowsers().isEmpty());
    }

}
