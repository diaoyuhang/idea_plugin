package com._3stock.module;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.util.IconLoader;
import com._3stock.ui.GidConfig;
import org.jetbrains.annotations.NotNull;

public class SettingBar extends DumbAwareAction {

    private ViewBars bars;

    public SettingBar(ViewBars viewBars) {
        super("配置股票", "Click to setting", IconLoader.getIcon("/icon/config.svg",SettingBar.class));
        this.bars = viewBars;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ShowSettingsUtil.getInstance().editConfigurable(bars.getProject(),new GidConfig(bars.getConsoleUI()));
    }
}
