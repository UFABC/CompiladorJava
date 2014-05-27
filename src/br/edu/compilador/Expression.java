package br.edu.compilador;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Expression<T> {
	public ArrayList<T> values;
	public ArrayList<Integer> operators;
	
	public Expression() {
		this.values = new ArrayList<T>();
		this.operators = new ArrayList<Integer>();
	}
	
	public void add(T value){
		values.add(value);
	}
	
	public void add(T value, Integer operator) {
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
