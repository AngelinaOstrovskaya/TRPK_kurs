package com.company.model.ModelTable;

import com.company.db.DBWorker;
import com.company.model.Inventory;
import com.company.model.InventoryList;
import com.company.model.MuseumObject;
import com.company.model.MuseumObjectList;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;

public class InventoryModelTable extends AbstractTableModel {
    public static InventoryList data=new InventoryList();

    public InventoryModelTable(InventoryList mus) {
        try{
            DBWorker.connectionBD();
            DBWorker.newTable();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.data=mus;
    }

    public static void connectDB(){
        data.clear();
        try {
            DBWorker.readInventoryDB();
            data = DBWorker.getArrInv();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return data.getCount();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "Id";
            case 1: return "Фонд";
            case 2: return "Место хранения";
            case 3: return "Дата поступления";

        }
        return "";
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Inventory rec= data.invList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rec.getMus_id();
            case 1:
                return rec.getFund_id();
            case 2:
                return rec.getLoc_id();
            case 3:
                return rec.getDate();

        }
        return "default";
    }


}
