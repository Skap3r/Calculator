
package com.java.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author atifk
 */
public class ExtractNumber {
    private List data;
    private char key;
    private double firstNumber, secondNumber, answer;
    private int index;
    
    public ExtractNumber() {
        this.data = new ArrayList();
        this.firstNumber = 0;
        this.secondNumber = 0;
        this.index = 0;
        this.answer = 0;
    }
    
    /**
     *
     * @param data
     * @return
     */
    public String getData(List data) {
        this.data = data;
        Calculate calc = new Calculate();
        
        if (this.data.indexOf('^') != -1)
            operation('^');
        
        if (this.data.indexOf('/') != -1)
            operation('/');
        
        if (this.data.indexOf('*') != -1)
            operation('*');
        
        while (this.data.size() != 1) {
            this.firstNumber = (double)this.data.get(0);
            this.secondNumber = (double)this.data.get(2);
            this.key = (char)this.data.get(1);

            this.answer = calc.calculate(this.key, this.firstNumber,
                    this.secondNumber);

            deleteEquation(1);
            
            this.data.add(0, this.answer);
        }
        
        if (this.data.size() == 1) {
            Bridge obj = Bridge.getInstance();
            String s = finalAnswer();
            obj.setString(s);
            return s;
        }
        
        return "failed";
    }
    
    private void operation(char s) {
        Calculate calc = new Calculate();
        
        while (this.data.size() != 1 && this.data.indexOf(s) != -1) {
            if (this.data.indexOf(s) != -1) {
                this.index = this.data.indexOf(s);
                
                this.key = s;
                this.firstNumber = (double)this.data.get(index - 1);
                this.secondNumber = (double)this.data.get(index + 1);
                
                this.answer = calc.calculate(this.key, this.firstNumber, 
                        this.secondNumber);
                
                deleteEquation(this.index);
                
                this.data.add(index - 1, this.answer);
            }
        }
    }
    
    /**
     * It removes the equation from the list.
     * For example, If index position 3, 4 and 5 are 4, / and 2 respectively,
     * it'll remove the three and shift every element on the right three 
     * positions towards left.
     * @param i 
     */
    private void deleteEquation(int i) {
        for (int j = 0; j < 3; j++)
            this.data.remove(i-1);
    }
    
    /**
     * This method will check if the answer has any number after the decimal
     * point. 
     * If not then the method will return a whole number.
     * For example, 4.0 will be returned as 4 in string format and 4.1 will 
     * remain unchanged and returned as string.
     * @return answer
     */
    private String finalAnswer() {
        double tempD;
        long tempL;
        
        tempD = (double)this.data.get(0);
        tempL = Math.round(tempD);
        
        if (tempD % tempL == 0)
            return (Long.toString(tempL));
        
        return (Double.toString(tempD));
    }
}
