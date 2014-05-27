package br.edu.compilador.commands;

public class CommandIf extends Command{
	public Object exprL;
	public Object exprR;
	public int operator;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toC() {
		String ifExpression = "if (";
		return ifExpression + ") {";
	}

	public Object getExprL() {
		return exprL;
	}

	public void setExprL(Object exprL) {
		this.exprL = exprL;
	}

	public Object getExprR() {
		return exprR;
	}

	public void setExprR(Object exprR) {
		this.exprR = exprR;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	
	
}
