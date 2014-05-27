package br.edu.compilador.commands;

import br.edu.compilador.LogicExpression;

public class CommandIf extends Command{
	public LogicExpression logic;
	
	public CommandIf(LogicExpression expression) {
		this.logic = expression;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toC() {
		String ifExpression = "if (";
		ifExpression += this.logic.toC();
		return ifExpression + ") {";
	}
}
