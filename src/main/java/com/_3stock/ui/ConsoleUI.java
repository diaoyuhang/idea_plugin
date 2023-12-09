package com._3stock.ui;

import com._3stock.infrastructure.DataSetting;
import com._3stock.infrastructure.DataState;
import com._3stock.module.vo.Data;
import com._3stock.module.vo.GoPicture;
import com._3stock.service.IStock;
import com._3stock.service.impl.StockImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ConsoleUI {
    private JTabbedPane tabbedPane1;
    private JPanel one;
    private JPanel two;
    private JTable table;
    private JLabel picMin;
    private JLabel picDay;

    private IStock stock = new StockImpl();

    private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][]{}, new String[]{"股票", "代码", "最新", "涨跌", "涨幅"});

    public ConsoleUI() {
        table.setModel(defaultTableModel);
        addRows(DataSetting.getInstance().getState());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                Object valueAt = table.getValueAt(selectedRow, 1);
                GoPicture goPicture = stock.queryGidGoPicture(valueAt.toString());

                try {
                    picMin.setIcon(new ImageIcon(new URL(goPicture.getMinurl())));
                    picDay.setIcon(new ImageIcon(new URL(goPicture.getDayurl())));
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void addRows(DataState state) {
        List<String> gids = state.getGids();
        List<Data> dataList = stock.queryPresetStockData(gids);

        int rowCount = defaultTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            defaultTableModel.removeRow(0);
        }
        for (Data data : dataList) {
            defaultTableModel.addRow(new String[]{data.getName(), data.getGid(), data.getNowPri(), data.getIncrease(), data.getIncrePer()});
        }
//        for (Integer i=0;i< new Random().nextInt(20)+1;i++){
//            defaultTableModel.addRow(new String[]{i.toString()+" "+String.join(",",gids),i.toString(),i.toString(),i.toString(),i.toString()});
//        }
        table.setModel(defaultTableModel);
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public void setTabbedPane1(JTabbedPane tabbedPane1) {
        this.tabbedPane1 = tabbedPane1;
    }

    public JPanel getOne() {
        return one;
    }

    public void setOne(JPanel one) {
        this.one = one;
    }

    public JPanel getTwo() {
        return two;
    }

    public void setTwo(JPanel two) {
        this.two = two;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JLabel getPicMin() {
        return picMin;
    }

    public void setPicMin(JLabel picMin) {
        this.picMin = picMin;
    }

    public JLabel getPicDay() {
        return picDay;
    }

    public void setPicDay(JLabel picDay) {
        this.picDay = picDay;
    }

    public IStock getStock() {
        return stock;
    }

    public void setStock(IStock stock) {
        this.stock = stock;
    }

    public DefaultTableModel getDefaultTableModel() {
        return defaultTableModel;
    }

    public void setDefaultTableModel(DefaultTableModel defaultTableModel) {
        this.defaultTableModel = defaultTableModel;
    }
}
