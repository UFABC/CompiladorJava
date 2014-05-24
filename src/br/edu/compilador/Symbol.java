package br.edu.compilador;

public abstract class Symbol {
	public String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract String toC();
}
