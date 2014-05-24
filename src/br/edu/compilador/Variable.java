package br.edu.compilador;

public abstract class Variable {
	public String name;
	public char[] value;
	
	public abstract void SetValue(char[] value);
	
	public void SetName(String name)
	{
		this.name = name;
	}
}
