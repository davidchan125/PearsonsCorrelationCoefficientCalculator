/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.math.*;
import javax.swing.*;

/**
 *
 * @author David
 */
public class Correlation {
    // xList = column1
    private ArrayList<Long> xList = new ArrayList<Long>();
    // yList = column2
    private ArrayList<Long> yList = new ArrayList<Long>();
    // xyList = column1 * column2
    private ArrayList<Long> xyList = new ArrayList<Long>();
    // xSquaredList = column1 * column1
    private ArrayList<Long> xSquaredList = new ArrayList<Long>();
    // ySquaredList = column2 * column2
    private ArrayList<Long> ySquaredList = new ArrayList<Long>();
    // total of values in column1
    private long sumX = 0;
    // total of values in column2
    private long sumY = 0;
    // total of values from column1 * column2
    private long sumXY = 0;
    // total of values from column1 * column1
    private long sumXSquared = 0;
    // total of values from column2 * column2
    private long sumYSquared = 0;
    // number of values in column1/column2
    private int n = 0;
    // value for correlation coefficient
    private BigDecimal r;
    // top of equation in formula
    private long top;
    // bottom of equation in formula (square root of bottomXY)
    private BigDecimal bottom;
    // bottom of equation in formula (not square rooted)
    private BigDecimal bottomXY;
    // first part of bottomXY
    private long bottomX;
    // second part of bottomXY
    private long bottomY;
    
    public Correlation() {
        
    }
    
    public Correlation(ArrayList<Long> xList, ArrayList<Long> yList) {
        this.xList = xList;
        this.yList = yList;
        doEverything();
    }
    
    public long getTop() {
        return top;
    }
    
    public BigDecimal getBottom() {
        return bottom;
    }
    
    public BigDecimal getBottomXY() {
        return bottomXY;
    }
    
    public long getBottomX() {
        return bottomX;
    }
    
    public long getBottomY() {
        return bottomY;
    }
    
    public long getSumX() {
        return sumX;
    }
    
    public long getSumY() {
        return sumY;
    }
    
    public long getSumXY() {
        return sumXY;
    }
    
    public long getSumXSquared() {
        return sumXSquared;
    }
    
    public long getSumYSquared() {
        return sumYSquared;
    }
    
    public ArrayList<Long> getXList() {
        return xList;
    }
    
    public void setXList(ArrayList<Long> xList) {
        this.xList = xList;
    }
    
    public ArrayList<Long> getYList() {
        return yList;
    }
    
    public void setYList(ArrayList<Long> yList) {
        this.yList = yList;
    }
    
    public ArrayList<Long> getXYList() {
        return xyList;
    }
    
    public void setXYList(ArrayList<Long> xyList) {
        this.xyList = xyList;
    }
    
    public ArrayList<Long> getXSquaredList() {
        return xSquaredList;
    }
    
    public void setXSquaredList(ArrayList<Long> xSquaredList) {
        this.xSquaredList = xSquaredList;
    }
    
    public ArrayList<Long> getYSquaredList() {
        return ySquaredList;
    }
    
    public void setYSquaredList(ArrayList<Long> ySquaredList) {
        this.ySquaredList = ySquaredList;
    }
    
    public int getN() {
        return n;
    }
    
    public void setN(int n) {
        this.n = n;
    }
    
    public BigDecimal getCorrelationCoefficient() {
        return r;
    }
    
    public void setCorrelationCoefficient(BigDecimal r) {
        this.r = r;
    }
    
    public void calculateSumOf(ArrayList<Long> valueList) {
        if (valueList == xList) {
            for (int i = 0; i < valueList.size(); i++) {
                long xValue = valueList.get(i);
                sumX = sumX + xValue;
            }
        } else if (valueList == yList) {
            for (int i = 0; i < valueList.size(); i++) {
                long yValue = valueList.get(i);
                sumY = sumY + yValue;
            }
        } else if (valueList == xyList) {
            for (int i = 0; i < valueList.size(); i++) {
                long xyValue = valueList.get(i);
                sumXY = sumXY + xyValue;
            }
        } else if (valueList == xSquaredList) {
            for (int i = 0; i < valueList.size(); i++) {
                long xSquaredValue = valueList.get(i);
                sumXSquared = sumXSquared + xSquaredValue;
            }
        } else if (valueList == ySquaredList) {
            for (int i = 0; i < valueList.size(); i++) {
                long ySquaredValue = valueList.get(i);
                sumYSquared = sumYSquared + ySquaredValue;
            }
        }
    }
    
    public void calculateSquaredValues(ArrayList<Long> valueList) {
        if (valueList == xList) {
            for (int i = 0; i < valueList.size(); i++) {
                long xValue = valueList.get(i);
                long xSquaredValue = xValue * xValue;
                xSquaredList.add(xSquaredValue);
            }
        } else if (valueList == yList) {
            for (int i = 0; i < valueList.size(); i++) {
                long yValue = valueList.get(i);
                long ySquaredValue = yValue * yValue;
                ySquaredList.add(ySquaredValue);
            }
        }
    }
    
