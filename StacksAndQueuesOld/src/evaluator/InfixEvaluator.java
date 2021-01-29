package evaluator;

import java.util.Stack;

public class InfixEvaluator extends Evaluator{
	PostfixEvaluator postfixEva = new PostfixEvaluator();
	
	@Override
	public int evaluate(String expression) throws ArithmeticException {
		return this.postfixEva.evaluate(convertToPostfix(expression));
	}

	public String convertToPostfix(String string) {

		Stack<String> s = new Stack<>();
		String[] str = string.split(" "); // split the string by space
		StringBuilder postfix = new StringBuilder();
		int count = 0; // count # of "()"
		for (int i = 0; i < str.length; i++) {
			String temp = str[i];
			
			if (Character.isDigit(temp.charAt(0))) postfix.append(temp + " ");
			else if (temp.equals("(")) {
				count++; // count # of "()"
				s.push(temp);
			} else if (temp.equals(")")) {
				count++; // count # of "()"
				while (!s.isEmpty()) {
					if (s.peek().equals("(")) {
						s.pop();
						break;
					}
					postfix.append(s.pop() + " ");
				}
			} else {
				while (!s.isEmpty() && op(temp) <= op(s.peek()))
					postfix.append(s.pop() + " ");
				s.push(temp);
			}
		}
		while (!s.isEmpty())  
			postfix.append(s.pop() + " ");
		if (postfix.charAt(postfix.length() - 1) == ' ') 
			postfix.deleteCharAt(postfix.length() - 1);
		if (count % 2 != 0) throw new ArithmeticException();
		return postfix.toString();
	}

	private static int op(String str) {
		switch (str) {
		case "+":
			return 1;
		case "-":
			return 1;
		case "*":
			return 2;
		case "/":
			return 2;

		case "^":
			return 3;
		default:
			break;
		}
		return 0;
	}


}
