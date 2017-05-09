/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CCGManager extends JFrame {
    
//    private static CCGManager ccgManager;
    
    private JTable dataTable = new JTable(0, 0);
    private JTextField results;
    private JTextField searchArea;
    private JLabel currentUserLabel;
    private ArrayList<Long> xList = new ArrayList<Long>(); // column 1
    private ArrayList<Long> yList = new ArrayList<Long>(); // column 2
    private int xListSize = 0;
    private int yListSize = 0;
    private String username;
    private ActivityLogger activityLogger = new ActivityLogger();
    
    public CCGManager(String username) throws FileNotFoundException {
        this.username = username;
        // initialises components for JFrame
        initComponents();
    }
    
//    public void setUser(String username) {
//        this.username = username;
//        currentUserLabel.setText("Currently logged in as: " + username);
//    }
    
    public DefaultTableModel initialiseTable() throws FileNotFoundException {
        DefaultTableModel model = new DefaultTableModel();
        int noOfColumns;
        String[] columnNames, row;
        BufferedReader reader = new BufferedReader(new FileReader("1734_ccg-reg-patients-1.csv"));
        int j = 0;
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                if (j == 0) {
                    columnNames = line.split (",");
                    noOfColumns = columnNames.length;
                    for (int i = 0; i < noOfColumns; i++) {
                        columnNames[i] = columnNames[i].replace("\"", "");
                        model.addColumn(columnNames[i]);
                    }
                } else {
                    row = line.split(",");
                    noOfColumns = row.length;
                    for (int i = 0; i < noOfColumns; i++) {
                        row[i] = row[i].replace("\"", "");
                    }
                    model.addRow(row);
                }
                j++;
            }
//            System.out.println("J: " + j);
            xListSize = j - 1; // j - 1 to exclude including column
            yListSize = j - 1; // j - 1 to exclude including column
        } catch (IOException e) {
            
        }
        return model;
    }
    
    public DefaultTableModel refreshTable(String searchQuery) throws FileNotFoundException {
        DefaultTableModel searchModel = new DefaultTableModel();
        int noOfColumns;
        int noOfRows = 0;
        String[] columnNames, row;
        BufferedReader reader = new BufferedReader(new FileReader("1734_ccg-reg-patients-1.csv"));
        int j = 0;
        String line = null;
        if (searchQuery.equals("")) {
            searchModel = initialiseTable();
        } else {
            try {
                while ((line = reader.readLine()) != null) {
                    if (j == 0) {
                        columnNames = line.split(",");
                        noOfColumns = columnNames.length;
                        for (int i = 0; i < noOfColumns; i++) {
                            columnNames[i] = columnNames[i].replace("\"", "");
                            searchModel.addColumn(columnNames[i]);
                        }
                    } else {
                        row = line.split(",");
                        noOfColumns = row.length;
                        for (int i = 0; i < noOfColumns; i++) {
                            row[i] = row[i].replace("\"", "");
                        }
                        for (int i = 0; i < noOfColumns; i++) {
                            if (row[i].toUpperCase().contains((searchQuery.toUpperCase()))) {
                                searchModel.addRow(row);
                                noOfRows++;
                                break;
                            }
                        }
                    }
                    j++;
                }
//                System.out.println("No. of rows added: " + noOfRows);
                xListSize = noOfRows; // columns aren't included in noOfRows unlike variable j.
                yListSize = noOfRows; // columns aren't included in noOfRows unlike variable j.
            } catch (IOException e) {

            }
        }
        return searchModel;
    }
    
    private class MyListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String btnLabel = evt.getActionCommand();
            
            if (btnLabel.equals("Search")) {
                String searchQuery = searchArea.getText();
                if (searchQuery.equals("")) {
                    activityLogger.writeToUserLog(username, "tableDefault", "");
                } else {
                    activityLogger.writeToUserLog(username, "search", searchQuery);
                }
                try {
                    DefaultTableModel searchModel = refreshTable(searchQuery);
                    dataTable.setModel(searchModel);
                    if (xListSize <= 1 || yListSize <= 1) {
                        results.setText("");
                        JOptionPane errorWindow = new JOptionPane();
                        JOptionPane.showMessageDialog(null, "Error: not enough values available to calculate correlation.");
                        errorWindow.setVisible(true);
                        errorWindow.setSize(300, 500);
                    } else {
                        calculateCorrelation();
                    }
                } catch (FileNotFoundException e) {
                    
                }
            } else if (btnLabel.equals("Show scatter graph")) {
//                System.out.println("xList size: " + xList.size());
//                System.out.println("yList size: " + yList.size());
                if (xListSize <= 1 || yListSize <= 1) {
                    JOptionPane errorWindow = new JOptionPane();
                    JOptionPane.showMessageDialog(null, "Error: unable to view graphical representation as correlation cannot be calculated.");
                    errorWindow.setVisible(true);
                    errorWindow.setSize(300, 500);
                } else {
                    activityLogger.writeToUserLog(username, "scatter", "");
                    CorrelationGraphics lines = new CorrelationGraphics();
                    lines.setCoordinateValues(xList, yList);
                    JFrame correlationGraphicsFrame = new JFrame("Scatter Graph");
    //                correlationGraphicsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    correlationGraphicsFrame.add(lines);
                    correlationGraphicsFrame.setSize(700, 700);
                    correlationGraphicsFrame.setResizable(false);
                    correlationGraphicsFrame.setLocationRelativeTo(null);
                    correlationGraphicsFrame.setVisible(true);
                }
            } else if (btnLabel.equals("Show user log")) {
                activityLogger.writeToUserLog(username, "userlog", "");
                activityLogger.setUserLog(username);
                activityLogger.setSize(750, 730);
                activityLogger.setVisible(true);
            } else if (btnLabel.equals("Log out")) {
                activityLogger.writeToUserLog(username, "logout", "");
                setVisible(false);
//                correlationGraphicsFrame.setVisible(false);
                LoginUI loginUI = new LoginUI();
                loginUI.setDefaultCloseOperation(loginUI.EXIT_ON_CLOSE);
                loginUI.setSize(600, 500);
                loginUI.setMinimumSize(new Dimension(280, 300));
                loginUI.setVisible(true);
            } else {
                String searchQuery = searchArea.getText();
                if (searchQuery.equals("")) {
                    activityLogger.writeToUserLog(username, "tableDefault", "");
                } else {
                    activityLogger.writeToUserLog(username, "search", searchQuery);
                }
                try {
                    DefaultTableModel searchModel = refreshTable(searchQuery);
                    dataTable.setModel(searchModel);
                    if (xListSize <= 1 || yListSize <= 1) {
                        results.setText("");
                        JOptionPane errorWindow = new JOptionPane();
                        JOptionPane.showMessageDialog(null, "Error: not enough values available to calculate correlation.");
                        errorWindow.setVisible(true);
                        errorWindow.setSize(300, 500);
                    } else {
                        calculateCorrelation();
                    }
                } catch (FileNotFoundException e) {
                    
                }
            }
        }
    } 
    
    // calculates correlation
    public void calculateCorrelation() {
        // testing purposes
//        xList.add((long) 43);
//        xList.add((long) 21);
//        xList.add((long) 25);
//        xList.add((long) 42);
//        xList.add((long) 57);
//        xList.add((long) 59);
//        
//        yList.add((long) 99);
//        yList.add((long) 65);
//        yList.add((long) 79);
//        yList.add((long) 75);
//        yList.add((long) 87);
//        yList.add((long) 81);

// adds ten values from table
// refresh xList and yList, otherwise values from other searches overlap
        xList.clear();
        yList.clear();
        
        for (int i = 0; i < xListSize; i++) {
            Object tableObject;
            String xValueString;
            long xValue;
            tableObject = dataTable.getModel().getValueAt(i, 3);
//            System.out.println(tableObject);
            xValueString = tableObject.toString();
            xValue = Long.parseLong(xValueString);
            xList.add(xValue);
//            if (i == 208) {
//                System.out.println("Last x value: " + xList.get(i));
//            }
        }
        
        for (int i = 0; i < yListSize; i++) {
            Object tableObject;
            String yValueString;
            long yValue;
            tableObject = dataTable.getModel().getValueAt(i, 4);
//            System.out.println(tableObject);
            yValueString = tableObject.toString();
            yValue = Long.parseLong(yValueString);
            yList.add(yValue);
//            if (i == 208) {
//                System.out.println("Last y value: " + yList.get(i));
//            }
        }
//        System.out.println("xList size: " + xList.size());
//        System.out.println("xList size: " + yList.size());
        
        // providing values to correlation object to calculate correlation coefficient
        Correlation correlationObject = new Correlation(xList, yList);
//        System.out.println("XList:" + correlationObject.getXList());
//        System.out.println("YList:" + correlationObject.getYList());
//        System.out.println("XYList:" + correlationObject.getXYList());
//        System.out.println("XSquaredList:" + correlationObject.getXSquaredList());
//        System.out.println("YSquaredList:" + correlationObject.getYSquaredList());
//        System.out.println("SumX:" + correlationObject.getSumX());
//        System.out.println("SumY:" + correlationObject.getSumY());
//        System.out.println("SumXY:" + correlationObject.getSumXY());
//        System.out.println("SumXSquared:" + correlationObject.getSumXSquared());
//        System.out.println("SumYSquared:" + correlationObject.getSumYSquared());
//        System.out.println("BottomXY: " + correlationObject.getBottomXY());
//        System.out.println("BottomX: " + correlationObject.getBottomX());
//        System.out.println("BottomY: " + correlationObject.getBottomY());
//        System.out.println("Top:" + correlationObject.getTop());
//        System.out.println("Bottom: " + correlationObject.getBottom());
        BigDecimal correlationCoefficient = correlationObject.getCorrelationCoefficient();
        System.out.println("Correlation Coefficient: " + correlationCoefficient);
        results.setText(correlationCoefficient + "");
        activityLogger.writeToUserLog(username, "analytics", correlationCoefficient.toString());
    }
    
    public void initComponents() throws FileNotFoundException {
        Container cp = getContentPane();
        JPanel cpPanel = new JPanel();
//        JScrollPane scrPane = new JScrollPane(cpPanel);
        cpPanel.setLayout(new GridBagLayout());
        cpPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new GridBagLayout());
        userPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel currentUserLabelPanel = new JPanel();
        currentUserLabelPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        currentUserLabel = new JLabel("Currently logged in as: " + username);
        currentUserLabelPanel.add(currentUserLabel);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
