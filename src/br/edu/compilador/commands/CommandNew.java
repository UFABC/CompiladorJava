package br.edu.compilador.commands;

import br.edu.compilador.*;

public class CommandNew extends Command {
	private Variable<?> variable;
	
	
	public CommandNew(Variable<?> variable) {
		this.variable = variable;
	}

	public Variable<?> getVariable() {
		return variable;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toC() {
		String cNew = "";
		if (variable instanceof IntegerVariable) {
			cNew += "int ";
		} else if (variable instanceof StringVariable) {
			cNew += "string ";
		}
		cNew += variable.name;
		
		if (variable.initialized) {
			cNew += " " + variable.value;
		}
		
		return cNew + ";";
	}

}
