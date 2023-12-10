package com._4scaffolding.service;

import com._4scaffolding.vo.ProjectConfigVO;
import com.intellij.openapi.project.Project;

public class ProjectGeneratorImpl extends AbstractProjectGenerator {
    @Override
    public void generateProjectPOM(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project,"/",entryPath,"pom.xml","pom.ftl",projectConfig);
    }
}
