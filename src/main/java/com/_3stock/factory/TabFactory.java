package com._3stock.factory;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com._3stock.module.ViewBars;
import org.jetbrains.annotations.NotNull;

public class TabFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ViewBars viewBars = new ViewBars(project);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(viewBars, "股票", false);

        toolWindow.getContentManager().addContent(content,0);
    }
}
