package com._5orm.infrastructure.po;

import java.util.List;

public class Table {
    private String comment;
    private String name;
    private List<Column> columns;

    public Table(String comment, String name, List<Column> columns) {
        this.comment = comment;
        this.name = name;
        this.columns = columns;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Table{" +
                "comment='" + comment + '\'' +
                ", name='" + name + '\'' +
                ", columns=" + columns +
                '}';
    }
}
