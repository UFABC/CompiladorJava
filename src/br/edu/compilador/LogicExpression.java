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
		return left.toString() + " " + symbolToString()  + " " + right.toString();
	}
	
	public String symbolToString () {
		switch (operator) {
		case JujuParserTokenTypes.T_and:
			return "&&";
			
		case JujuParserTokenTypes.T_or:
			return "||";
			
		case JujuParserTokenTypes.T_lt:
			return "<";
			
		case JujuParserTokenTypes.T_lteq:
			return "<=";
			
		case JujuParserTokenTypes.T_gt:
			return ">";
			
		case JujuParserTokenTypes.T_gteq:
			return ">=";
		default:
			return "==";
			
		}
	}
}
