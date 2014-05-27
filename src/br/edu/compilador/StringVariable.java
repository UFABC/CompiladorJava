package br.edu.compilador;

public class StringVariable extends Variable<String> {
	
	public StringVariable(String name)
	{
		this(name, "");
		this.initialized = false;
	}
	
	public StringVariable(String name, String value) {
		super(name, value);
	}

	@Override
	public Variable<?> copy() {
		return new StringVariable(this.name, this.value);
	}

}
