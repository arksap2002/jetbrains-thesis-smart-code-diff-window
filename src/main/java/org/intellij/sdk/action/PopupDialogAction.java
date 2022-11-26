package org.intellij.sdk.action;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class PopupDialogAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        assert editor != null;
        StringBuilder stringBuilder = new StringBuilder();
        String code = editor.getDocument().getText();
        CompilationUnit compilationUnit = StaticJavaParser.parse(code);
        List<MethodDeclaration> methodDeclarations = compilationUnit.findAll(MethodDeclaration.class);
        stringBuilder.append("Number of methods in the the currently opened file: ").append(methodDeclarations.size()).append("\n");
        int cursorLocation = StringUtils.countMatches(code.substring(0, editor.getCaretModel().getOffset()), "\n") + 1;
        MethodDeclaration cursorMethod = null;
        for (MethodDeclaration methodDeclaration : methodDeclarations) {
            assert methodDeclaration.getBegin().isPresent();
            if (methodDeclaration.getBegin().get().line <= cursorLocation) {
                cursorMethod = methodDeclaration;
            }
        }
        if (cursorMethod == null) {
            stringBuilder.append("Put your cursor in some method, please\n");
        } else {
            stringBuilder.append("Number of variables defined in the currently selected method: ").append(cursorMethod.findAll(VariableDeclarator.class).size()).append("\n");
        }
        Messages.showMessageDialog(
                project,
                stringBuilder.toString(),
                event.getPresentation().getDescription(),
                Messages.getInformationIcon());
    }

    @Override
    public void update(AnActionEvent event) {
        Project currentProject = event.getProject();
        event.getPresentation().setEnabledAndVisible(currentProject != null);
    }
}
