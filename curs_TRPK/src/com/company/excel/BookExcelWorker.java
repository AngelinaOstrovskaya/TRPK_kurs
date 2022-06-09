package com.company.excel;

import com.company.db.DBWorker;
import com.company.model.BookExcel;
import com.sun.rowset.internal.Row;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookExcelWorker {
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private HSSFRow row;
    private ArrayList<BookExcel> dataRec=new ArrayList<BookExcel>();
    private double sum;

    public BookExcelWorker(){

        // создание самого excel файла в памяти
        workbook = new HSSFWorkbook();
        // создание листа
        sheet = workbook.createSheet("Лист 1");

        int rowNum=0;
        //создание подписей к столбцам
        row=sheet.createRow(rowNum);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Наименование музейного предмета");
        row.createCell(2).setCellValue("Дата поступления в музей");
        row.createCell(3).setCellValue("Способ постпления");
        row.createCell(4).setCellValue("Сохранность");
        row.createCell(5).setCellValue("Фонд");
        row.createCell(6).setCellValue("Место хранения");
        row.createCell(7).setCellValue("Дата поступления в фонд");
        row.createCell(8).setCellValue("Результат проверки");


        // data=fillData(name,data1,data2);
        dataRec=fillData();

        // заполняем лист данными
        //for (Record dataModel : dataRec) {
        //     createSheetHeader(sheet, ++rowNum, dataModel);
        // }
        for (BookExcel dataModel : dataRec) {
            createSheetHeader(sheet, ++rowNum, dataModel);

            //System.out.println(data.getCount());
            //System.out.println(data.getPecord(i).getPayment());
        }

        //Row row = sheet.createRow(10);
        // идем в 4ю ячейку строки и устанавливаем
        // формулу подсчета зарплат (столбец D)
        //Cell sum = row.createCell(3);
        // sum.setCellValue(String.valueOf(sum));
//        row=sheet.createRow(rowNum+1);
//        row.createCell(3).setCellValue(sum);


        // записываем созданный в памяти Excel документ в файл
        try (FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Public\\file.xls"))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Excel файл успешно создан!");

    }

    // заполнение строки (rowNum) определенного листа (sheet)
    // данными  из dataModel созданного в памяти Excel файла
    private static void createSheetHeader(HSSFSheet sheet, int rowNum, BookExcel book) {
        org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue(book.getId());
        row.createCell(1).setCellValue(book.getName());
        row.createCell(2).setCellValue(book.getDate());
        row.createCell(3).setCellValue(book.getWay());
        row.createCell(4).setCellValue(book.getSafety());
        row.createCell(5).setCellValue(book.getFund());
        row.createCell(6).setCellValue(book.getLocation());
        row.createCell(7).setCellValue(book.getI_date());

        //Cell date=row.createCell(4);
        //  row.createCell(4).setCellStyle(dataStyle);
        //  String s= String.valueOf(rec.getDate());
//        row.createCell(4).setCellValue(String.valueOf(rec.getDate()));

    }

    // заполняем список рандомными данными
    // в реальных приложениях данные будут из БД или интернета
    private static ArrayList<BookExcel> fillData() {
//        RecordList dat=new RecordList();
        ArrayList<BookExcel> r=new ArrayList<BookExcel>();
        try{
            r= DBWorker.excel();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return r;

    }

}
