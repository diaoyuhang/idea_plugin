package com._5orm.infrastructure.service;

import com._5orm.model.CodeGenContextVO;
import com.intellij.openapi.project.Project;

public interface IProjectGenerator {
    void generation(Project project, CodeGenContextVO codeGenContextVO);
}
