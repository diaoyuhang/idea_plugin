package com._5orm.action;

import com._5orm.ui.ORMSettingsUI;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;

public class CodeGenerateAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

//        Project project = e.getProject();
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        ShowSettingsUtil.getInstance().editConfigurable(project,new ORMSettingsUI());
    }
}
