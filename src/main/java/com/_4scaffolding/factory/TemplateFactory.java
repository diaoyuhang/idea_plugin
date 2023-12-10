package com._4scaffolding.factory;

import com._4scaffolding.infrastructure.ICONS;
import com._4scaffolding.module.DDDModuleBuilder;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.project.Project;
import com.intellij.platform.ProjectTemplate;
import com.intellij.platform.ProjectTemplatesFactory;
import com.intellij.platform.templates.BuilderBasedTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TemplateFactory extends ProjectTemplatesFactory {

    public static Project project = null;

    @Override
    public String @NotNull [] getGroups() {
        return new String[]{"DDD脚手架"};
    }

    @Override
    public Icon getGroupIcon(String group) {
        return ICONS.DDD;
    }

    @Override
    public ProjectTemplate @NotNull [] createTemplates(@Nullable String group, @NotNull WizardContext context) {
        project = context.getProject();
        return new ProjectTemplate[]{new BuilderBasedTemplate(new DDDModuleBuilder())};
    }
}
