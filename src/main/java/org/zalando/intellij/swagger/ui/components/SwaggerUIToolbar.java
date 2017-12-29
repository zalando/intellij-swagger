package org.zalando.intellij.swagger.ui.components;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.util.ui.JBEmptyBorder;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import java.awt.*;

class SwaggerUIToolbar extends JPanel {

    private static final String SWAGGER_TOOLBAR_GROUP_ID = "Swagger.Toolbar";

    SwaggerUIToolbar(final JComponent targetComponentForActions) {
        super(new GridBagLayout());

        final ActionToolbar toolbar = createToolbarFromGroupId(SWAGGER_TOOLBAR_GROUP_ID);
        toolbar.setTargetComponent(targetComponentForActions);

        add(toolbar.getComponent());
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIUtil.CONTRAST_BORDER_COLOR));
    }

    private static ActionToolbar createToolbarFromGroupId(final String groupId) {
        final ActionManager actionManager = ActionManager.getInstance();

        if (!actionManager.isGroup(groupId)) {
            throw new IllegalStateException(groupId + " should have been a group");
        }
        final ActionGroup group = ((ActionGroup)actionManager.getAction(groupId));
        final ActionToolbarImpl editorToolbar =
                ((ActionToolbarImpl)actionManager.createActionToolbar(ActionPlaces.EDITOR_TOOLBAR, group, true));
        editorToolbar.setOpaque(false);
        editorToolbar.setBorder(new JBEmptyBorder(0, 2, 0, 2));

        return editorToolbar;
    }
}
