package com._4scaffolding.module;

import com._4scaffolding.infrastructure.DataSetting2;
import com._4scaffolding.infrastructure.ICONS;
import com._4scaffolding.service.ProjectGeneratorImpl;
import com._4scaffolding.ui.ProjectConfigUI;
import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.StdModuleTypes;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

public class DDDModuleBuilder extends ModuleBuilder {

    private ProjectGeneratorImpl projectGenerator = new ProjectGeneratorImpl();

    @Override
    public Icon getNodeIcon() {
        return ICONS.SPRING_BOOT;
    }

    @Override
    public ModuleType<?> getModuleType() {
        return StdModuleTypes.JAVA;
    }

    @Override
    public @Nullable @NonNls String getBuilderId() {
        return getClass().getName();
    }

    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[]{new DDDModuleConfigStep(new ProjectConfigUI())};
    }

    @Override
    public void setupRootModel(@NotNull ModifiableRootModel rootModel) throws ConfigurationException {
        System.out.println(this.myJdk.getName());
        System.out.println(getContentEntryPath());
        String path = FileUtil.toSystemDependentName(Objects.requireNonNull(getContentEntryPath()));

        new File(path).mkdirs();

        VirtualFile virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
        rootModel.addContentEntry(virtualFile);

        Project project = rootModel.getProject();
        WriteCommandAction.Builder builder = WriteCommandAction.writeCommandAction(project);
        builder.run(()->{
            //开始创建工程文件步骤
            projectGenerator.doGenerator(project,getContentEntryPath(), DataSetting2.getInstance().getState().getProjectConfigVO());
        });


//        if (ApplicationManager.getApplication().isUnitTestMode()
//                || ApplicationManager.getApplication().isHeadlessEnvironment()) {
//            r.run();
//            return;
//        }
//
//        if (!project.isInitialized()) {
//            StartupManager.getInstance(project).registerPostStartupActivity(DisposeAwareRunnable.create(r, project));
//            return;
//        }
//
//        if (DumbService.isDumbAware(r)) {
//            r.run();
//        } else {
//            DumbService.getInstance(project).runWhenSmart(DisposeAwareRunnable.create(r, project));
//        }
    }

    @Override
    public @Nullable ModuleWizardStep modifySettingsStep(@NotNull SettingsStep settingsStep) {
        ModuleNameLocationSettings moduleNameLocationSettings = settingsStep.getModuleNameLocationSettings();
        String artifactId = DataSetting2.getInstance().getState().getProjectConfigVO().getArtifactId();
        if (null != moduleNameLocationSettings && !StringUtil.isEmptyOrSpaces(artifactId)) {
            moduleNameLocationSettings.setModuleName(artifactId);
        }
        return super.modifySettingsStep(settingsStep);
    }
}