//        c.weightx = 0;
        userPanel.add(currentUserLabelPanel, c);
        // HCI: buttons positioned close together with user JLabel to group them as user actions
        JPanel showUserLogButtonPanel = new JPanel();
        showUserLogButtonPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        JButton showUserLogButton = new JButton("Show user log");
        showUserLogButtonPanel.add(showUserLogButton);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
//        c.weightx = 0.5;
        userPanel.add(showUserLogButtonPanel, c);
        JPanel logoutButtonPanel = new JPanel();
        logoutButtonPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        JButton logoutButton = new JButton("Log out");
        logoutButtonPanel.add(logoutButton);
        c.gridx = 1;
        c.gridy = 1;
//        c.weightx = 0.5;
        userPanel.add(logoutButtonPanel, c);
        JPanel instructionLabelPanel = new JPanel();
        instructionLabelPanel.setBorder(new EmptyBorder(0, 100, 0, 0));
        // HCI: included instructions to reset table as it's not intuitive to the user
        JLabel instructionLabel = new JLabel("Search with a blank search query to reset the table");
        instructionLabelPanel.add(instructionLabel);
        c.gridx = 2;
        c.gridy = 1;
        userPanel.add(instructionLabelPanel, c);
        c.gridx = 0;
        c.gridy = 0;
