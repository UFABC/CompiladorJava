package br.edu.compilador;

public abstract class Variable<Type> extends Symbol {
	public Type value;
	
	public Variable(String name, Type value) {
		this.name = name;
		this.value = value;
	}
	
	public void setValue(Type value)
	{
		this.value = value;
	}

	public Type getValue() {
		return value;
	}
	
}
