package org.zalando.intellij.swagger.ui.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiFile;
import org.zalando.intellij.swagger.service.SwaggerFileService;

public class RefreshSwaggerUIAction extends AnAction {

    private static final Logger LOG = Logger.getInstance(RefreshSwaggerUIAction.class);

    @Override
    public void actionPerformed(AnActionEvent e) {
        final PsiFile target = e.getData(LangDataKeys.PSI_FILE);
        if (target != null) {
            SwaggerFileService swaggerFileService = ServiceManager.getService(SwaggerFileService.class);
            swaggerFileService.convertSwaggerToHtml(target);
        } else {
            LOG.warn("Refresh Swagger UI action not performed: target: was null");
        }
    }
}
