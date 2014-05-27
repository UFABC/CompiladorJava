package br.edu.compilador.commands;

import br.edu.compilador.Function;

public class CommandFunction extends Command {
	private Function function;
	
	public CommandFunction(String name) {
		function = new Function(name);
	}
	
	public Function getFunction() {
		return function;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toC() {
		return "int " + function.getName() + "(){";
	}

}
