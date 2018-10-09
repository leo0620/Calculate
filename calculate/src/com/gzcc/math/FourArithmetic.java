package com.gzcc.math;

import java.util.Scanner;
import java.util.Stack;

public class FourArithmetic
{
	private static int record;
	private static String[] op = { "+", "-", "*", "/" };  //�����
    public static void main(String[] args) {
    	System.out.print("������Ŀ������");
    	Scanner scanner = new Scanner(System.in);
    	int times = scanner.nextInt();
    	for(int i=0;i<times;i++)
    	{
	        String question = MakeFormula();
	        String result = Solve(question);
	     	//���˵��𰸲�Ϊ������
	        if("invalid"==result)
	        {
	        	i--;continue;
	        }
	        float results= Float.valueOf(result);
	        System.out.println(question+"=");
	        scanner = new Scanner(System.in);
	        float anwser = scanner.nextFloat();
	        if(Score(anwser,results))
	        {
	        	System.out.println("��ϲ�����ˣ�");
	        }
	        else{
	        System.out.println("���ź������ˣ�Ҫ�������ͣ�\n"+ question+"="+result);
	        }
    	}
    	 System.out.println("���η���Ϊ"+record);
    }
    
    private static boolean Score(float answer,float result)
    {
    	if(answer==result)
    	{
    		record+=10;
    		return true;
    	}
    	return false;
    }
    /**
     * �����������ʽ��
     * @resulturn
     */
    public static String MakeFormula(){
        StringBuilder build = new StringBuilder();
        int count =(int)(Math.random() * 2)+1;        //2-3�������
        int number1 = (int) (Math.random() * 99) + 1;  //1-100֮�ڵ������
        int start = 0;
        build.append(number1);
        while (start <= count)
		{
        	int operation = (int) (Math.random() * 3) + 1; // ����������
        	int number2 = (int) (Math.random() * 99) + 1;
            build.append(op[operation]).append(number2);
            start++;
		}
        return build.toString();
    }

    /**
     * ��������ȼ�
     * @param formula
     * @resulturn
     */
    public static String Solve(String formula){
        Stack<String> tempStack = new Stack<>();//�����֣������
        Stack<Character> operatorStack = new Stack<>();//�������
        int len = formula.length(); 
        int k = 0;
        String mark = "invalid";
        for(int j = -1; j < len - 1; j++){
            char formulaChar = formula.charAt(j + 1);  //index 0 ���� ��һ����
            if(j == len - 2 || formulaChar == '+' || formulaChar == '-' || formulaChar == '/' || formulaChar == '*') {
                if (j == len - 2) {
                    tempStack.push(formula.substring(k));
                }
                else {
                        tempStack.push(formula.substring(k, j + 1));
                    if(operatorStack.empty()){
                        operatorStack.push(formulaChar); // ��ջ�з����һ������
                    }else{
                        char stackChar = operatorStack.peek();
                        if ((stackChar == '+' || stackChar == '-')
                                && (formulaChar == '*' || formulaChar == '/')){
                            operatorStack.push(formulaChar);
                        }else {
                            tempStack.push(operatorStack.pop().toString());
                            operatorStack.push(formulaChar);
                        }
                    }
                }
                k = j + 2; 
            }
        }
        while (!operatorStack.empty()){ 
            tempStack.push(operatorStack.pop().toString());
        }
        Stack<String> calcStack = new Stack<>();
        // ����
        for(String peekChar : tempStack){ 
            if(!peekChar.equals("+") && !peekChar.equals("-") && !peekChar.equals("/") && !peekChar.equals("*")) {
                calcStack.push(peekChar); // �����ִ���ջ
            }else{
            	float a = 0;
            	float b = 0;
                if(!calcStack.empty()){
                    b = Float.parseFloat(calcStack.pop());
                    
                }
                if(!calcStack.empty()){
                	a = Float.parseFloat(calcStack.pop());
                	
                }
                switch (peekChar) {
                    case "+":
                        calcStack.push(String.valueOf(a + b));
                        break;
                    case "-":
                        calcStack.push(String.valueOf(a - b));
                        break;
                    case "*":
                        calcStack.push(String.valueOf(a * b));
                        break;
                    default:
                        calcStack.push(String.valueOf((float)a / b));
                        break;
                }
            }
        }
        if(Float.parseFloat(calcStack.peek())<0){
        	return mark;
        }
        return calcStack.pop();
    }
}

