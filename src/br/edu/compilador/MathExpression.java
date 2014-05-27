package br.edu.compilador;

public class MathExpression extends Expression {

	public MathExpression(Integer firstValue) {
		super();
		values.add(firstValue);
	}
	
	public MathExpression(IntegerVariable firstValue) {
		super();
		values.add(firstValue);
	}

}
