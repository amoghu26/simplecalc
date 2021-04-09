import java.util.List;		// used by expression evaluator
import java.util.ArrayList;

/**
 *	A simple calculator that can perform addition, subtraction,
 *  multiplication, division, powers, and modulus. It can also handle
 *  parenthesis and nested parenthesis. 
 * 
 *	@author	Amogh Upadhyaya
 *	@since	6 April 2021
 */
public class SimpleCalc {
	
	private ExprUtils utils;	// expression utilities
	
	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack

	private ExprUtils e; // Object of ExprUtils class
	private Prompt Prompt; // object of Prompt class

	// constructor	
	public SimpleCalc() {
		valueStack = new ArrayStack<Double>();
		operatorStack = new ArrayStack<String>();
		e = new ExprUtils();
		Prompt = new Prompt();
	}
	
	/** Main method */
	public static void main(String[] args) {
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}
	
	/** Prints introduction, runs game, and prints ending message */
	public void run() {
		System.out.println("\nWelcome to SimpleCalc!!!\n");
		runCalc();
		System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
	}
	
	/**
	 *	Prompt the user for expressions, run the expression evaluator,
	 *	and display the answer.
	 */
	public void runCalc() {
		boolean gameOver = false; // determines whether the user has quit or not
		List <String> tokens = new ArrayList<String>(); // holds the tokenized input expression

		while(gameOver == false) // runs the expression evaluator until the user enters 'q'
		{
			String expression = Prompt.getString("");
			while (expression.equalsIgnoreCase("h")) // prints help menu
			{
				printHelp();
				expression = Prompt.getString("");
			}
			if(expression.equalsIgnoreCase("q")) // quits game
			{
				gameOver = true;
				break;
			}
			tokens = e.tokenizeExpression(expression);
			double answer = evaluateExpression(tokens);
			System.out.println(answer);
		}
	}
	
	/**	Print help */
	public void printHelp() {
		System.out.println("Help:");
		System.out.println("  h - this message\n  q - quit\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'\n");
	}
	
	/**
	 *	Evaluate expression and return the value
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			a double value of the evaluated expression
	 */
	public double evaluateExpression(List<String> tokens) 
	{
		double value = 0; // the final value of the expression
		boolean hasParen = false; // whether the expression has parenthesis or not

		for(int i = 0; i<tokens.size(); i++) // used to determine whether there are parenthesis
		{
			if(tokens.get(i).equals("(")) hasParen = true;
		}

		if(!hasParen) // solves the expression if it has no parenthesis
		{
			for(int i = 0; i<tokens.size(); i++)
			{
				char currentToken = tokens.get(i).charAt(0); // char value of current token, used to check if it is an operator

				if(!e.isOperator(currentToken)) // if its not an operator, push onto value stack
					valueStack.push(Double.parseDouble(tokens.get(i)));
				else // if it is an operator
				{
					if(!operatorStack.isEmpty() && hasPrecedence(tokens.get(i), operatorStack.peek()))
					{	
						solve();
						while(!operatorStack.isEmpty() && hasPrecedence(tokens.get(i), operatorStack.peek())) solve();
						operatorStack.push(tokens.get(i));
					}
					else operatorStack.push(tokens.get(i));
				}
			}
		}
		else if(hasParen) // solves the expression if it has parenthesis
		{
			for(int i = 0; i<tokens.size(); i++)
			{
				if(e.isOperator(tokens.get(i).charAt(0)))
				{
					if(!operatorStack.isEmpty()) 
					{
						if(tokens.get(i).equals(")")) // deals with parenthesis
						{
							while(!operatorStack.peek().equals("(")) // iterates until opening parenthesis is found
							{
								solve();
							}
							operatorStack.pop();
						}
						else if(tokens.get(i).equals("(")) // pushes opening parenthesis onto the operator stack
						{
							operatorStack.push(tokens.get(i));
						}
						else if(hasPrecedence(tokens.get(i), operatorStack.peek())) // determines whether to solve before pushing the next operator onto the stack
						{
							while(!operatorStack.isEmpty() && hasPrecedence(tokens.get(i), operatorStack.peek()))
							{
								solve();
							}
							operatorStack.push(tokens.get(i));
						}
						else operatorStack.push(tokens.get(i));
					}
					else operatorStack.push(tokens.get(i));
				}
				else valueStack.push(Double.parseDouble(tokens.get(i))); // if not an operator, push onto value stack
			}
		}

		while (operatorStack.isEmpty() == false) solve(); // if there are any operators left over, finishes solving the equaiton

		value = valueStack.pop(); // the solution will be the last number left in the value stack
		return value;
	}

	/** 
	 * Pops two values and an operator from their respective stacks,
	 *  performs the operation, then pushes the result onto the valueStack
	 */
	public void solve()
	{
		String operator = operatorStack.pop(); // operator to be used
		double num2 = valueStack.pop(); // second value
		double num1 = valueStack.pop(); // first value

		if(operator.equals("%")) valueStack.push(num1%num2);
		else if(operator.equals("/")) valueStack.push(num1/num2);
		else if(operator.equals("*")) valueStack.push(num1*num2);
		else if(operator.equals("+")) valueStack.push(num1+num2);
		else if(operator.equals("-")) valueStack.push(num1-num2);
		else if(operator.equals("^")) valueStack.push(Math.pow(num1, num2));
	}
	
	/**
	 *	Precedence of operators
	 *	@param op1	operator 1
	 *	@param op2	operator 2
	 *	@return		true if op2 has higher or same precedence as op1; false otherwise
	 *	Algorithm:
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and 
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2) {
		if (op1.equals("^")) return false;
		if (op2.equals("(") || op2.equals(")")) return false;
		if ((op1.equals("*") || op1.equals("/") || op1.equals("%")) 
				&& (op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}
	 
}
