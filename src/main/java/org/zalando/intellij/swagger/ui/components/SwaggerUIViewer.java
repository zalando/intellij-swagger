package org.zalando.intellij.swagger.ui.components;

import com.intellij.openapi.application.*;
import com.intellij.ui.*;
import com.intellij.util.*;
import com.intellij.util.messages.*;
import com.sun.javafx.application.*;
import javafx.application.*;
import javafx.embed.swing.*;
import javafx.scene.*;
import javafx.scene.web.*;
import org.zalando.intellij.swagger.ui.actions.*;

import javax.swing.*;
import java.awt.*;

public class SwaggerUIViewer extends JPanel {

    private WebEngine webEngine;
    private MessageBus messageBus;
    private MessageBusConnection messageBusConnection;

    public SwaggerUIViewer() {
        super(new BorderLayout());
        this.setBackground(JBColor.background());
        this.setVisible(false);
        Platform.setImplicitExit(false);

        ApplicationManager
                .getApplication()
                .invokeLater(() -> PlatformImpl.startup(this::createWebView));

        messageBus = ApplicationManager
                .getApplication()
                .getMessageBus();
    }

    private void createWebView() {
        JFXPanel myPanel = new JFXPanel();
        WebView myWebView = new WebView();
        Scene scene = new Scene(myWebView);

        webEngine = myWebView.getEngine();

        Platform.runLater(() -> myPanel.setScene(scene));

        this.add(myPanel, BorderLayout.CENTER);
        this.repaint();
    }

    public void subscribeChanges() {
        messageBusConnection = messageBus.connect();

        messageBusConnection.subscribe(
                SwaggerUIFilesChangeNotifier.SWAGGER_UI_FILES_CHANGED,
                new SwaggerUIUpdater());
    }

    public void unsubscribeChanges() {
        if (messageBusConnection != null) {
            messageBusConnection.disconnect();
        }
    }

    class SwaggerUIUpdater implements SwaggerUIFilesChangeNotifier {

        @Override
        public void swaggerHTMLFilesChanged(Url indexUrl) {
            Platform.runLater(() -> webEngine.load("file:///" + indexUrl.toExternalForm()));
        }
    }
}
