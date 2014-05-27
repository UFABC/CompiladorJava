package br.edu.compilador.commands;

import br.edu.compilador.*;

public class CommandAttribution extends Command{
	Variable<?> variable;
	
	Expression<?> attributedValue;

	public CommandAttribution(Variable<?> variable, String newValue) {
		this.variable = variable;
		this.attributedValue = new StringExpression(newValue);
		
		((StringVariable) this.variable).setValue(newValue);
		
	}
	
	public CommandAttribution(Variable<?> variable, Integer newValue) {
		this.variable = variable;
		this.attributedValue = new MathExpression(newValue);
		
		((IntegerVariable) this.variable).setValue(newValue);
	}
	
	public CommandAttribution(Variable<?> variable, Expression<?> newValue) {
		this.variable = variable;
		this.attributedValue = newValue;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toC() {
		String cValue = variable.name;
		cValue += " = " + attributedValue.toC();
		return cValue + ";";
	}

}
