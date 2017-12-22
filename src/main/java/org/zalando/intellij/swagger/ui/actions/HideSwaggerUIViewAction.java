package org.zalando.intellij.swagger.ui.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditor;
import org.zalando.intellij.swagger.ui.components.SwaggerUISplitView;

public class HideSwaggerUIViewAction extends AnAction {

    private static final Logger LOG = Logger.getInstance(HideSwaggerUIViewAction.class);

    @Override
    public void actionPerformed(AnActionEvent e) {
        FileEditor fileEditor = e.getData(PlatformDataKeys.FILE_EDITOR);
        if (fileEditor instanceof SwaggerUISplitView) {
            ((SwaggerUISplitView) fileEditor).getUIViewer().setVisible(false);
        } else {
            LOG.warn(String.format("Hide Swagger UI action not performed: fileEditor: %s", fileEditor));
        }
    }
}
