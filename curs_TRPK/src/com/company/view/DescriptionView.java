package com.company.view;

import com.company.db.DBWorker;
import com.company.model.DescComboBoxModel;
import com.company.model.DescriptionList;
import com.company.model.MusComboBoxModel;
import com.company.model.ModelTable.DescriptionModelTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DescriptionView extends JFrame {
    private JTable table;
    public static DescriptionModelTable tableModel=new DescriptionModelTable(new DescriptionList());
    private JPanel panelTable=new JPanel();
    private JPanel panelMenuTable=new JPanel();

    private JComboBox<String> cbFirst;
    public static DescComboBoxModel cbmodel=new DescComboBoxModel();

    private JLabel labelID=new JLabel("Введите номер музейного предмета:");
    private JLabel labelAvtor=new JLabel("Введите автора:");
    private JLabel labelDesc=new JLabel("Введите историческое описание:");


    private JTextField textID=new JTextField();
    private JTextField textAvtor=new JTextField();
    private JTextField textDesc=new JTextField();


    private JButton buttonAdd=new JButton("Добавить описание");
    private JButton buttonDelete=new JButton("Удалить запись");
    private JButton buttonUpdate=new JButton("Изменить запись");

    //пранировщик для формы
    private GridLayout layoutForm = new GridLayout(1, 2, 5, 12);
    //пранировщик для панели
    private GridLayout layoutPanel = new GridLayout(7, 1, 5, 12);
    public DescriptionView(){
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
        cbFirst=new JComboBox<>();
        cbFirst.setModel(cbmodel);
        panelMenuTable.add(cbFirst);

        panelMenuTable.add(labelAvtor);
        panelMenuTable.add(textAvtor);

        panelMenuTable.add(labelDesc);
        panelMenuTable.add(textDesc);
//        cbFirst=new JComboBox<>();
//        cbFirst.setModel(cbmodel);
//        panelMenuTable.add(cbFirst);


        panelMenuTable.add(buttonAdd);
//        panelMenuTable.add(buttonUpdate);
//        panelMenuTable.add(buttonDelete);
        if(AuthorizationView.user){
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
        if (cbFirst.getSelectedItem()==null || textAvtor.getText().trim().length() == 0 ||
                textDesc.getText().trim().length() == 0  ) {
            JOptionPane.showMessageDialog(null, "Введите значения в поля");
            return false;
        } else return true;

    }


    public class AddListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {

                    if(checkInput()) {
                        DBWorker.insertDescriptionDB(Integer.valueOf(cbFirst.getSelectedItem().toString()), textAvtor.getText(), textDesc.getText());
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
