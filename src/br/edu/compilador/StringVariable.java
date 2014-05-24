package br.edu.compilador;

public class StringVariable extends Variable<String> {
	
	public StringVariable(String name)
	{
		this(name, "");
	}
	
	public StringVariable(String name, String value) {
		super(name, value);
	}

	@Override
	public String toC() {
		// TODO Auto-generated method stub
		return null;
	}

}
