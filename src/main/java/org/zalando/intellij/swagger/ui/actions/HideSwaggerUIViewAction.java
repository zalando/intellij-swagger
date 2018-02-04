package org.zalando.intellij.swagger.ui.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.diagnostic.*;
import com.intellij.openapi.fileEditor.*;
import org.zalando.intellij.swagger.ui.components.*;

public class HideSwaggerUIViewAction extends AnAction {

    private static final Logger LOG = Logger.getInstance(HideSwaggerUIViewAction.class);

    @Override
    public void actionPerformed(AnActionEvent e) {
        final FileEditor fileEditor = e.getData(PlatformDataKeys.FILE_EDITOR);
        if (fileEditor instanceof SwaggerUISplitView) {
            SwaggerUISplitView splitView = (SwaggerUISplitView) fileEditor;
            splitView.getUIViewer().setVisible(false);
            splitView.getUIViewer().unsubscribeChanges();
        } else {
            LOG.warn(String.format("Hide Swagger UI action not performed: fileEditor: %s", fileEditor));
        }
    }
}
