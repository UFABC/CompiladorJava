package br.edu.compilador;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Expression {
	public ArrayList<Object> values;
	public ArrayList<Integer> operators;
	
	public Expression() {
		this.values = new ArrayList<Object>();
		this.operators = new ArrayList<Integer>();
	}
	
	public void add(Object value){
		values.add(value);
	}
	
	public void add(Object value, Integer operator) {
		values.add(value);
		operators.add(operator);
	}
	
	public String toC() {
		String cValue = "";
		for (int i = 0; i < values.size(); i++) {
			cValue += values.get(i).toString();
			
			if (i < operators.size() && operators.size() > 0) {
				cValue += " " + JujuUtils.tokenToString(operators.get(i)) + " ";
			}
		}
		return cValue;
	}
}
