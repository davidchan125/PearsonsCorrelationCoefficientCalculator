/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;

/**
 *
 * @author David
 */
public class RegisterUI extends JFrame {
    
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel repeatPasswordLabel;
    private JPasswordField repeatPasswordField;
    private JButton registerButton;
    private JButton signInButton;
    private ActivityLogger activityLogger = new ActivityLogger();
    
    public RegisterUI() {
        initComponents();
    }
    
    public class MyListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String btnLabel = evt.getActionCommand();
            
            if (btnLabel.equals("Register")) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("login_details.txt"));
                    BufferedWriter writer = new BufferedWriter(new FileWriter("login_details.txt", true));
                    String username = usernameTextField.getText();
                    String password = passwordField.getText();
//                    System.out.println(password);
                    String repeatPassword = repeatPasswordField.getText();
//                    System.out.println(repeatPassword);
//                    boolean isEmpty = checkIfFieldsEmpty(username, password, repeatPassword);
//                    boolean checkIfFieldsAreValid = checkIfFieldsAreValid(username, password);
                    boolean validRegistration = validateRegistration(username, password, repeatPassword);
                    if (validRegistration) {
                        String line = null;
                        if ((line = reader.readLine()) != null) {
                            writer.newLine();
                        }
                        for (int i = 0; i < username.length(); i++) {
                            char c = username.charAt(i);
                            if (c >= 'a' && c <= 'm') c += 13;
                            else if (c >= 'A' && c <= 'M') c += 13;
                            else if (c >= 'n' && c <= 'z') c -= 13;
                            else if (c >= 'N' && c <= 'Z') c -= 13;
                            writer.append(c);
            //                System.out.print(c);
                        }
                        writer.append(",");
                        for (int i = 0; i < password.length(); i++) {
                            char c = password.charAt(i);
                            if (c >= 'a' && c <= 'm') c += 13;
                            else if (c >= 'A' && c <= 'M') c += 13;
                            else if (c >= 'n' && c <= 'z') c -= 13;
                            else if (c >= 'N' && c <= 'Z') c -= 13;
                            writer.append(c);
            //                System.out.print(c);
                        }
//                        writer.append(password);
                        
                        reader.close();
                        writer.close();
                        
//                        activityLogger = new ActivityLogger(username);
                        activityLogger.writeToUserLog(username, "registration", "");
                        
                        JOptionPane registrationSuccessWindow = new JOptionPane();
                        JOptionPane.showMessageDialog(null, "Registration successful!");
                        registrationSuccessWindow.setVisible(true);
                        registrationSuccessWindow.setSize(300, 500);
                        
                        setVisible(false);
                        LoginUI loginUI = new LoginUI();
                        Container cp = loginUI.getContentPane();
                        JLabel registrationSuccessLabel = new JLabel("Please try logging in:");
                        cp.add(registrationSuccessLabel, BorderLayout.NORTH);
                        loginUI.setDefaultCloseOperation(loginUI.EXIT_ON_CLOSE);
                        loginUI.setVisible(true);
                        loginUI.setSize(600, 500);
                        loginUI.setMinimumSize(new Dimension(280, 300));
                    }
                } catch (IOException e) {
                    
                }
