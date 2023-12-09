package com._3stock.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import com._3stock.infrastructure.DataSetting;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class GidConfig implements Configurable {
    private JPanel mainPanel;
    private JTextField textField;

    private ConsoleUI consoleUI;

    public GidConfig(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Stock";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return this.mainPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        String gidStr = this.textField.getText();
        System.out.println(gidStr);
        String[] gitArr = gidStr.split(";");
        List<String> gitList = DataSetting.getInstance().getState().getGids();
        gitList.clear();
        gitList.addAll(Arrays.asList(gitArr));

        consoleUI.addRows(DataSetting.getInstance().getState());
    }
}
