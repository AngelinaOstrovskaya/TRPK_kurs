package com.company.model.ModelTable;

import com.company.db.DBWorker;
import com.company.model.MuseumObject;
import com.company.model.MuseumObjectList;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;

public class MuseumModelTable extends AbstractTableModel {
    public static MuseumObjectList data=new MuseumObjectList();

    public MuseumModelTable(MuseumObjectList mus) {
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
            DBWorker.readMuseumDB();
            data = DBWorker.getArrMus();
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
        return 5;
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "Id";
            case 1: return "Наименование";
            case 2: return "Размер";
            case 3: return "Материал";
            case 4: return "Сохранность";
        }
        return "";
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MuseumObject rec= data.museumList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rec.getB_number();
            case 1:
                return rec.getName();
            case 2:
                return rec.getSize();
            case 3:
                return rec.getMaterial();
            case 4:
                return rec.getSafety();

        }
        return "default";
    }


}
