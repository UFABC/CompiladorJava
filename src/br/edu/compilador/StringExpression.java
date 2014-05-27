package br.edu.compilador;

public class StringExpression extends Expression {

	public StringExpression(String value) {
		super();
		this.values.add(value);
	}
	
	public StringExpression(StringVariable value) {
		super();
		this.values.add(value);
	}

}
