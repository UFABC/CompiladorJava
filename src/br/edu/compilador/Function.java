package br.edu.compilador;

public class Function implements Symbol{
	public String name;
	
	public Function(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
