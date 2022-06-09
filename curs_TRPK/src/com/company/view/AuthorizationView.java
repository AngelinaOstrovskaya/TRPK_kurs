package com.company.view;

import javax.swing.*;
import java.awt.*;

public class AuthorizationView extends JFrame {
    JLabel loginLable = new JLabel("Логин:");
    JLabel passvordLable = new JLabel("Пароль:");
    JTextField loginField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPanel panel=new JPanel();

    JButton loginButton = new JButton("Войти");

    public static boolean user;

    public AuthorizationView() throws HeadlessException {

        super("Авторизация");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);



        loginButton.addActionListener(e -> {
            if (passwordField.getText().equals("accounting") && loginField.getText().equals("accounting")) {
                user = true;
                MainView v = new MainView();
            }
            else if ((passwordField.getText().equals("research") && loginField.getText().equals("research"))){
                user=false;
                MainView v = new MainView();
            }else  JOptionPane.showMessageDialog(null, "Неверный логин или пароль");


        });

        panel.setLayout(new GridLayout(6, 1, 5, 12));

        panel.add(loginLable);
        panel.add(loginField);
        panel.add(passvordLable);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel, BorderLayout.NORTH);
//        frame.add(loginLable);
//        frame.add(loginField);
//        frame.add(passvordLable);
//        frame.add(passwordField);
//        frame.add(loginButton);
        //frame.add(regisrtationButton);
        pack();
    }
}
