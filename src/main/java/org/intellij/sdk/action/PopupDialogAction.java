package org.intellij.sdk.action;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;


public class PopupDialogAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project currentProject = event.getProject();
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        assert editor != null;
        String selectedText = editor.getSelectionModel().getSelectedText();
        String numberOfAllMethods = Integer.toString(StaticJavaParser.parse(editor.getDocument().getText()).findAll(MethodDeclaration.class).size());
        String title = event.getPresentation().getDescription();
        Messages.showMessageDialog(
                currentProject,
                numberOfAllMethods,
                title,
                Messages.getInformationIcon());
    }

    @Override
    public void update(AnActionEvent event) {
        Project currentProject = event.getProject();
        event.getPresentation().setEnabledAndVisible(currentProject != null);
    }
}
