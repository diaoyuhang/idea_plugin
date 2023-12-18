package com._5orm.infrastructure.util;

import com._5orm.infrastructure.po.Column;
import com._5orm.infrastructure.po.Table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DBHelper {

    private String dbName;
    private String host;
    private String userName;
    private String password;
    private Integer port;

    public DBHelper(String dbName, String host, String userName, String password, Integer port) {
        this.dbName = dbName;
        this.host = host;
        this.userName = userName;
        this.password = password;
        this.port = port;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {



    }

    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://%s:%d/%s?useUnicode=true&socketTimeout=10000&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
        return DriverManager.getConnection(String.format(url, this.host, this.port, this.dbName),this.userName,this.password);
    }

    public List<String> getTables() {
        List<String> tables = new ArrayList<>();
        try (Connection connection = getConnection()){
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                String s = rs.getString("TABLE_NAME");
                tables.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    public List<Table> getTableInfo(Set<String> tableNames) {
        List<Table> tables = new ArrayList<>();
        try(Connection connection = getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            for (String tableName : tableNames) {

                ResultSet resultSet = metaData.getTables(connection.getCatalog(), null, tableName, new String[]{"TABLE"});
                while (resultSet.next()){
                    tables.add(new Table(resultSet.getString("REMARKS"),tableName,getAllColumn(tableName,connection)));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    private List<Column> getAllColumn(String tableName, Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
        String primaryKey=null;
        while(primaryKeys.next()){
            primaryKey = primaryKeys.getString("COLUMN_NAME");
        }
        ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, null);
        ArrayList<Column> columnList = new ArrayList<>();
        while(columns.next()){
            String column_name = columns.getString("COLUMN_NAME");
            columnList.add(new Column(column_name.equals(primaryKey),columns.getInt("DATA_TYPE"),column_name,columns.getString("REMARKS")));
        }
        return columnList;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
