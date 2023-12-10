package com._4scaffolding.service;

import com._4scaffolding.vo.ProjectConfigVO;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.twelvemonkeys.lang.StringUtil;
import freemarker.template.Template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public abstract class AbstractProjectGenerator extends FreemarkerConfiguration implements IProjectGenerator {
    @Override
    public void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig) {
        // 1.创建工程主POM文件
        generateProjectPOM(project, entryPath, projectConfig);
    }

    public abstract void generateProjectPOM(Project project, String entryPath, ProjectConfigVO projectConfig);

    public void writeFile(Project project, String packageName, String entryPath, String name, String ftl, Object dataModel) {

        try {
            String path = FileUtil.toSystemDependentName(entryPath + "/" + StringUtil.replace(packageName, ".", "/"));
            new File(path).mkdirs();
            VirtualFile virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByPath(path).createChildData(project, name);

            StringWriter stringWriter = new StringWriter();
            Template template = getTemplate(ftl);
            template.process(dataModel, stringWriter);
            virtualFile.setBinaryContent(stringWriter.toString().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
