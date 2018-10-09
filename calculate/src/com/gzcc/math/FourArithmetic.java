package com.gzcc.math;

import java.util.Scanner;
import java.util.Stack;

public class FourArithmetic
{
	private static int record;
	private static String[] op = { "+", "-", "*", "/" };  //运算符
    public static void main(String[] args) {
    	System.out.print("输入题目次数：");
    	Scanner scanner = new Scanner(System.in);
    	int times = scanner.nextInt();
    	for(int i=0;i<times;i++)
    	{
	        String question = MakeFormula();
	        String result = Solve(question);
	     	//过滤掉答案不为负数的
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
	        	System.out.println("恭喜你答对了！");
	        }
	        else{
	        System.out.println("真遗憾做错了，要继续加油！\n"+ question+"="+result);
	        }
    	}
    	 System.out.println("本次分数为"+record);
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
     * 随机生成运算式子
     * @resulturn
     */
    public static String MakeFormula(){
        StringBuilder build = new StringBuilder();
        int count =(int)(Math.random() * 2)+1;        //2-3个运算符
        int number1 = (int) (Math.random() * 99) + 1;  //1-100之内的随机数
        int start = 0;
        build.append(number1);
        while (start <= count)
		{
        	int operation = (int) (Math.random() * 3) + 1; // 随机的运算符
        	int number2 = (int) (Math.random() * 99) + 1;
            build.append(op[operation]).append(number2);
            start++;
		}
        return build.toString();
    }

    /**
     * 运算符优先级
     * @param formula
     * @resulturn
     */
    public static String Solve(String formula){
        Stack<String> tempStack = new Stack<>();//放数字，运算符
        Stack<Character> operatorStack = new Stack<>();//放运算符
        int len = formula.length(); 
        int k = 0;
        String mark = "invalid";
        for(int j = -1; j < len - 1; j++){
            char formulaChar = formula.charAt(j + 1);  //index 0 返回 第一个数
            if(j == len - 2 || formulaChar == '+' || formulaChar == '-' || formulaChar == '/' || formulaChar == '*') {
                if (j == len - 2) {
                    tempStack.push(formula.substring(k));
                }
                else {
                        tempStack.push(formula.substring(k, j + 1));
                    if(operatorStack.empty()){
                        operatorStack.push(formulaChar); // 在栈中放入第一个符号
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
        // 遍历
        for(String peekChar : tempStack){ 
            if(!peekChar.equals("+") && !peekChar.equals("-") && !peekChar.equals("/") && !peekChar.equals("*")) {
                calcStack.push(peekChar); // 把数字存入栈
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

