package com._5orm.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ORMSettingsUI implements Configurable {
    private JPanel main;
    private JTextField classpath;
    private JTextField projectName;
    private JTextField poPath;
    private JTextField daoPath;
    private JTextField xmlPath;
    private JTextField host;
    private JTextField password;
    private JTextField database;
    private JTextField user;
    private JButton poButton;
    private JButton daoButton;
    private JButton xmlButton;
    private JTextField port;
    private JButton testConn;
    private JButton selectTableName;
    private JTable table1;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return null;
    }

    @Override
    public @Nullable JComponent createComponent() {
        return null;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }
}
