package com._5orm.ui;

import com._5orm.infrastructure.data.DataSetting3;
import com._5orm.infrastructure.po.Table;
import com._5orm.infrastructure.service.ProjectGeneratorImpl;
import com._5orm.infrastructure.util.DBHelper;
import com._5orm.model.CodeGenContextVO;
import com._5orm.model.ORMConfigVO;
import com._5orm.module.FileChooserComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;

public class ORMSettingsUI implements Configurable {
    private JPanel main;
    private JTextField classpath;
    private JTextField projectName;
    private JTextField poPath;
    private JTextField daoPath;
    private JTextField xmlPath;
    private JTextField host;
    private JTextField password;
    private JTextField database;
    private JTextField user;
    private JButton poButton;
    private JButton daoButton;
    private JButton xmlButton;
    private JTextField port;
    private JButton testConn;
    private JButton selectTableName;
    private JTable table;

    private Project project;

    private ORMConfigVO ormConfigVO;

    public ORMSettingsUI(Project project) {
        this.project = project;
        ormConfigVO = DataSetting3.getInstance(project).getORMConfig();

        if (ormConfigVO!=null){
            this.user.setText(ormConfigVO.getUser());
            this.password.setText(ormConfigVO.getPassword());
            this.host.setText(ormConfigVO.getHost());
            this.port.setText(ormConfigVO.getPort());
        }


        this.poButton.addActionListener((event) -> {
////            VirtualFile baseDir = project.getBaseDir();
            VirtualFile baseDir = ProjectUtil.guessProjectDir(project);

            VirtualFile virtualFile = FileChooserComponent.getInstance(project).showFolderSelectionDialog("选择po生成目录", baseDir);
            if (virtualFile != null) {
                ORMSettingsUI.this.poPath.setText(virtualFile.getPath());
            }

        });

        this.daoButton.addActionListener((event) -> {
            VirtualFile baseDir = ProjectUtil.guessProjectDir(project);

            VirtualFile virtualFile = FileChooserComponent.getInstance(project).showFolderSelectionDialog("选择po生成目录", baseDir);
            if (virtualFile != null) {
                ORMSettingsUI.this.daoPath.setText(virtualFile.getPath());
            }
        });

        this.xmlButton.addActionListener((event) -> {
            VirtualFile baseDir = ProjectUtil.guessProjectDir(project);

            VirtualFile virtualFile = FileChooserComponent.getInstance(project).showFolderSelectionDialog("选择po生成目录", baseDir);
            if (virtualFile != null) {
                ORMSettingsUI.this.xmlPath.setText(virtualFile.getPath());
            }
        });

        this.selectTableName.addActionListener(e -> {
            DBHelper dbHelper = new DBHelper(this.database.getText(), this.host.getText(), this.user.getText(), this.password.getText(), Integer.parseInt(this.port.getText()));
            List<String> tableNames = dbHelper.getTables();

            String[] title = {"", "表名"};
            Object[][] data = new Object[tableNames.size()][2];
            for (int i = 0; i < tableNames.size(); i++) {
                data[i][1] = tableNames.get(i);
            }

            table.setModel(new DefaultTableModel(data, title));
            TableColumn column = table.getColumnModel().getColumn(0);
            column.setCellEditor(new DefaultCellEditor(new JCheckBox()));
            column.setCellEditor(table.getDefaultEditor(Boolean.class));
            column.setCellRenderer(table.getDefaultRenderer(Boolean.class));
        });

        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int rowIdx = table.rowAtPoint(e.getPoint());
                    Boolean flag = (boolean) table.getValueAt(rowIdx, 0);
                    Set<String> tableNames = ormConfigVO.getTableNames();
                    if (flag != null && flag) {
                        tableNames.add(table.getValueAt(rowIdx, 1).toString());
                    } else {
                        tableNames.remove(table.getValueAt(rowIdx, 1).toString());
                    }
                }
            }
        });
    }

    @Override
    public void apply() throws ConfigurationException {
        ormConfigVO.setDatabase(this.database.getText());
        ormConfigVO.setHost(this.host.getText());
        ormConfigVO.setPort(this.port.getText());
        ormConfigVO.setUser(this.user.getText());
        ormConfigVO.setPassword(this.password.getText());
        ormConfigVO.setPoPath(this.poPath.getText());
        ormConfigVO.setXmlPath(this.xmlPath.getText());
        ormConfigVO.setDaoPath(this.xmlPath.getText());

        DBHelper dbHelper = new DBHelper(this.database.getText(), this.host.getText(), this.user.getText(), this.password.getText(), Integer.parseInt(this.port.getText()));
        List<Table> tableInfoList = dbHelper.getTableInfo(ormConfigVO.getTableNames());

        CodeGenContextVO codeGenContextVO = new CodeGenContextVO();
        codeGenContextVO.setPoPath(ormConfigVO.getPoPath()+"/po/");
        codeGenContextVO.setTableList(tableInfoList);

        ProjectGeneratorImpl projectGenerator = new ProjectGeneratorImpl();
        projectGenerator.generation(project,codeGenContextVO);
    }
    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "orm";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return main;
    }

    @Override
    public boolean isModified() {
        return true;
    }

}
