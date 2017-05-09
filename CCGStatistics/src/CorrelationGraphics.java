/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author w1576234
 */
public class CorrelationGraphics extends JPanel {
    ArrayList<Long> xCoordinateList = new ArrayList<Long>();
    ArrayList<Long> yCoordinateList = new ArrayList<Long>();
    int firstXCoordinate = 9999; // making value large so can determine lowest x-coordinate value when going through ArrayList
    int firstYCoordinate = 0; // making value small so can determine lowest y-coordinate value when going through ArrayList, as graphics draw downwards
    int lastXCoordinate = 0; // making the value small so can determine highest x-coordinate when going through ArrayList
    int lastYCoordinate = 9999; // making the value large so can determine highest y-coordinate when going through ArrayList, as graphics draw downwards
    int lowestXValue = 999999; // making value large so can determine lowest x value when going through ArrayList
    int lowestYValue = 999999; // making value large so can determine highest y value when going through ArrayList
    int highestXValue = 0; // making value small so can determine highest x value when going through ArrayList
    int highestYValue = 0; // making value small so can determine highest y value when going through ArrayList
    int axesCrossover = 70; // point where the x-axis and the y-axis meet
    int yAxisLength = 600;
    int xAxisLength = 600;
    int yAxisStart = 50;
    int distanceBeforeYAxisStart = 60;
    int distanceBeforeXAxisEnd = 590;
    int distanceAboveXAxis = 595;
    int distanceBelowXAxis = 605;
    int distanceAfterYAxis = 75;
    int distanceBeforeYAxis = 65;
    int shortNumberBeforeYAxis = 15;
    int longNumberBeforeYAxis = 20;
    int numberBelowXAxis = 625;
//    Container cp = getContentPane();

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(new Color(31, 21, 1));
//        g2d.drawLine(98, 147, 2, 3);
        // draws arrows at the end of x-axis.
        g2d.drawLine(yAxisLength, yAxisLength, distanceBeforeXAxisEnd, distanceBelowXAxis);
        g2d.drawLine(yAxisLength, yAxisLength, distanceBeforeXAxisEnd, distanceAboveXAxis);
        // draws arrow at the end of y-axis.
        g2d.drawLine(axesCrossover, yAxisStart, distanceAfterYAxis, distanceBeforeYAxisStart);
        g2d.drawLine(axesCrossover, yAxisStart, distanceBeforeYAxis, distanceBeforeYAxisStart);
        // draw line for x-axis
        g2d.drawLine(axesCrossover, yAxisLength, xAxisLength, yAxisLength);
        // draw line for y-axis
        g2d.drawLine(axesCrossover, yAxisStart, axesCrossover, yAxisLength);
        // draw starting marker on x-axis
        g2d.drawLine(firstXCoordinate, distanceBelowXAxis, firstXCoordinate, distanceAboveXAxis);
        if (Integer.toString(lowestXValue).length() < 6) {
            g2d.drawString(Integer.toString(lowestXValue), firstXCoordinate - 15, numberBelowXAxis);
        } else {
            g2d.drawString(Integer.toString(lowestXValue), firstXCoordinate - 20, numberBelowXAxis);
        }
        // draw starting marker on y-axis
        g2d.drawLine(distanceBeforeYAxis, firstYCoordinate, distanceAfterYAxis, firstYCoordinate);
        if (Integer.toString(lowestYValue).length() < 6) {
            g2d.drawString(Integer.toString(lowestYValue), longNumberBeforeYAxis, firstYCoordinate + 5);
        } else {
            g2d.drawString(Integer.toString(lowestYValue), shortNumberBeforeYAxis, firstYCoordinate + 5);
        }
        // draw end marker on x-axis
        g2d.drawLine(lastXCoordinate, distanceBelowXAxis, lastXCoordinate, distanceAboveXAxis);
        if (Integer.toString(highestXValue).length() < 6) {
            g2d.drawString(Integer.toString(highestXValue), lastXCoordinate - 15, numberBelowXAxis);
        } else {
            g2d.drawString(Integer.toString(highestXValue), lastXCoordinate - 20, numberBelowXAxis);
        }
        // draw end marker on y-axis
        g2d.drawLine(distanceBeforeYAxis, lastYCoordinate, distanceAfterYAxis, lastYCoordinate);
        if (Integer.toString(highestYValue).length() < 6) {
            g2d.drawString(Integer.toString(highestYValue), longNumberBeforeYAxis, lastYCoordinate + 5);
        } else {
            g2d.drawString(Integer.toString(highestYValue), shortNumberBeforeYAxis, lastYCoordinate + 5);
        }
        // plots points on scatter graph
        for (int i = 0; i < xCoordinateList.size(); i++) {
            int x = xCoordinateList.get(i).intValue();
//            System.out.println("xCoordinate: " + xCoordinateList.get(i));
            int y = yCoordinateList.get(i).intValue();
//            System.out.println("yCoordinate: " + yCoordinateList.get(i));
            g2d.fillOval(x, y, 10, 10);
        }
        // draws line of best fit
        g2d.drawLine(firstXCoordinate, firstYCoordinate, lastXCoordinate, lastYCoordinate);
        
