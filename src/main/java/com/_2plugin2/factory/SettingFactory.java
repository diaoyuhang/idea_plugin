package com._2plugin2.factory;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.util.NlsContexts;
import com._2plugin2.Config.Config;
import com._2plugin2.ui.SettingUI;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.io.RandomAccessFile;

public class SettingFactory implements SearchableConfigurable {
    private SettingUI settingUI = new SettingUI();

    @Override
    public @NotNull @NonNls String getId() {
        return "plugin2";
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "plugin2";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return settingUI.getMainPanel();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        String url = settingUI.getTextField1().getText();
        try {
            File file = new File(url);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

            randomAccessFile.seek(0);
            byte[] bytes = new byte[1024 * 1024];
            int readSize = randomAccessFile.read(bytes);

            byte[] copy = new byte[readSize];
            System.arraycopy(bytes,0,copy,0,readSize);
            Config.readUI.getTextContent().setText(new String(copy,"utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