    /**
     * This method should workout the xy values from xList and yList. The inputs are the x values from xList and the y values from yList.
     * From this method you should be able to generate a list of xy values, for use in the working out of the correlation coefficient.
     * This method returns a full list of xy values in the ArrayList xyList.
     * @param valueList1: 
     * @param valueList2 
     */
    public void calculateXYValues(ArrayList<Long> valueList1, ArrayList<Long> valueList2) {
        if (valueList1 == xList && valueList2 == yList) {
            for (int i = 0; i < valueList1.size(); i++) {
                long xValue = valueList1.get(i);
                long yValue = valueList2.get(i);
                long xyValue = xValue * yValue;
                xyList.add(xyValue);
            }
        }
    }
    
    public BigDecimal calculateSquareRootOf(BigDecimal num) {
//        num = num.setScale(15, RoundingMode.HALF_UP);
//        System.out.println("Num: " + num);
        BigDecimal root = BigDecimal.ZERO;
        if (num == BigDecimal.ZERO) {
            return root;
        }
        BigDecimal upperOrLowerLimit = BigDecimal.ZERO;
        BigDecimal rootGuess = BigDecimal.ZERO;
        BigDecimal initialInput = num;
        BigDecimal compareToNum = BigDecimal.ZERO;
        
        int checkIfLargerThanFour = num.subtract(BigDecimal.valueOf(4)).compareTo(BigDecimal.ZERO);
        if (checkIfLargerThanFour >= 0) {
            initialInput.divide(BigDecimal.valueOf(2));
//            System.out.println("Initial input: " + initialInput);
        }
        
        upperOrLowerLimit = num.divide(initialInput);
//        System.out.println("upperOrLowerLimit: " + upperOrLowerLimit);
        rootGuess = (upperOrLowerLimit.add(initialInput)).divide(BigDecimal.valueOf(2), 15, RoundingMode.HALF_UP);
//        System.out.println("rootGuess: " + rootGuess);
        compareToNum = rootGuess.multiply(rootGuess);
//        System.out.println("compareToNum: " + compareToNum);
        if (compareToNum.intValue() == num.intValue()) {
//            System.out.println("baguette");
            root = rootGuess;
            return root;
        } else {
//            System.out.println("sausage");
            while (compareToNum.intValue() != num.intValue()) {
//                System.out.println("Num: " + num);
                upperOrLowerLimit = num.divide(rootGuess, 15, RoundingMode.HALF_UP);
//                System.out.println("upperOrLowerLimit: " + upperOrLowerLimit);
                rootGuess = (upperOrLowerLimit.add(rootGuess)).divide(BigDecimal.valueOf(2), 15, RoundingMode.HALF_UP);
//                System.out.println("rootGuess: " + rootGuess);
                compareToNum = rootGuess.multiply(rootGuess);
                compareToNum = compareToNum.setScale(0, RoundingMode.HALF_UP);
//                System.out.println("compareToNum: " + compareToNum);
                root = rootGuess;
                root = root.setScale(0, RoundingMode.HALF_UP);
//                System.out.println("root: " + root);
            }
        }
        return root;
    } 
    
    public void calculateCorrelationCoefficient() {
        n = xList.size();
        top = (n * sumXY) - (sumX * sumY);
        bottomX = (n * sumXSquared) - (sumX * sumX);
        bottomY = (n * sumYSquared) - (sumY * sumY);
        bottomXY = BigDecimal.valueOf(bottomX).multiply(BigDecimal.valueOf(bottomY));
        bottom = calculateSquareRootOf(bottomXY);
        try {
            r = BigDecimal.valueOf(top).divide(bottom, 6, RoundingMode.HALF_UP);
        } catch (ArithmeticException e) {
            System.out.println("Error: division by zero, cannot compute");
        }
    }
    
    public void doEverything() {
        calculateXYValues(xList, yList);
        calculateSquaredValues(xList);
        calculateSquaredValues(yList);
        calculateSumOf(xList);
        calculateSumOf(yList);
        calculateSumOf(xyList);
        calculateSumOf(xSquaredList);
        calculateSumOf(ySquaredList);
        calculateCorrelationCoefficient();
    }
    
//    public static void main(String[] args) {
//        // test
//        BigDecimal value = new BigDecimal("644498498498444984984984");
//        BigDecimal v = new BigDecimal(Math.sqrt(value.doubleValue()));
//        System.out.println("sausage: " + v);
//        Correlation correlationObject = new Correlation();
//        System.out.println("Calculate the square root of: " + value);
//        System.out.println("Square root: " + correlationObject.calculateSquareRootOf(value));
//    }
}
