package com._3stock.module;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.JBSplitter;
import com._3stock.ui.ConsoleUI;

public class ViewBars extends SimpleToolWindowPanel {

    private ConsoleUI consoleUI;
    private Project project;

    public ViewBars(Project project) {
        super(false,true);
        consoleUI = new ConsoleUI();
        this.project = project;
        DefaultActionGroup defaultActionGroup = new DefaultActionGroup();
        defaultActionGroup.add(new SettingBar(this));
        defaultActionGroup.add(new RefreshBar(this));

        ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar("bar", defaultActionGroup, false);
        actionToolbar.setTargetComponent(this);
        setToolbar(actionToolbar.getComponent());

        JBSplitter jbSplitter = new JBSplitter(false);
        jbSplitter.setSplitterProportionKey("main.splitter.key");
        jbSplitter.setFirstComponent(consoleUI.getTabbedPane1());

        setContent(jbSplitter);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ConsoleUI getConsoleUI() {
        return consoleUI;
    }

    public void setConsoleUI(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }
}
