package br.edu.compilador.commands;

import br.edu.compilador.*;

public class CommandNew extends Command {
	private Variable<?> variable;
	private Object initialValue;
	
	public CommandNew(Variable<?> variable) {
		this.variable = variable;
		this.initialValue = variable.value;
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
		
		cNew += " = " + initialValue.toString();
		if (initialValue.toString().isEmpty()) {
			cNew += "\"\"";
		}
		
		return cNew + ";";
	}

}
