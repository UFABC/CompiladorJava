package br.edu.compilador.commands;

public class CommandEnd extends Command {

	@Override
	public void run() {

	}

	@Override
	public String toC() {
		return "}";
	}

}
