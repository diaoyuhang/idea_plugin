package com._3stock.module;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.util.IconLoader;
import com._3stock.infrastructure.DataSetting;
import org.jetbrains.annotations.NotNull;

public class RefreshBar extends DumbAwareAction {

    private ViewBars bars;

    public RefreshBar(ViewBars bars){
        super("刷新指数", "Click to refresh", IconLoader.getIcon("/icon/refresh.svg",RefreshBar.class));
        this.bars = bars;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        bars.getConsoleUI().addRows(DataSetting.getInstance().getState());
    }
}
