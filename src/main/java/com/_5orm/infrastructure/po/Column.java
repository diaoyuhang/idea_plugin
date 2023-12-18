package com._5orm.infrastructure.po;

public class Column {
    private boolean primaryKey;
    //对应java.sql.Types
    private int type;
    private String name;
    private String comment;

    public Column(boolean primaryKey, int type, String name, String comment) {
        this.primaryKey = primaryKey;
        this.type = type;
        this.name = name;
        this.comment = comment;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Column{" +
                "primaryKey=" + primaryKey +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
