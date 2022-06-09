package com.company.model.ModelTable;

import com.company.db.DBWorker;
import com.company.model.Description;
import com.company.model.DescriptionList;
import com.company.model.Inventory;
import com.company.model.InventoryList;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;

public class DescriptionModelTable extends AbstractTableModel {
    public static DescriptionList data=new DescriptionList();

    public DescriptionModelTable(DescriptionList mus) {
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
            DBWorker.readDescriptionDB();
            data = DBWorker.getArrDesc();
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
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "Id";
            case 1: return "Автор";
            case 2: return "Историческое описание";

        }
        return "";
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Description rec= data.invList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rec.getM_id();
            case 1:
                return rec.getAvtor();
            case 2:
                return rec.getDescription();

        }
        return "default";
    }


}
