package com._4scaffolding.ui;

import javax.swing.*;

public class ProjectConfigUI {
    private JTextField groupIdField;
    private JTextField artifactIdField;
    private JTextField versionField;
    private JTextField packageField;
    private JPanel mainPanel;

    public JTextField getGroupIdField() {
        return groupIdField;
    }

    public void setGroupIdField(JTextField groupIdField) {
        this.groupIdField = groupIdField;
    }

    public JTextField getArtifactIdField() {
        return artifactIdField;
    }

    public void setArtifactIdField(JTextField artifactIdField) {
        this.artifactIdField = artifactIdField;
    }

    public JTextField getVersionField() {
        return versionField;
    }

    public void setVersionField(JTextField versionField) {
        this.versionField = versionField;
    }

    public JTextField getPackageField() {
        return packageField;
    }

    public void setPackageField(JTextField packageField) {
        this.packageField = packageField;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
}
