
package com.java.calculator;

/**
 *
 * @author atifk
 */
public class Calculate {
    /**
     * This Class will perform the appropriate arithmetic operation.
     */
    
    public double calculate(char key, double a, double b) {
        double answer = 0;
        
        switch (key) {
            case '+': 
                answer = add(a, b);
                break;
            case '-': 
                answer = sub(a, b);
                break;
            case '*': 
                answer = pro(a, b);
                break;
            case '/': 
                answer = div(a, b);
                break;
            case '^':
                answer = power(a, b);
        }
        
        return answer;
    }
    
    private double add(double a, double b) {
        return (a + b);
    }
    
    private double sub(double a, double b) {
        return (a - b);
    }
    
    private double pro(double a, double b) {
        return (a * b);
    }
    
    private double div(double a, double b) {
        return (a / b);
    }
    
    private double power(double number, double power) {
        return (Math.pow(number, power));
    }
}
