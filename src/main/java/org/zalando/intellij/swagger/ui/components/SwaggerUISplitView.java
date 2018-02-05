package org.zalando.intellij.swagger.ui.components;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.impl.text.TextEditorState;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.ui.JBSplitter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;

public class SwaggerUISplitView extends UserDataHolderBase implements FileEditor {

    private final TextEditor swaggerEditor;
    private final SwaggerUIViewer swaggerViewer;
    private final JComponent splitUIView;

    private JComponent swaggerUIToolbar;

    public SwaggerUISplitView(@NotNull final TextEditor swaggerEdtior, @NotNull final SwaggerUIViewer swaggerViewer) {
        this.swaggerEditor = swaggerEdtior;
        this.swaggerViewer = swaggerViewer;

        splitUIView = createComponent();
    }

    private JComponent createComponent() {
        final JBSplitter splitter = new JBSplitter(false, 0.5f, 0.15f, 0.85f);
        splitter.setFirstComponent(swaggerEditor.getComponent());
        splitter.setSecondComponent(swaggerViewer);

        swaggerUIToolbar = new SwaggerUIToolbar(splitter);

        JPanel result = new JPanel(new BorderLayout());
        result.add(swaggerUIToolbar, BorderLayout.NORTH);
        result.add(splitter, BorderLayout.CENTER);

        return result;
    }

    @NotNull
    public JComponent getComponent() {
        return splitUIView;
    }

    public SwaggerUIViewer getUIViewer() {
        return this.swaggerViewer;
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return swaggerEditor.getComponent();
    }

    @NotNull
    @Override
    public String getName() {
        return "SwaggerUISplitView";
    }

    @Override
    public void setState(@NotNull FileEditorState state) {
    }

    @NotNull
    @Override
    public FileEditorState getState(@NotNull FileEditorStateLevel level) {
        return new TextEditorState();
    }

    @Override
    public boolean isModified() {
        return swaggerEditor.isModified();
    }

    @Override
    public boolean isValid() {
        return swaggerEditor.isValid();
    }

    @Override
    public void selectNotify() {
        swaggerEditor.selectNotify();
    }

    @Override
    public void deselectNotify() {
        swaggerEditor.deselectNotify();
    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {
        swaggerEditor.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {
        swaggerEditor.removePropertyChangeListener(listener);
    }

    @Nullable
    @Override
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return swaggerEditor.getBackgroundHighlighter();
    }

    @Nullable
    @Override
    public FileEditorLocation getCurrentLocation() {
        return swaggerEditor.getCurrentLocation();
    }

    @Override
    public void dispose() {
    }
}
