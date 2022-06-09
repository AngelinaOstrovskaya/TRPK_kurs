package com.company.view;

import com.company.db.DBWorker;
import com.company.model.InventoryList;
import com.company.model.MusComboBoxModel;
import com.company.model.ModelTable.InventoryModelTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

public class InventoryView extends JFrame {
    private JTable table;
    public static InventoryModelTable tableModel=new InventoryModelTable(new InventoryList());
    private JPanel panelTable=new JPanel();
    private JPanel panelMenuTable=new JPanel();

    private JComboBox<String> cbFirst;
    public static MusComboBoxModel cbmodel=new MusComboBoxModel();

    private JComboBox<String> cbFund;
    private DefaultComboBoxModel model= new DefaultComboBoxModel();

    private JLabel labelFund=new JLabel("Введите фонд:");
    private JLabel labelLoc=new JLabel("Введите место хранения:");
    private JLabel labelDate=new JLabel("Введите время поступления:");
    private JLabel labelID=new JLabel("Введите номер музейного предмета:");

    private JTextField textFund=new JTextField();
    private JTextField textLoc=new JTextField();
    private JTextField textDate=new JTextField();
    private JTextField textId=new JTextField();

    private JButton buttonAdd=new JButton("Добавить запись");
    private JButton buttonDelete=new JButton("Удалить запись");
    private JButton buttonUpdate=new JButton("Изменить запись");

    //пранировщик для формы
    private GridLayout layoutForm = new GridLayout(1, 2, 5, 12);
    //пранировщик для панели
    private GridLayout layoutPanel = new GridLayout(9, 1, 5, 12);
    public InventoryView(){
        super("Инвентарь");
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

        panelMenuTable.add(labelID);
//        panelMenuTable.add(textId);
                cbFirst=new JComboBox<>();
        cbFirst.setModel(cbmodel);
        panelMenuTable.add(cbFirst);

        panelMenuTable.add(labelFund);
//        panelMenuTable.add(textFund);

        cbFund=new JComboBox<>();
        model.addElement("Художественный");
        model.addElement("Скульптурный");


        cbFund.setModel(model);
        panelMenuTable.add(cbFund);

        panelMenuTable.add(labelLoc);
        panelMenuTable.add(textLoc);


        panelMenuTable.add(labelDate);
        panelMenuTable.add(textDate);

        panelMenuTable.add(buttonAdd);
//        panelMenuTable.add(buttonUpdate);
//        panelMenuTable.add(buttonDelete);


        AddListener addListener = new AddListener();
        buttonAdd.addActionListener(addListener);
//
//        DeleteListener deleteListener=new DeleteListener();
//        buttonDelete.addActionListener(deleteListener);
//
//        UpdateListener updateListener=new UpdateListener();
//        buttonUpdate.addActionListener(updateListener);
        if(!AuthorizationView.user){
            buttonAdd.setVisible(false);
        }else{
            buttonAdd.setVisible(true);
        }

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private boolean checkInput() {
        if (cbFirst.getSelectedItem()==null || cbFund.getSelectedItem()==null ||
                textLoc.getText().trim().length() == 0 || textDate.getText().trim().length() == 0 ) {
            JOptionPane.showMessageDialog(null, "Введите значения в поля");
            return false;
        } else return true;

    }

    public class AddListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {

                if(checkInput()) {
                    DBWorker.insertInventoryDB(Integer.valueOf(cbFirst.getSelectedItem().toString()), cbFund.getSelectedItem().toString(), Integer.valueOf(textLoc.getText()), Date.valueOf(textDate.getText()));
                    //tableModel.addPub(num,Integer.parseInt(textOrg.getText()), textDriver.getText(), textCar.getText(),Integer.parseInt(textPay.getText()), Date.valueOf(textDate.getText()));

                    tableModel.connectDB();
                    tableModel.fireTableDataChanged();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }
    }
}
