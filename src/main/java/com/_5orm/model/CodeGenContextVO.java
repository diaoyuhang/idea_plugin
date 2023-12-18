package com._5orm.model;

import com._5orm.infrastructure.po.Table;

import java.util.List;

public class CodeGenContextVO {

    private String poPath;

    private List<Table> tableList;

    public List<Table> getTableList() {
        return tableList;
    }

    public void setTableList(List<Table> tableList) {
        this.tableList = tableList;
    }

    public String getPoPath() {
        return poPath;
    }

    public void setPoPath(String poPath) {
        this.poPath = poPath;
    }
}
