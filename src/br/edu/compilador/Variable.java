package br.edu.compilador;

public abstract class Variable<Type> implements Symbol {
	public String name;
	public Type value;
	public boolean initialized = false;
	
	public Variable(String name, Type value) {
		this.name = name;
		this.value = value;
		this.initialized = true;
	}
	
	public void setValue(Type value)
	{
		this.value = value;
	}

	public Type getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	public abstract Variable<?> copy ();
	
	public String toString(){
		return name;
	}
}