//                try {
//                    BufferedReader reader = new BufferedReader(new FileReader("login_details.txt"));
//                    BufferedWriter writer = new BufferedWriter(new FileWriter("login_details.txt", true));
//                    String username = usernameTextField.getText();
//                    String password = passwordField.getText();
//                    String repeatPassword = repeatPasswordField.getText();
//                    boolean isEmpty = checkIfFieldsEmpty(username, password, repeatPassword);
//                    boolean checkIfFieldsAreValid = checkIfFieldsAreValid(username, password);
//                    if (!(username.equals(""))) {
//                        boolean userNameTaken = checkIfUsernameTaken(username);
//                        if (userNameTaken) {
//                            
//                        }
//                    } 
//                    if (username.equals("") && password.equals("") && repeatPassword.equals("")) {
//                        usernameLabel.setForeground(new Color(0xFF0000));
//                        passwordLabel.setForeground(new Color(0xFF0000));
//                        repeatPasswordLabel.setForeground(new Color(0xFF0000));
//                        JOptionPane registrationFailureWindow = new JOptionPane();
//                        JOptionPane.showMessageDialog(null, "All fields are empty");
//                        registrationFailureWindow.setVisible(true);
//                        registrationFailureWindow.setSize(300, 500);
//                    } else if (password.equals("")) {
//                        usernameLabel.setForeground(new Color(0x000000));
//                        passwordLabel.setForeground(new Color(0xFF0000));
//                        repeatPasswordLabel.setForeground(new Color(0x000000));
//                        JOptionPane registrationFailureWindow = new JOptionPane();
//                        JOptionPane.showMessageDialog(null, "Please enter a password in the highlighted field");
//                        registrationFailureWindow.setVisible(true);
//                        registrationFailureWindow.setSize(300, 500);
//                    } else if (repeatPassword.equals("")){
//                        usernameLabel.setForeground(new Color(0x000000));
//                        passwordLabel.setForeground(new Color(0x000000));
//                        repeatPasswordLabel.setForeground(new Color(0xFF0000));
//                        JOptionPane registrationFailureWindow = new JOptionPane();
//                        JOptionPane.showMessageDialog(null, "Please re-enter your password in the highlighted field");
//                        registrationFailureWindow.setVisible(true);
//                        registrationFailureWindow.setSize(300, 500);
//                    } else if (!(repeatPassword.equals(password))) {
//                        usernameLabel.setForeground(new Color(0x000000));
//                        passwordLabel.setForeground(new Color(0xFF0000));
//                        repeatPasswordLabel.setForeground(new Color(0xFF0000));
//                        JOptionPane registrationFailureWindow = new JOptionPane();
//                        JOptionPane.showMessageDialog(null, "Passwords do not match");
//                        registrationFailureWindow.setVisible(true);
//                        registrationFailureWindow.setSize(300, 500);
//                    } else if (!(username.equals("") || password.equals("") || repeatPassword.equals(""))) {
//                        String line = null;
//                        if ((line = reader.readLine()) != null) {
//                            writer.newLine();
//                            writer.append(username + ",");
//                            writer.append(password);
//                        } else {
//                            writer.append(username + ",");
//                            writer.append(password);
//                        }
//                        reader.close();
//                        writer.close();
//                        
//                        JOptionPane registrationSuccessWindow = new JOptionPane();
//                        JOptionPane.showMessageDialog(null, "Registration successful!");
//                        registrationSuccessWindow.setVisible(true);
//                        registrationSuccessWindow.setSize(300, 500);
//                        
//                        setVisible(false);
//                        LoginUI loginUI = new LoginUI();
//                        Container cp = loginUI.getContentPane();
//                        JLabel registrationSuccessLabel = new JLabel("Please try logging in:");
//                        cp.add(registrationSuccessLabel, BorderLayout.NORTH);
//                        loginUI.setDefaultCloseOperation(loginUI.EXIT_ON_CLOSE);
//                        loginUI.setVisible(true);
//                        loginUI.setSize(600, 500);
//                        loginUI.setMinimumSize(new Dimension(280, 300));
//                    }
//                } catch (IOException e) {
//                    
//                }
            } else if (btnLabel.equals("Go back")) {
                setVisible(false);
                LoginUI loginUI = new LoginUI();
                loginUI.setDefaultCloseOperation(loginUI.EXIT_ON_CLOSE);
                loginUI.setVisible(true);
                loginUI.setSize(600, 500);
                loginUI.setMinimumSize(new Dimension(280, 300));
            }
        } 
    }
    
    public boolean validateRegistration(String username, String password, String repeatPassword) {
        boolean validRegistration = true;
        // resets colour of labels
        usernameLabel.setForeground(new Color(0x000000));
        passwordLabel.setForeground(new Color(0x000000));
        repeatPasswordLabel.setForeground(new Color(0x000000));
        
        // checks if fields are empty
        // HCI: If any values entered in the field are invalid (e.g. field is empty when a value needs to be entered into it),
        // the label above the text field with the invalid input turns red as a visual cue to signal that the input is wrong.
        if (username.equals("")) {
            usernameLabel.setForeground(new Color(0xFF0000));
            validRegistration = false;
        }
        if (password.equals("")) {
            passwordLabel.setForeground(new Color(0xFF0000));
            validRegistration = false;
        }
        if (repeatPassword.equals("")) {
            repeatPasswordLabel.setForeground(new Color(0xFF0000));
            validRegistration = false;
        }
        // checks if username is taken
        // HCI: when a username is already taken, or the passwords do not match, feedback will be given
        // in the form of a pop-up window, telling the user that the username is already taken
        // or that the passwords do not match.
        try {
            BufferedReader reader = new BufferedReader(new FileReader("login_details.txt"));
            String[] loginDetails;
            String decryptedLine;
            String line = null;
            while ((line = reader.readLine()) != null) {
                decryptedLine = "";
                // Security: Rot13 encryption to improve security
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c >= 'a' && c <= 'm') c += 13;
                    else if (c >= 'A' && c <= 'M') c += 13;
                    else if (c >= 'n' && c <= 'z') c -= 13;
                    else if (c >= 'N' && c <= 'Z') c -= 13;
                    decryptedLine = decryptedLine + c;
    //                System.out.print(c);
                }
                System.out.println(decryptedLine);
                loginDetails = decryptedLine.split(",");
                if (username.equals(loginDetails[0])) {
                    validRegistration = false;
                    usernameLabel.setForeground(new Color(0xFF0000));
                    JOptionPane registrationFailureWindow = new JOptionPane();
                    JOptionPane.showMessageDialog(null, "Username is already taken");
                    registrationFailureWindow.setVisible(true);
                    registrationFailureWindow.setSize(300, 500);
                }
            }
        } catch (IOException e) {
            
        }
        
        // checks if password and reentered password is the same
        
        if (!(repeatPassword.equals(password))) {
            validRegistration = false;
            passwordLabel.setForeground(new Color(0xFF0000));
            repeatPasswordLabel.setForeground(new Color(0xFF0000));
            JOptionPane registrationFailureWindow = new JOptionPane();
            JOptionPane.showMessageDialog(null, "Passwords do not match");
            registrationFailureWindow.setVisible(true);
            registrationFailureWindow.setSize(300, 500);
        }
        
        return validRegistration;
    }
    
