package com._5orm.infrastructure.service;

import com._5orm.infrastructure.po.Column;
import com._5orm.infrastructure.po.Field;
import com._5orm.infrastructure.po.Model;
import com._5orm.infrastructure.po.Table;
import com._5orm.infrastructure.util.JavaType;
import com._5orm.model.CodeGenContextVO;
import com.google.common.base.CaseFormat;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProjectGeneratorImpl implements IProjectGenerator {
    @Override
    public void generation(Project project, CodeGenContextVO codeGenContextVO) {
        Configuration freemarker = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        freemarker.setDefaultEncoding("UTF-8");
        freemarker.setClassForTemplateLoading(this.getClass(), "/template");

        List<Table> tableList = codeGenContextVO.getTableList();
        for (Table table : tableList) {
            List<Column> columns = table.getColumns();
            List<Field> fields = new ArrayList<>();

            for (Column column : columns) {
                Field field = new Field(column.getComment(), JavaType.convertType(column.getType()), column.getName());
                fields.add(field);
            }
            Model model = new Model(table.getComment(), codeGenContextVO.getPoPath() + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName()), table.getName(), fields);

            String javaClassName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName());
            String totalPath = codeGenContextVO.getPoPath() + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table.getName());

            String path = FileUtil.toSystemIndependentName(codeGenContextVO.getPoPath());

            new File(path).mkdirs();
            VirtualFile virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
            try {
                virtualFile = virtualFile.createChildData(project, javaClassName + ".java");
                Template template = freemarker.getTemplate("orm/po.ftl");
                StringWriter stringWriter = new StringWriter();
                template.process(model,stringWriter);
                virtualFile.setBinaryContent(stringWriter.toString().getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
