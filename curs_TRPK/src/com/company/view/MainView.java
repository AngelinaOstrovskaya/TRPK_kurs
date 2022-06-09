package com.company.view;

import com.company.db.DBWorker;
import com.company.excel.BookExcelWorker;
import com.company.model.BookComboBoxModel;
import com.company.model.BookList;
import com.company.model.ModelTable.BookModelTable;
import com.company.model.ModelTable.MuseumModelTable;
import com.company.model.MuseumObjectList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;

public class MainView extends JFrame {
    private JTable table;
    public static MuseumModelTable tableModel=new MuseumModelTable(new MuseumObjectList());

    private JPanel panel1=new JPanel();


    private JPanel panelTable=new JPanel();
    private JPanel panelMenuTable=new JPanel();
    private JPanel panelMenu=new JPanel();

    private JLabel labelNumber=new JLabel("Введите номер:");
    private JLabel labelName=new JLabel("Введите наименование:");
    private JLabel labelSize=new JLabel("Введите размер:");
    private JLabel labelMaterial=new JLabel("Введите Материал:");
    private JLabel labelSafety=new JLabel("Введите сохранность:");

    private JComboBox<String> cbFirst;
 public static BookComboBoxModel cbmodel=new BookComboBoxModel();

    private JTextField textNumber=new JTextField();
    private JTextField textName=new JTextField();
    private JTextField textSize=new JTextField();
    private JTextField textMaterial=new JTextField();
    private JTextField textSafety=new JTextField();

    private JButton buttonAdd=new JButton("Добавить запись");
    private JButton buttonDelete=new JButton("Удалить запись");
    private JButton buttonUpdate=new JButton("Изменить запись");
//    private JButton buttonFilter=new JButton("Фильтр");

    private JButton buttonOrg=new JButton("Книга поступлений");
    private JButton buttonCar=new JButton("Инвентарь");
    private JButton buttonDesc=new JButton("Научное описание");
    private JButton buttonExel=new JButton("Инвентаризация");


    //пранировщик для формы
    private GridLayout layoutForm = new GridLayout(1, 2, 5, 12);
    //пранировщик для панели
    private GridLayout layoutPanel = new GridLayout(11, 1, 5, 12);
    private GridLayout layoutPanelЕ = new GridLayout(2, 1, 5, 12);
    private GridLayout layoutP= new GridLayout(1, 3, 5, 12);
    private BoxLayout layoutT=new BoxLayout(getContentPane() ,BoxLayout.X_AXIS);
    private BoxLayout layout=new BoxLayout(getContentPane() ,BoxLayout.Y_AXIS);

    public MainView()  {
        super("Музейные предметы");
        this.setSize(500,500);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // this.setLayout(layoutForm);
        this.setLayout(layout);
        //panelTable.setLayout(new FlowLayout(FlowLayout.CENTER));
        // panelTable.setLayout();

        table=new JTable();
        table.setModel(tableModel);
        JScrollPane jScrollPane=new JScrollPane(table);
        panelTable.add(jScrollPane);

        this.add(panelMenu);
        this.add(panel1);
        panel1.setLayout(layoutForm);
        panel1.add(panelTable);
        panel1.add(panelMenuTable);
        // panelTable.add(panelMenu);
        //setLayout(layoutP);
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.X_AXIS));
        panelMenu.add(buttonOrg);
        panelMenu.add(buttonCar);
        panelMenu.add(buttonDesc);
        panelMenu.add(buttonExel);
        tableModel.connectDB();

        panelMenuTable.setLayout(layoutPanel);

        panelMenuTable.add(labelNumber);
//        panelMenuTable.add(textNumber);
        cbFirst=new JComboBox<>();
        cbFirst.setModel(cbmodel);
        panelMenuTable.add(cbFirst);

        panelMenuTable.add(labelName);
        panelMenuTable.add(textName);

        panelMenuTable.add(labelSize);
        panelMenuTable.add(textSize);

        panelMenuTable.add(labelMaterial);
        panelMenuTable.add(textMaterial);

        panelMenuTable.add(labelSafety);
        panelMenuTable.add(textSafety);

        panelMenuTable.add(buttonAdd);
//        panelMenuTable.add(buttonUpdate);
//        panelMenuTable.add(buttonDelete);
//        panelMenuTable.add(buttonFilter);
//
        if(AuthorizationView.user){
            buttonAdd.setVisible(false);
        }else{
            buttonAdd.setVisible(true);
        }
        AddListener addListener = new AddListener();
        buttonAdd.addActionListener(addListener);
//
        DeleteListener deleteListener=new DeleteListener();
        buttonDelete.addActionListener(deleteListener);
//
        UpdateListener updateListener=new UpdateListener();
        buttonUpdate.addActionListener(updateListener);
//
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    DBWorker.closeDB();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        buttonOrg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookView v=new BookView();
            }
        });

        buttonCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InventoryView v=new InventoryView();
            }
        });
        buttonDesc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DescriptionView v=new DescriptionView();
            }
        });
        buttonExel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookExcelWorker v=new BookExcelWorker();
            }
        });
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }


    private boolean checkInput() {
        if (cbFirst.getSelectedItem()==null || textName.getText().trim().length() == 0 ||
                textSize.getText().trim().length() == 0 || textMaterial.getText().trim().length() == 0 ||
                textSafety.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Введите значения в поля");
            return false;
        } else return true;

    }
    public class AddListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            try {

                if(checkInput()) {
                    DBWorker.insertMuseumDB(cbFirst.getSelectedItem().toString(), textName.getText(), textSize.getText(), textMaterial.getText(), textSafety.getText());

                    tableModel.connectDB();
                    tableModel.fireTableDataChanged();
                    cbmodel.setData();
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }
    }
    public Object GetIndex(){
        Object o= table.getValueAt(table.getSelectedRow(),0);

        return o;
    }
    public class UpdateListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            try {
                if(checkInput()) {
                            int num = (int) GetIndex();
                            DBWorker.updateMuseumDB(num,textNumber.getText(), textName.getText(), textSize.getText(), textMaterial.getText(), textSafety.getText());
                            JOptionPane.showMessageDialog(null, "Запись успешно изменена");
                            tableModel.connectDB();
                            tableModel.fireTableDataChanged();
                            cbmodel.setData();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "Выберите запись");
            }


        }
    }
    public class DeleteListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            try {
                int num= (int) GetIndex();
                DBWorker.deleteMuseum(num);
                System.out.println(num);
                JOptionPane.showMessageDialog(null, "Запись успешно удалёна");
                tableModel.connectDB();
                tableModel.fireTableDataChanged();
                cbmodel.setData();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "Выберите запись");
            }


        }
    }
}
