package com._5orm.module;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class FileChooserComponent {

    private Project project;
    private FileEditorManager fileEditorManager;

    private FileChooserComponent(Project project) {
        this.project = project;
        fileEditorManager = FileEditorManager.getInstance(project);
    }

    public static FileChooserComponent getInstance(Project project) {
        return new FileChooserComponent(project);
    }

    public VirtualFile showFolderSelectionDialog(String title, VirtualFile toSelect) {
        FileChooserDescriptor singleFileDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor();
        singleFileDescriptor.setTitle(title);
        return FileChooser.chooseFile(singleFileDescriptor, project, toSelect);
    }
}
