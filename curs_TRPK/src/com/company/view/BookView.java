package com.company.view;

import com.company.db.DBWorker;
import com.company.model.BookList;
import com.company.model.ModelTable.BookModelTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

public class BookView extends JFrame {
    private JTable table;
    public static BookModelTable tableModel=new BookModelTable(new BookList());
    private JPanel panelTable=new JPanel();
    private JPanel panelMenuTable=new JPanel();




    private JLabel labelDate=new JLabel("Введите время поступления:");
    private JLabel labelWay=new JLabel("Введите способ поступления:");

    private JTextField textDate=new JTextField();
    private JTextField textWay=new JTextField();
    //private JTextField textId=new JTextField();

    private JButton buttonAdd=new JButton("Зарегистрировать");
    private JButton buttonDelete=new JButton("Удалить запись");
    private JButton buttonUpdate=new JButton("Изменить запись");

    //пранировщик для формы
    private GridLayout layoutForm = new GridLayout(1, 2, 5, 12);
    //пранировщик для панели
    private GridLayout layoutPanel = new GridLayout(5, 1, 5, 12);
    public BookView(){
        super("Книга поступлений");
        this.setSize(600,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.setLayout(layoutForm);

        table=new JTable();
        table.setModel(tableModel);
        JScrollPane jScrollPane=new JScrollPane(table);

        panelTable.add(jScrollPane);

        tableModel.connectDB();


        this.add(panelTable);
        this.add(panelMenuTable);

        panelMenuTable.setLayout(layoutPanel);

        panelMenuTable.add(labelDate);
        panelMenuTable.add(textDate);

        panelMenuTable.add(labelWay);
        panelMenuTable.add(textWay);

        panelMenuTable.add(buttonAdd);
//        panelMenuTable.add(buttonUpdate);
//        panelMenuTable.add(buttonDelete);
        if(!AuthorizationView.user){
            buttonAdd.setVisible(false);
        }else{
            buttonAdd.setVisible(true);
        }

        AddListener addListener = new AddListener();
        buttonAdd.addActionListener(addListener);
//
//        DeleteListener deleteListener=new DeleteListener();
//        buttonDelete.addActionListener(deleteListener);
//
//        UpdateListener updateListener=new UpdateListener();
//        buttonUpdate.addActionListener(updateListener);


        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private boolean checkInput() {
        if (textDate.getText().trim().length() == 0 || textWay.getText().trim().length() == 0 ) {
            JOptionPane.showMessageDialog(null, "Введите значения в поля");
            return false;
        } else return true;

    }

    public class AddListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                if(checkInput()) {

                        DBWorker.insertBookDB(Date.valueOf(textDate.getText()), textWay.getText());
                        //tableModel.addPub(num,Integer.parseInt(textOrg.getText()), textDriver.getText(), textCar.getText(),Integer.parseInt(textPay.getText()), Date.valueOf(textDate.getText()));

                        tableModel.connectDB();
                        tableModel.fireTableDataChanged();
                        MainView.cbmodel.setData();

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }
    }
}
