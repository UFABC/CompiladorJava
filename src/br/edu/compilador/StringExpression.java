package br.edu.compilador;

public class StringExpression extends Expression<String> {

	public StringExpression(String value) {
		super();
		this.values.add(value);
	}

}
