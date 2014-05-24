package br.edu.compilador;

public class IntegerVariable extends Variable<Integer>{
	public IntegerVariable(String name)
	{
		this(name, 0);
	}
	
	public IntegerVariable(String name, Integer value) {
		super(name, value);
	}

	@Override
	public String toC() {
		// TODO Auto-generated method stub
		return null;
	}

}
