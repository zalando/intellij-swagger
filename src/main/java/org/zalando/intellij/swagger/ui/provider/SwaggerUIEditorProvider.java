package org.zalando.intellij.swagger.ui.provider;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.ui.components.SwaggerUISplitView;
import org.zalando.intellij.swagger.ui.components.SwaggerUIViewer;

public class SwaggerUIEditorProvider implements FileEditorProvider {

    private final TextEditorProvider editorProvider;
    private final FileDetector fileDetector;

    public SwaggerUIEditorProvider() {
        this.editorProvider = new PsiAwareTextEditorProvider();
        this.fileDetector = new FileDetector();
    }

    @Override
    public boolean accept(@NotNull final Project project, @NotNull final VirtualFile file) {
        PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
        return (
                fileDetector.isSwaggerContentCompatible(file) &&
                        (fileDetector.isMainSwaggerFile(psiFile) || fileDetector.isMainOpenApiFile(psiFile))
        );
    }

    @NotNull
    @Override
    public FileEditor createEditor(@NotNull final Project project, @NotNull final VirtualFile file) {
        FileEditor fileEditor = editorProvider.createEditor(project, file);

        return new SwaggerUISplitView(
                (TextEditor) fileEditor,
                new SwaggerUIViewer(file)
        );
    }

    @NotNull
    @Override
    public FileEditorState readState(@NotNull final Element sourceElement, @NotNull final Project project, @NotNull final VirtualFile file) {
        return editorProvider.readState(sourceElement, project, file);
    }

    @Override
    public void writeState(@NotNull final FileEditorState state, @NotNull final Project project, @NotNull final Element targetElement) {
        editorProvider.writeState(state, project, targetElement);
    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return "SwingUIEditor[" + editorProvider.toString() + "]";
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }
}
