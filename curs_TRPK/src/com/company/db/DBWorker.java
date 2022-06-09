package com.company.db;

import com.company.model.*;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DBWorker {
    public static Connection pub;
    public static Statement state; //используется для выполнения SQL-запросов
    public static ResultSet result;
    public static BookList booklist=new BookList();
    public static MuseumObjectList muslist=new MuseumObjectList();
    public static InventoryList invlist=new InventoryList();
    public static DescriptionList desclist=new DescriptionList();

    private static Properties connectionProperties = new Properties();

    public static BookList getArr() {
        return booklist;
    }
    public static MuseumObjectList getArrMus() {
        return muslist;
    }
    public static InventoryList getArrInv() {
        return invlist;
    }
    public static DescriptionList getArrDesc() {
        return desclist;
    }

    public static void connectionBD() throws ClassNotFoundException, SQLException {
        pub = null;
        Class.forName("org.sqlite.JDBC");  //("имя движка") вызывает динамическую загрузку класса, org.sqlite.JDBC принадлежит к jar sqlite-jdbc

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        connectionProperties = config.toProperties();
        pub = DriverManager.getConnection("jdbc:sqlite:dataMuseum1.s3db",connectionProperties); //("протокол:движок:имя_файла_БД")находит java.sql.Driver соответствующей базы данных и вызывает у него метод connect (метод connect всегда создает базу данных заранее)
        System.out.println("БД подключена!");
    }
    public static void newTable() throws ClassNotFoundException, SQLException {
        //создание экземпляра класса Statement
        state = pub.createStatement();
        state.execute("CREATE TABLE if not exists BookIncome (\n" +
                "  B_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  B_time DATE NULL,\n" +
                "  B_way VARCHAR NULL\n" +
                ");\n");
        state.execute("CREATE TABLE if not exists MuseumObject (\n" +
                "  BookIncome_B_id INTEGER NOT NULL,\n" +
                "  M_name VARCHAR(255) NOT NULL ,\n" +
                "  M_size VARCHAR(255) NULL,\n" +
                "  M_material VARCHAR(255) NULL,\n" +
                "  M_safety VARCHAR NULL,\n" +
                "  PRIMARY KEY(BookIncome_B_id),\n" +
                "  FOREIGN KEY(BookIncome_B_id)\n" +
                "    REFERENCES BookIncome(B_id)\n" +
                "      ON DELETE NO ACTION\n" +
                "      ON UPDATE NO ACTION\n" +
                ");\n");
//        state.execute("CREATE TABLE if not exists Fund (\n" +
//                "  idFund INTEGER AUTO_INCREMENT,\n" +
//                "  F_name VARCHAR(255) NULL,\n" +
//                "  PRIMARY KEY(idFund)\n" +
//                ");\n");
//        state.execute("INSERT INTO Fund(F_name) VALUES\n" +
//                "('Художественный'),\n" +
//                "('Скульптурный'),\n" +
//                "('Графики');");
//        state.execute("CREATE TABLE if not exists Location (\n" +
//                "  idLocation INTEGER AUTO_INCREMENT,\n" +
//                "  L_name VARCHAR(45) NULL,\n" +
//                "  PRIMARY KEY(idLocation)\n" +
//                ");\n");
//        state.execute("INSERT INTO Location(L_name) VALUES\n" +
//                "('Выставка 1'),\n" +
//                "('Шкаф 1'),\n" +
//                "('Стол 1');");

        state.execute("CREATE TABLE if not exists Inventory (\n" +
                "  Fund_idFund VARCHAR(255) NOT NULL,\n" +
                "  Location_idLocation INTEGER UNSIGNED NOT NULL,\n" +
                "  MuseumObject_BookIncome_B_id INTEGER NOT NULL,\n" +
                "  I_date DATE NULL,\n" +
                "  PRIMARY KEY(MuseumObject_BookIncome_B_id),\n" +
                "  FOREIGN KEY(MuseumObject_BookIncome_B_id)\n" +
                "    REFERENCES MuseumObject(BookIncome_B_id)\n" +
                "      ON DELETE NO ACTION\n" +
                "      ON UPDATE NO ACTION\n" +
                ");\n");
        state.execute("CREATE TABLE if not exists Description (\n" +
                "  MuseumObject_BookIncome_B_id INTEGER NOT NULL,\n" +
                "  D_avtor VARCHAR(255) NULL,\n" +
                "  D_description VARCHAR NULL,\n" +
                "  PRIMARY KEY(MuseumObject_BookIncome_B_id),\n" +
                "  FOREIGN KEY(MuseumObject_BookIncome_B_id)\n" +
                "    REFERENCES MuseumObject(BookIncome_B_id)\n" +
                "      ON DELETE NO ACTION\n" +
                "      ON UPDATE NO ACTION\n" +
                ");");
        System.out.println("Таблица существует");
    }

    public static void insertBookDB(Date date, String way) throws SQLException {
        PreparedStatement prep = pub.prepareStatement("INSERT INTO 'BookIncome' ('B_time', 'B_way') VALUES (?, ?) ;");
        prep.setDate(1, date);
        prep.setString(2, way);
        prep.execute();
    }


    public static void insertMuseumDB(String b_number, String name, String size, String material, String safety ) throws SQLException {
        PreparedStatement prep = pub.prepareStatement("INSERT INTO 'MuseumObject' ( 'BookIncome_B_id', 'M_name', 'M_size', 'M_material', 'M_safety') VALUES (?, ?, ?, ?, ?) ;");
        prep.setString(1,  b_number);
        prep.setString(2, name);
        prep.setString(3, size);
        prep.setString(4, material);
        prep.setString(5, safety);
        prep.execute();
    }

    public static void insertInventoryDB(Integer id, String fund, Integer loc, Date date ) throws SQLException {

        PreparedStatement prep = pub.prepareStatement("INSERT INTO 'Inventory' ( 'Fund_idFund', 'Location_idLocation', 'MuseumObject_BookIncome_B_id', 'I_date') VALUES (?, ?, ?, ?) ;");

        prep.setString(1, fund);
        prep.setInt(2, loc);
        prep.setInt(3, id);
        prep.setDate(4, date);
        prep.execute();

    }
    public static void insertDescriptionDB(Integer id, String avtor, String desc ) throws SQLException {

        PreparedStatement prep = pub.prepareStatement("INSERT INTO 'Description' ( 'MuseumObject_BookIncome_B_id', 'D_avtor', 'D_description') VALUES (?, ?, ?) ;");

        prep.setInt(1, id);
        prep.setString(2, avtor);
        prep.setString(3, desc);
        prep.execute();

    }

    public static void updateMuseumDB(Integer number,String b_number, String name, String size, String material, String safety) throws SQLException {

        PreparedStatement prep = pub.prepareStatement("UPDATE MuseumObject SET BookIncome_B_id=?, M_name=?, M_size=?, M_material=?, M_safety=? WHERE BookIncome_B_id = ?");
        prep.setString(1,  b_number);
        prep.setString(2, name);
        prep.setString(3, size);
        prep.setString(4, material);
        prep.setString(5, safety);
        prep.setInt(6, number);
        prep.execute();
        System.out.println("Запись изменена");
    }
    public static void deleteMuseum(int id) throws SQLException {
        PreparedStatement del = pub.prepareStatement("DELETE FROM MuseumObject WHERE BookIncome_B_id = ?");
        del.setObject(1, id);
        del.execute();
        System.out.println("Запись удалена"+id);
    }

    // вывод данных из таблицы
    public static void readDB() throws ClassNotFoundException, SQLException {

        BookIncome book;
        result = state.executeQuery("SELECT * FROM BookIncome"); //выборки данных с помощью команды SELECT
        while (result.next()) {
            book= new BookIncome(result.getInt( "B_id"),result.getDate("B_time"),result.getString("B_way"));
            booklist.bookList.add(book);
        }

        System.out.println("Таблица выгружена");
    }

    public static void readMuseumDB() throws ClassNotFoundException, SQLException {

        MuseumObject mus;
        result = state.executeQuery("SELECT * FROM MuseumObject"); //выборки данных с помощью команды SELECT
        while (result.next()) {
            mus= new MuseumObject(result.getInt( "BookIncome_B_id"),result.getString("M_name"),result.getString("M_size"), result.getString("M_material"), result.getString("M_safety"));
            muslist.museumList.add(mus);
        }
        System.out.println("Таблица выгружена");
    }
    public static void readInventoryDB() throws ClassNotFoundException, SQLException {

        Inventory mus;
        result = state.executeQuery("SELECT * FROM Inventory"); //выборки данных с помощью команды SELECT
        while (result.next()) {
            mus= new Inventory(result.getInt( "Fund_idFund"),result.getInt( "Location_idLocation"),result.getInt( "MuseumObject_BookIncome_B_id"),result.getDate("I_date"));
            invlist.invList.add(mus);
        }
        System.out.println("Таблица выгружена");
    }
    public static void readDescriptionDB() throws ClassNotFoundException, SQLException {

        Description mus;
        result = state.executeQuery("SELECT * FROM Description"); //выборки данных с помощью команды SELECT
        while (result.next()) {
            mus= new Description(result.getInt( "MuseumObject_BookIncome_B_id"),result.getString( "D_avtor"),result.getString( "D_description"));
            desclist.invList.add(mus);
        }
        System.out.println("Таблица выгружена");
    }
    public static ArrayList<String> cbselect() throws ClassNotFoundException, SQLException {

        ArrayList<String> list = new ArrayList<>();
        result = DBWorker.state.executeQuery("SELECT idLocation FROM Location"); //выборки данных с помощью команды SELECT
        while (result.next()) {

            list.add(result.getString("idLocation"));
            System.out.println(result.getString("idLocation"));
            //pubList.Publications.add(publication);

        }
        System.out.println("Таблица выгружена");
        return list;
    }

    public static ArrayList<String> cbBookSelect() throws ClassNotFoundException, SQLException {

        ArrayList<String> list = new ArrayList<>();
        result = DBWorker.state.executeQuery("SELECT * FROM BookIncome WHERE B_id not in (SELECT BookIncome_B_id FROM MuseumObject)"); //выборки данных с помощью команды SELECT
        while (result.next()) {

            list.add(result.getString("B_id"));
            System.out.println(result.getString("B_id"));
            //pubList.Publications.add(publication);

        }
        System.out.println("Таблица выгружена");
        return list;
    }
    public static ArrayList<String> cbMusSelect() throws ClassNotFoundException, SQLException {

        ArrayList<String> list = new ArrayList<>();
        result = DBWorker.state.executeQuery("SELECT * FROM MuseumObject WHERE BookIncome_B_id not in (SELECT MuseumObject_BookIncome_B_id FROM Inventory)"); //выборки данных с помощью команды SELECT
        while (result.next()) {

            list.add(result.getString("BookIncome_B_id"));
            System.out.println(result.getString("BookIncome_B_id"));
            //pubList.Publications.add(publication);

        }
        System.out.println("Таблица выгружена");
        return list;
    }
    public static ArrayList<String> cbDescSelect() throws ClassNotFoundException, SQLException {

        ArrayList<String> list = new ArrayList<>();
        result = DBWorker.state.executeQuery("SELECT * FROM MuseumObject WHERE BookIncome_B_id not in (SELECT MuseumObject_BookIncome_B_id FROM Description)"); //выборки данных с помощью команды SELECT
        while (result.next()) {

            list.add(result.getString("BookIncome_B_id"));
            System.out.println(result.getString("BookIncome_B_id"));
            //pubList.Publications.add(publication);

        }
        System.out.println("Таблица выгружена");
        return list;
    }
    public static ArrayList<BookExcel> excel() throws SQLException {
        BookExcel exc;
        ArrayList<BookExcel> data=new ArrayList<BookExcel>();
        PreparedStatement fltr = pub.prepareStatement("SELECT BookIncome.B_id, MuseumObject.M_name , BookIncome.B_time, BookIncome.B_way, MuseumObject.M_safety, Inventory.Fund_idFund, Inventory.Location_idLocation, Inventory.I_date   \n" +
                "FROM BookIncome, MuseumObject, Inventory \n" +
                "WHERE BookIncome.B_id = MuseumObject.BookIncome_B_id AND BookIncome.B_id=Inventory.MuseumObject_BookIncome_B_id ");

        result = fltr.executeQuery();
        while (result.next()) {
            exc = new BookExcel(result.getInt("B_id"), result.getString("M_name"),
                    result.getDate("B_time"), result.getString("B_way"),
                    result.getString("M_safety"),result.getString("Fund_idFund"),
                    result.getString("Location_idLocation"),result.getDate("I_date"));
            data.add(exc);
            //Reclist.Rec.add(rec);
        }

        System.out.println("Запрос для экселя");
        return  data;
    }

    //закрытие БД
    public static void closeDB() throws ClassNotFoundException, SQLException {
        result.close();
        state.close();
        pub.close();

        System.out.println("Соединения закрыты");
    }

}
