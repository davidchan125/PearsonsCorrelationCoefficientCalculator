/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.border.*;
import java.time.*;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author David
 */
public class ActivityLogger extends JFrame {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private JTable userLogTable = new JTable(0, 0);
    private String username;
    
    public ActivityLogger() {
        initComponents();
    }
    
//    public void setUserName(String username) {
//        this.username = username;
//    }
    
    public void setUserLog(String username) {
        DefaultTableModel userLogModel = new DefaultTableModel();
        int noOfColumns;
        String[] logLine;
        String decryptedLine;
        userLogModel.addColumn("Activity");
        userLogModel.addColumn("Date/time");
        try {
            BufferedReader reader = new BufferedReader(new FileReader("user_log.txt"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                decryptedLine = "";
//                if (line.contains(username)) {
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
                    logLine = decryptedLine.split(",");
                    noOfColumns = logLine.length;
                    for (int i = 0; i < noOfColumns; i++) {
                        logLine[i] = logLine[i].replace("\\s+", "");
                    }
                    userLogModel.addRow(logLine);
//                }
            }
            reader.close();
        } catch (IOException e) {
            
        }
        userLogTable.setModel(userLogModel);
        revalidate();
//        return userLogModel;
    }
    
    public void writeToUserLog(String username, String activity, String extra) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("user_log.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("user_log.txt", true));
            String line = null;
            String appendToLog = "";
            if ((line = reader.readLine()) != null) {
                writer.newLine();
            }
            switch (activity) {
                case "registration":
                    Date registrationTS = new Date();
                    appendToLog = username + " created, " + sdf.format(registrationTS);
                    break;
                case "login":
                    Date loginTS = new Date();
                    appendToLog = username + " logged in, " + sdf.format(loginTS);
                    break;
                case "search":
                    Date searchTS = new Date();
                    appendToLog = username + " searched for " + extra + ", " + sdf.format(searchTS);
                    break;
                case "tableDefault":
                    Date tableDefaultTS = new Date();
                    appendToLog = username + " set table back to default, " + sdf.format(tableDefaultTS);
                    break;
                case "scatter":
                    Date scatterTS = new Date();
                    appendToLog = username + " displayed at a scatter graph of the data, " + sdf.format(scatterTS);
                    break;
                case "analytics":
                    Date analyticsTS = new Date();
                    appendToLog = username + " received a PCC of " + extra + ", " + sdf.format(analyticsTS);
                    break;
                case "userlog":
                    Date userlogTS = new Date();
                    appendToLog = username + " displayed the user log, " + sdf.format(userlogTS);
                    break;
                case "logout":
                    Date logoutTS = new Date();
                    appendToLog = username + " logged out, " + sdf.format(logoutTS);
                    break;
            }
            // Security: Rot13 encryption
            for (int i = 0; i < appendToLog.length(); i++) {
                char c = appendToLog.charAt(i);
                if (c >= 'a' && c <= 'm') c += 13;
                else if (c >= 'A' && c <= 'M') c += 13;
                else if (c >= 'n' && c <= 'z') c -= 13;
                else if (c >= 'N' && c <= 'Z') c -= 13;
                writer.append(c);
//                System.out.print(c);
            }
//            System.out.println(appendToLog);
//            writer.append(appendToLog);
            reader.close();
            writer.close();
        } catch (IOException e) {
            
        }
    }
    
    public void initComponents() {
        Container cp = getContentPane();
        JPanel cpPanel = new JPanel();
        cpPanel.setLayout(new BorderLayout());
        cpPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel userLogTablePanel = new JPanel();
        userLogTablePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
//        DefaultTableModel model = findUserLog(username);
//        userLogTable = new JTable(model);
        userLogTable.setEnabled(false);
//        userLogTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane tableScrPane = new JScrollPane(userLogTable);
        tableScrPane.setPreferredSize(new Dimension(650, 600));
        userLogTablePanel.add(tableScrPane);
        cpPanel.add(userLogTablePanel);
        cp.add(cpPanel);
    }
    
//    public static void main(String[] args) {
//        // test
//        ActivityLogger activityLogger = new ActivityLogger();
//        activityLogger.setDefaultCloseOperation(activityLogger.EXIT_ON_CLOSE);
//        activityLogger.setVisible(true);
//        activityLogger.setSize(750, 730);
//    }
}
