package org.zalando.intellij.swagger.ui.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.psi.PsiFile;
import org.zalando.intellij.swagger.service.SwaggerFileService;
import org.zalando.intellij.swagger.ui.components.SwaggerUISplitView;

public class ShowSwaggerUIViewAction extends AnAction {

    private static final Logger LOG = Logger.getInstance(ShowSwaggerUIViewAction.class);

    @Override
    public void actionPerformed(AnActionEvent e) {
        final FileEditor fileEditor = e.getData(PlatformDataKeys.FILE_EDITOR);
        final PsiFile target = e.getData(LangDataKeys.PSI_FILE);
        if (fileEditor instanceof SwaggerUISplitView && target != null) {
            ((SwaggerUISplitView) fileEditor).getUIViewer().setVisible(true);
            SwaggerFileService swaggerFileService = ServiceManager.getService(SwaggerFileService.class);
            swaggerFileService.convertSwaggerToHtml(target);
        } else {
            LOG.warn(String.format("Show Swagger UI action not performed: fileEditor: %s, target: %s", fileEditor, target));
        }
    }
}