        // plot on the scatter graph
//        g2d.fillOval(100, 100, 10, 10);
    }
    
    public void setCoordinateValues(ArrayList<Long> xList, ArrayList<Long> yList) {
//        System.out.println("sanausage: " + "bork");
        for (int i = 0; i < xList.size(); i++) {
            xCoordinateList.add(((xList.get(i) / 1000) + 70));
            yCoordinateList.add(590 - (yList.get(i) / 1000));
        }
//        System.out.println("xCoordinateList: " + xCoordinateList);
//        System.out.println("yCoordinateList: " + yCoordinateList);
//        firstXCoordinate = xCoordinateList.get(0).intValue();
//        firstYCoordinate = yCoordinateList.get(0).intValue();
        for (int i = 0; i < xCoordinateList.size(); i++) {
            if (xCoordinateList.get(i).intValue() < firstXCoordinate) {
                firstXCoordinate = xCoordinateList.get(i).intValue() + 5;
                // 5 is added so that marker will point directly to middle of point on graph
            }
            
            if (xCoordinateList.get(i).intValue() > lastXCoordinate) {
                lastXCoordinate = xCoordinateList.get(i).intValue() + 5;
                // 5 is added so that marker will point directly to middle of point on graph
            }
            
            if (yCoordinateList.get(i).intValue() > firstYCoordinate) {
                firstYCoordinate = yCoordinateList.get(i).intValue() + 5;
                // 5 is added so that marker will point directly to middle of point on graph
            }
            
            if (yCoordinateList.get(i).intValue() < lastYCoordinate) {
                lastYCoordinate = yCoordinateList.get(i).intValue() + 5;
                // 5 is added so that marker will point directly to middle of point on graph
            }
        }
//        System.out.println("firstXCoordinate: " + firstXCoordinate);
//        System.out.println("firstYCoordinate: " + firstYCoordinate);
        
        for (int i = 0; i < xList.size(); i++) {
            if (xList.get(i).intValue() < lowestXValue) {
                lowestXValue = xList.get(i).intValue();
            }
            
            if (xList.get(i).intValue() > highestXValue) {
                highestXValue = xList.get(i).intValue();
            }
            
            if (yList.get(i).intValue() < lowestYValue) {
                lowestYValue = yList.get(i).intValue();
            }
            
            if (yList.get(i).intValue() > highestYValue) {
                highestYValue = yList.get(i).intValue();
            }
        }
        System.out.println("lowestXValue: " + lowestXValue);
        System.out.println("highestXValue: " + highestXValue);
        System.out.println("lowestYValue: " + lowestYValue);
        System.out.println("highestYValue: " + highestYValue);
//        this.xCoordinateList = xList;
//        this.yCoordinateList = yList;
    }
    
//    public static void main(String[] args) {
//        CorrelationGraphics lines = new CorrelationGraphics();
//        JFrame frame = new JFrame("Scatter Graph");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(lines);
//        frame.setSize(700, 700);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
}
