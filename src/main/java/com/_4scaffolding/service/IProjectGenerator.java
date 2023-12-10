package com._4scaffolding.service;

import com._4scaffolding.vo.ProjectConfigVO;
import com.intellij.openapi.project.Project;

public interface IProjectGenerator {
    void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig);
}
