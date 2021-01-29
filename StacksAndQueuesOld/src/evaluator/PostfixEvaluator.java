package evaluator;
import java.util.Stack;

public class PostfixEvaluator extends Evaluator {

	@Override
	public int evaluate(String expression) throws ArithmeticException {
		// TO DO Auto-generated method stub
		Stack<Integer> operand = new Stack<Integer>();
		String[] str = expression.split(" ");
		int op1;
		int countN = 0;
		int countOp = 0;
		boolean op = false;
		
		for (int i = 0; i < str.length; i++) {
			String temp = str[i];
			
			// letters or '(' ')' throw exception
			if (Character.isAlphabetic(temp.charAt(0)) 
					|| temp.charAt(0) == '(' || temp.charAt(0) == ')') 
				throw new ArithmeticException();
				
			if (isOperator(temp)) {
				// not enough operand
				if (operand.size() < 2) throw new ArithmeticException();
				countOp++; // count # of operators
				op = true; // there's at least one operator
				switch (temp) {
				case "+": // add
					operand.push(operand.pop() + operand.pop());
					break;
				case "-": // sub
					op1 = operand.pop();
					operand.push(operand.pop() - op1);
					break;
				case "*": // multiply
					operand.push(operand.pop() * operand.pop());
					break;
				case "/": // divide
					op1 = operand.pop();
					if (op1 == 0) throw new ArithmeticException();
					operand.push(operand.pop() / op1);
					break;
				case "^": // power
					op1 = operand.pop();
					operand.push((int) (Math.pow(operand.pop(), op1)));
					break;
				}
			} else {
				countN++;
				operand.push(Integer.parseInt(temp));
			}
		}
		// too many operands
		if ((countN - countOp) > 1) throw new ArithmeticException();
		// no operators
		if (!op && operand.size() > 1) throw new ArithmeticException();
		// return the result
		if (!operand.isEmpty()) return operand.pop();
		return 0;
	}
}