//        c.weightx = 1;
        cpPanel.add(userPanel, c);
        
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        searchArea = new JTextField();
        searchArea.setPreferredSize(new Dimension(350, 27));
        searchPanel.add(searchArea);
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(245, 27));
        searchPanel.add(searchButton);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        cpPanel.add(searchPanel, c);
        
        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        DefaultTableModel model = initialiseTable();
        dataTable = new JTable(model);
        dataTable.setEnabled(false);
        dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane tableScrPane = new JScrollPane(dataTable);
        tableScrPane.setPreferredSize(new Dimension(600, 400));
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        tablePanel.add(tableScrPane);
        cpPanel.add(tablePanel, c);
        
        JPanel resultsPanel = new JPanel();
        resultsPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        JLabel resultsLabel = new JLabel("Correlation: ");
        results = new JTextField();
        results.setPreferredSize(new Dimension(275, 27));
        results.setEditable(false);
        resultsPanel.add(resultsLabel);
        resultsPanel.add(results);
        JButton graphicsButton = new JButton("Show scatter graph");
        graphicsButton.setPreferredSize(new Dimension(245, 27));
        resultsPanel.add(graphicsButton);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        cpPanel.add(resultsPanel, c);
        
//        JPanel graphicsPanel = new JPanel();
//        JLabel correlationGraphLabel = new JLabel("Graphical representation: ");
//        graphicsPanel.add(correlationGraphLabel);
//        c.gridx = 1;
//        c.gridy = 1;
//        c.gridwidth = 1;
//        c.gridheight = 2;
//        cpPanel.add(graphicsPanel, c);
        
//        cp.add(scrPane);
        cp.add(cpPanel);
        calculateCorrelation();
        
        MyListener handler = new MyListener();
        searchButton.addActionListener(handler);
        graphicsButton.addActionListener(handler);
        showUserLogButton.addActionListener(handler);
        logoutButton.addActionListener(handler);
        searchArea.addActionListener(handler);
    }
    
//    public static void main(String[] args) throws FileNotFoundException {
//        // test
//        //  creates JFrame to display main interface of the program
//        CCGManager ccgManager = new CCGManager("guest");
//        ccgManager.setDefaultCloseOperation(ccgManager.EXIT_ON_CLOSE);
//        ccgManager.setVisible(true);
//        ccgManager.setSize(700, 730);
////        ccgManager.setMinimumSize(new Dimension(700, 730));
//        ccgManager.setResizable(false);
//    }
 
    // alternative code
    //        Scanner scanner = new Scanner(new File("1734_ccg-reg-patients-1.csv"));
//        scanner.useDelimiter(",");
//        for (int i = 0; i < model.getColumnCount(); i++) {
//            if (scanner.hasNext()) {
//                
//                String columnName = scanner.next();
//                System.out.println(columnName); 
//                model.addColumn(columnName);
//            }
//        }
}
