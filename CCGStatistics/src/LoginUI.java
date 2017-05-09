/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author w1576234
 */

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LoginUI extends JFrame {
    
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel loginStatusLabel;
    private ActivityLogger activityLogger = new ActivityLogger();
    
    
    public LoginUI() {
        initComponents();
    }
    
    public class MyListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String btnLabel = evt.getActionCommand();
            
                if (btnLabel.equals("Log in")) {
                    try {
                        int loginStatus = validateLogin();

                        switch (loginStatus) {
                            case 1:
                                loginStatusLabel.setText("No username or password entered.");
                                break;
                            case 2:
                                loginStatusLabel.setText("No username entered.");
                                break;
                            case 3:
                                loginStatusLabel.setText("No password entered.");
                                break;
                            case 4:
                                loginStatusLabel.setText("Please enter a valid username and password.");
                                break;
                            case 0:
                                activityLogger = new ActivityLogger();
                                activityLogger.writeToUserLog(usernameTextField.getText(), "login", "");
                                CCGManager ccgManager = new CCGManager(usernameTextField.getText());
    //                            ccgManager.setUser(usernameTextField.getText());
                                setVisible(false);
    //                            CCGManager ccgManager = new CCGManager();
                                ccgManager.setDefaultCloseOperation(ccgManager.EXIT_ON_CLOSE);
                                ccgManager.setVisible(true);
                                ccgManager.setSize(700, 730);
    //                            ccgManager.setMinimumSize(new Dimension(700, 730));
                                ccgManager.setResizable(false);
                                break;
                        }
                    } catch (IOException e) {

                    }
                } else if (btnLabel.equals("Register")) {
                    setVisible(false);
                    RegisterUI registerUI = new RegisterUI();
                    registerUI.setDefaultCloseOperation(registerUI.EXIT_ON_CLOSE);
                    registerUI.setVisible(true);
                    registerUI.setSize(600, 500);
                } else {
                    try {
                        int loginStatus = validateLogin();

                        switch (loginStatus) {
                            case 1:
                                loginStatusLabel.setText("No username or password entered.");
                                break;
                            case 2:
                                loginStatusLabel.setText("No username entered.");
                                break;
                            case 3:
                                loginStatusLabel.setText("No password entered.");
                                break;
                            case 4:
                                loginStatusLabel.setText("Please enter a valid username and password.");
                                break;
                            case 0:
                                activityLogger = new ActivityLogger();
                                activityLogger.writeToUserLog(usernameTextField.getText(), "login", "");
                                CCGManager ccgManager = new CCGManager(usernameTextField.getText());
    //                            ccgManager.setUser(usernameTextField.getText());
                                setVisible(false);
    //                            CCGManager ccgManager = new CCGManager();
                                ccgManager.setDefaultCloseOperation(ccgManager.EXIT_ON_CLOSE);
                                ccgManager.setVisible(true);
                                ccgManager.setSize(700, 730);
    //                            ccgManager.setMinimumSize(new Dimension(700, 730));
                                ccgManager.setResizable(false);
                                break;
                        }
                    } catch (IOException e) {

                    }
                }
            
            
        }
    }
    
    public int validateLogin() throws IOException {
        int validLogin = -1;
        String[] loginDetails;
        String decryptedLine;
        
        BufferedReader reader = new BufferedReader(new FileReader("login_details.txt"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            decryptedLine = "";
            // Security: Rot13 decryption
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c >= 'a' && c <= 'm') c += 13;
                else if (c >= 'A' && c <= 'M') c += 13;
                else if (c >= 'n' && c <= 'z') c -= 13;
                else if (c >= 'N' && c <= 'Z') c -= 13;
                decryptedLine = decryptedLine + c;
//                System.out.print(c);
            }
            // testing purposes
//            System.out.println(decryptedLine);
            loginDetails = decryptedLine.split(",");
            if (usernameTextField.getText().equals(loginDetails[0]) && passwordField.getText().equals(loginDetails[1])) {
                validLogin = 0;
                return validLogin;
            } else {
                validLogin = 4;
            }
        }
        
        if (usernameTextField.getText().equals("") && passwordField.getText().equals("")) {
            validLogin = 1;
        } else if (usernameTextField.getText().equals("")) {
            validLogin = 2;
        } else if (passwordField.getText().equals("")) {
            validLogin = 3;
        } else {
            validLogin = 4;
        }
        
        return validLogin;
    }
    
    public void initComponents() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
//        cp.setLayout(new BorderLayout());
        JPanel loginUIPanel = new JPanel();
        loginUIPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        loginUIPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        
        JPanel usernameLabelPanel = new JPanel();
        usernameLabelPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        usernameLabel = new JLabel("Username:");
        usernameLabelPanel.add(usernameLabel);
        c.gridx = 0;
        c.gridy = 0;
        loginUIPanel.add(usernameLabelPanel, c);
        
        JPanel usernameTextFieldPanel = new JPanel();
        usernameTextFieldPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        usernameTextField = new JTextField();
        usernameTextField.setPreferredSize(new Dimension(200, 27));
        usernameTextFieldPanel.add(usernameTextField);
        c.gridx = 0;
        c.gridy = 1;
        loginUIPanel.add(usernameTextFieldPanel, c);
        
        JPanel passwordLabelPanel = new JPanel();
        passwordLabelPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        passwordLabel = new JLabel("Password:");
        passwordLabelPanel.add(passwordLabel);
        c.gridx = 0;
        c.gridy = 2;
        loginUIPanel.add(passwordLabelPanel, c);
        
        JPanel passwordFieldPanel = new JPanel();
        passwordFieldPanel.setBorder(new EmptyBorder(0, 0, 25, 0));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 27));
        passwordFieldPanel.add(passwordField);
        c.gridx = 0;
        c.gridy = 3;
        loginUIPanel.add(passwordFieldPanel, c);
        
        JPanel loginButtonPanel = new JPanel();
        loginButtonPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        JButton loginButton = new JButton("Log in");
        loginButton.setPreferredSize(new Dimension(90, 27));
        loginButtonPanel.add(loginButton);
        JPanel registerButtonPanel = new JPanel();
        registerButtonPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
        
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(90, 27));
        registerButtonPanel.add(registerButton);
        loginButtonPanel.add(registerButtonPanel);
        c.gridx = 0;
        c.gridy = 4;
        loginUIPanel.add(loginButtonPanel, c);
        
        JPanel loginStatusLabelPanel = new JPanel();
        loginStatusLabelPanel.setBorder(new EmptyBorder(0, 0, 0, 10));
        loginStatusLabel = new JLabel(" ");
        loginStatusLabelPanel.add(loginStatusLabel);
        c.gridx = 0;
        c.gridy = 5;
        loginUIPanel.add(loginStatusLabelPanel, c);
        
        cp.add(loginUIPanel, BorderLayout.CENTER);
        
        MyListener handler = new MyListener();
        loginButton.addActionListener(handler);
        registerButton.addActionListener(handler);
        usernameTextField.addActionListener(handler);
        passwordField.addActionListener(handler);
    }
    
    public static void main(String[] args) {
        LoginUI loginUI = new LoginUI();
        loginUI.setDefaultCloseOperation(loginUI.EXIT_ON_CLOSE);
        loginUI.setVisible(true);
        loginUI.setSize(600, 500);
        loginUI.setMinimumSize(new Dimension(280, 300));
//        loginUI.setResizable(false);
    }
    
}
