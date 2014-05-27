package br.edu.compilador.commands;

import br.edu.compilador.LogicExpression;

public class CommandWhile extends Command {
	LogicExpression logic;
	public CommandWhile(LogicExpression actualLogic) {
		this.logic = actualLogic;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toC() {
		String cValue = "while (";
		cValue += this.logic.toC();
		return cValue + ") {";
	}

}
