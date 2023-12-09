package com._2plugin2.ui;

import javax.swing.*;
import java.io.File;

public class SettingUI {
    private JTextField textField1;
    private JButton button;
    private JPanel mainPanel;

    public SettingUI() {
        button.addActionListener(e->{
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser.showOpenDialog(mainPanel);
            File selectedFile = jFileChooser.getSelectedFile();
            textField1.setText(selectedFile.getPath());
        });
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public void setTextField1(JTextField textField1) {
        this.textField1 = textField1;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
}
