package org.zalando.intellij.swagger.ui.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.components.*;
import com.intellij.openapi.diagnostic.*;
import com.intellij.openapi.fileEditor.*;
import com.intellij.psi.*;
import org.zalando.intellij.swagger.service.*;
import org.zalando.intellij.swagger.ui.components.*;

public class ShowSwaggerUIViewAction extends AnAction {

    private static final Logger LOG = Logger.getInstance(ShowSwaggerUIViewAction.class);

    @Override
    public void actionPerformed(AnActionEvent e) {
        final FileEditor fileEditor = e.getData(PlatformDataKeys.FILE_EDITOR);
        final PsiFile target = e.getData(LangDataKeys.PSI_FILE);
        if (fileEditor instanceof SwaggerUISplitView && target != null) {
            SwaggerUISplitView splitView = (SwaggerUISplitView) fileEditor;
            splitView.getUIViewer().setVisible(true);
            splitView.getUIViewer().subscribeChanges();

            SwaggerFileService swaggerFileService = ServiceManager.getService(SwaggerFileService.class);
            swaggerFileService.convertSwaggerToHtml(target);
        } else {
            LOG.warn(String.format("Show Swagger UI action not performed: fileEditor: %s, target: %s", fileEditor, target));
        }
    }
}
