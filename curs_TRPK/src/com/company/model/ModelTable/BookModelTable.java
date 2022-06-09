package com.company.model.ModelTable;

import com.company.db.DBWorker;
import com.company.model.BookIncome;
import com.company.model.BookList;

import javax.swing.table.AbstractTableModel;
import java.sql.Date;
import java.sql.SQLException;

public class BookModelTable extends AbstractTableModel {
    public static BookList data=new BookList();

    public BookModelTable(BookList book) {
        try{
            DBWorker.connectionBD();
            DBWorker.newTable();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.data=book;
    }

    public static void connectDB(){
        data.clear();
        try {
            DBWorker.readDB();
            data = DBWorker.getArr();
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
            case 1: return "Время поспления";
            case 2: return "Способ поспления";
        }
        return "";
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BookIncome rec= data.bookList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rec.getB_number();
            case 1:
                return rec.getDate();
            case 2: {

                return rec.getWay();
            }

        }
        return "default";
    }


}
