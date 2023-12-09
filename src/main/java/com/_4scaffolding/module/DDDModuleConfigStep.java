package com._4scaffolding.module;

import com._4scaffolding.infrastructure.DataSetting2;
import com._4scaffolding.infrastructure.DataState;
import com._4scaffolding.ui.ProjectConfigUI;
import com._4scaffolding.vo.ProjectConfigVO;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;

import javax.swing.*;

public class DDDModuleConfigStep extends ModuleWizardStep {

    private ProjectConfigUI projectConfigUI;

    public DDDModuleConfigStep(ProjectConfigUI projectConfigUI) {
        this.projectConfigUI = projectConfigUI;
    }

    @Override
    public JComponent getComponent() {
        return projectConfigUI.getMainPanel();
    }

    @Override
    public void updateDataModel() {
        DataSetting2 dataSetting2 = DataSetting2.getInstance();
        DataState state = dataSetting2.getState();
        ProjectConfigVO projectConfigVO = state.getProjectConfigVO();

        projectConfigVO.setGroupId(projectConfigUI.getGroupIdField().getText());
        projectConfigVO.setArtifactId(projectConfigUI.getArtifactIdField().getText());
        projectConfigVO.setVersion(projectConfigUI.getVersionField().getText());
        projectConfigVO.setPackageName(projectConfigUI.getPackageField().getText());

    }
}
