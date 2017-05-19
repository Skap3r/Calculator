
package com.java.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author atifk
 */
public class Bridge {
    private double firstNum, secondNum;
    private ArrayList keyInputs;
    private static final String DEFAULT = "0";
    private static final String BACK_SPACE = "BSpc";
    private static final String CLEAR_ENTRY = "CE";
    private static final String CLEAR = "C";
    private boolean allowNumber, pointPressed;
    private StringBuilder sb;
    
    private Bridge() {
        this.allowNumber = false;
        this.pointPressed = false;
        this.keyInputs = new ArrayList();
        this.firstNum = 0;
        this.secondNum = 0;
        this.sb = new StringBuilder();
    }
    
    public static Bridge getInstance() {
        return BridgeHolder.INSTANCE;
    }
    
    private static class BridgeHolder {

        private static final Bridge INSTANCE = new Bridge();
    }
    
    public String inputText(String s) {
        
        if (s.equals(".") && (isLastInputKey() || this.sb.length() == 0))
            this.sb.append(DEFAULT);
            
        
        if (s.equals(".") && !this.pointPressed)
            this.pointPressed = true;
        else if (this.pointPressed && s.equals("."))
            return this.sb.toString();
        else if (this.pointPressed && checkIfKey(s))
            this.pointPressed = false;
        
        if (s.equals(CLEAR) || this.allowNumber) {
            if (s.equals(CLEAR)) {
                clear();
                return DEFAULT;
            } else if (this.allowNumber && !checkIfKey(s)) {
                this.sb.delete(0, this.sb.length());
                this.sb.append(s);
                this.allowNumber = false;
                return this.sb.toString();
            } else if (this.allowNumber) {
                this.allowNumber = false;
            }
        }
        
        if (s.equals(CLEAR_ENTRY))
            return clearEntry();
        
        if (s.equals(BACK_SPACE)) {
            if(!this.allowNumber)
                return backSpace();
        }
        
        if (this.sb.length() == 0) {
            if ((checkIfKey(s) || s.equals("=")) && !s.equals("-"))
                return DEFAULT;
        }
        else if (isLastInputKey()) {
            if (s.equals("=") || checkIfKey(s))
                return this.sb.toString();
        }
        
        if (s.equals("=") && this.sb.length() != 0) {
            this.sb.append(s);
            ExtractNumber en = new ExtractNumber();
            return (en.getData(parseInput()));
        }
        
        this.sb.append(s);
        return this.sb.toString();
    }
    
    /**
     * Parses through the String received from user through the calculator interface
     * and separates numbers from operation keys.
     * @return A list of alternating numbers and operation keys. For example;
     * a string of "123+4*4+7" is returned as [123.0, '+', 4.0, '*', 4.0, '+', 7.0]
     */
    private List parseInput() {
        List equation = new ArrayList();
        StringBuilder sb = new StringBuilder();
        
        try {
            for (int i = 0; i < this.sb.length(); i++) {
                if (this.sb.charAt(i) == '=') {
                    equation.add(Double.parseDouble(sb.toString()));
                    break;
                }
                
                if (!checkIfKey(this.sb.charAt(i))){
                    sb.append(this.sb.charAt(i));
                    continue;
                }
                
                equation.add(Double.parseDouble(sb.toString()));
                equation.add(this.sb.charAt(i));
                sb.delete(0, this.sb.length());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        return equation;
    }
    
    public void setString(String s) {
        this.sb.delete(0, this.sb.length());
        this.sb.append(s);
        this.allowNumber = true;
    }
    
    private String backSpace() {
        if (this.sb.length() <= 1)
            return DEFAULT;
        
        this.sb.deleteCharAt(this.sb.length() - 1);
        
        return this.sb.toString();
   }
    
    private boolean checkIfKey(String s) {
        return ("+".equals(s) || "*".equals(s)
                || "/".equals(s) || "-".equals(s) || "^".equals(s));
    }
    
    private boolean checkIfKey(char c) {
        return (c == '+' || c == '-' || c == '*' || 
                c == '/' || c == '^');
    }
    
    private void clear() {
        this.keyInputs.clear();
        this.firstNum = 0;
        this.secondNum = 0;
        this.allowNumber = false;
        this.sb.delete(0, this.sb.length());
    }
    
    private boolean isLastInputKey() {
        
        if (this.sb.length() != 0) {
            char c = this.sb.charAt(this.sb.length() - 1);
        
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') 
                return true;
        }
        
        return false;
    }
    
    private String clearEntry() {
        
        if (this.sb.length() == 0) {
            clear();
            return DEFAULT;
        }
        
        int i = 0;
        boolean deleteKey = true;
        this.sb.reverse();
        
        while (this.sb.length() > 0) {
            
            if (!checkIfKey(this.sb.charAt(i))) {
                this.sb.deleteCharAt(i);
                deleteKey = false;
            }
            else if (deleteKey && checkIfKey(this.sb.charAt(i))){
                this.sb.deleteCharAt(i);
                break;
            }
            else {
                break;
            }
            
            if (this.sb.length() == 0) {
                clear();
                return DEFAULT;
            }
        }
        
        this.sb.reverse();
        
        return this.sb.toString();
    }
}
