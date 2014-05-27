package br.edu.compilador;

public class LogicExpression {
	
	private int operator;
	private Object right;
	private Object left;

	public LogicExpression(Object left, Object right, int operator) {
		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	public String toC()
	{
		return left.toString() + " " + JujuUtils.tokenToString(operator)  + " " + right.toString();
	}
	

}
