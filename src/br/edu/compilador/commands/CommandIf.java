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
}