//    public boolean checkIfUsernameTaken(String username) {
//        boolean userNameTaken = false;
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("login_details.txt"));
//            String[] loginDetails;
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                loginDetails = line.split(",");
//                if (username.equals(loginDetails[0])) {
//                    userNameTaken = true;
//                    usernameLabel.setForeground(new Color(0xFF0000));
//                    usernameTextField.setForeground(new Color(0xFF0000));
//                    passwordLabel.setForeground(new Color(0x000000));
//                    repeatPasswordLabel.setForeground(new Color(0x000000));
//                    JOptionPane registrationFailureWindow = new JOptionPane();
//                    JOptionPane.showMessageDialog(null, "Username is already taken");
//                    registrationFailureWindow.setVisible(true);
//                    registrationFailureWindow.setSize(300, 500);
//                }
//            }
//        } catch (IOException e) {
//            
//        }
//        return userNameTaken;
//    }
//    
//    public boolean checkIfFieldsAreValid(String username, String repeatPassword) {
//        boolean fieldsValid = true;
//        return fieldsValid;
//    }
//    
//    public boolean checkIfFieldsEmpty(String username, String password, String repeatPassword) {
//        boolean isEmpty = false;
//        if (username.equals("")) {
//            usernameLabel.setForeground(new Color(0xFF0000));
//            isEmpty = true;
//        }
//        if (password.equals("")) {
//            passwordLabel.setForeground(new Color(0xFF0000));
//            isEmpty = true;
//        }
//        if (repeatPassword.equals("")) {
//            repeatPasswordLabel.setForeground(new Color(0xFF0000));
//            isEmpty = true;
//        }
//        return isEmpty;
//    }
    
    public void initComponents() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        JPanel registerUIPanel = new JPanel();
        registerUIPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        registerUIPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        
        JPanel usernameLabelPanel = new JPanel();
        usernameLabelPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        usernameLabel = new JLabel("Username:");
        usernameLabelPanel.add(usernameLabel);
        c.gridx = 0;
        c.gridy = 0;
        registerUIPanel.add(usernameLabelPanel, c);
        
        JPanel usernameTextFieldPanel = new JPanel();
        usernameTextFieldPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        usernameTextField = new JTextField();
        usernameTextField.setPreferredSize(new Dimension(200, 27));
        usernameTextFieldPanel.add(usernameTextField);
        c.gridx = 0;
        c.gridy = 1;
        registerUIPanel.add(usernameTextFieldPanel, c);
        
        JPanel passwordLabelPanel = new JPanel();
        passwordLabelPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        passwordLabel = new JLabel("Password:");
        passwordLabelPanel.add(passwordLabel);
        c.gridx = 0;
        c.gridy = 2;
        registerUIPanel.add(passwordLabelPanel, c);
        
        JPanel passwordFieldPanel = new JPanel();
        passwordFieldPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 27));
        passwordFieldPanel.add(passwordField);
        c.gridx = 0;
        c.gridy = 3;
        registerUIPanel.add(passwordFieldPanel, c);
        
        JPanel repeatPasswordLabelPanel = new JPanel();
        repeatPasswordLabelPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        repeatPasswordLabel = new JLabel("Re-enter password:");
        repeatPasswordLabelPanel.add(repeatPasswordLabel);
        c.gridx = 0;
        c.gridy = 4;
        registerUIPanel.add(repeatPasswordLabelPanel, c);
        
        JPanel repeatPasswordFieldPanel = new JPanel();
        repeatPasswordFieldPanel.setBorder(new EmptyBorder(0, 0, 25, 0));
        repeatPasswordField = new JPasswordField();
        repeatPasswordField.setPreferredSize(new Dimension(200, 27));
        repeatPasswordFieldPanel.add(repeatPasswordField);
        c.gridx = 0;
        c.gridy = 5;
        registerUIPanel.add(repeatPasswordFieldPanel, c);
        
        JPanel registerButtonPanel = new JPanel();
        registerButtonPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(83, 27));
        registerButtonPanel.add(registerButton);
        JPanel orLabelPanel = new JPanel();
        orLabelPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        JLabel orLabel = new JLabel("or");
        orLabelPanel.add(orLabel);
        registerButtonPanel.add(orLabelPanel);
        // HCI: log in button added in case user already has an account
        signInButton = new JButton("Go back");
        signInButton.setPreferredSize(new Dimension(83, 27));
        registerButtonPanel.add(signInButton);
        c.gridx = 0;
        c.gridy = 6;
        registerUIPanel.add(registerButtonPanel, c);
        
        cp.add(registerUIPanel, BorderLayout.CENTER);
        
        MyListener handler = new MyListener();
        registerButton.addActionListener(handler);
        signInButton.addActionListener(handler);
    }
    
//    public static void main(String[] args) {
//        // test
//        RegisterUI registerUI = new RegisterUI();
//        registerUI.setDefaultCloseOperation(registerUI.EXIT_ON_CLOSE);
//        registerUI.setVisible(true);
//        registerUI.setSize(600, 500);
//    }
}
