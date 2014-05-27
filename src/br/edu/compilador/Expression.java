package br.edu.compilador;

import java.util.ArrayList;
import java.util.Iterator;

public class Expression<T> {
	public ArrayList<T> values;
	public ArrayList<Integer> operators;
	
	public Expression() {
		this.values = new ArrayList<T>();
		this.operators = new ArrayList<Integer>();
	}
	
	public String toC() {
		String cValue = "";
		for (int i = 0; i < values.size(); i++) {
			cValue += values.get(i).toString();
			
			if (i < operators.size() && operators.size() > 0) {
				cValue += " " + operators.get(0) + " ";
			}
		}
		return cValue;
	}
}
