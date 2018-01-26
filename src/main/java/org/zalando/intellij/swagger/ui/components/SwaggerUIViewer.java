package org.zalando.intellij.swagger.ui.components;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.ui.JBColor;
import com.intellij.util.Url;
import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.zalando.intellij.swagger.ui.actions.SwaggerUIFilesChangeNotifier;

import javax.swing.*;
import java.awt.*;

public class SwaggerUIViewer extends JPanel {

    private WebEngine webEngine;

    public SwaggerUIViewer() {
        super(new BorderLayout());
        this.setBackground(JBColor.background());
        this.setVisible(false);
        Platform.setImplicitExit(false);

        ApplicationManager
                .getApplication()
                .invokeLater(() -> PlatformImpl.startup(this::createWebView));
    }

    private void createWebView() {
        JFXPanel myPanel = new JFXPanel();
        WebView myWebView = new WebView();
        Scene scene = new Scene(myWebView);

        webEngine  = myWebView.getEngine();

        Platform.runLater(() -> myPanel.setScene(scene));

        this.add(myPanel, BorderLayout.CENTER);
        this.repaint();

        subscribeToSwaggerFileUpdates();
    }

    private void subscribeToSwaggerFileUpdates() {
        ApplicationManager
                .getApplication()
                .getMessageBus()
                .connect()
                .subscribe(SwaggerUIFilesChangeNotifier.SWAGGER_UI_FILES_CHANGED, new SwaggerUIUpdater());
    }

    class SwaggerUIUpdater implements SwaggerUIFilesChangeNotifier {

        @Override
        public void swaggerHTMLFilesChanged(Url indexUrl) {
            Platform.runLater(() -> webEngine.load("file:///" + indexUrl.toExternalForm()));
        }
    }
}
