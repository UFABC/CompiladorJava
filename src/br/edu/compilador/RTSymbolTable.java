package br.edu.compilador;

import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author francisco.massetto
 */
public class RTSymbolTable {
    ArrayList<Symbol> symbols;
    
    public RTSymbolTable(){
        symbols = new ArrayList<Symbol>();
    }
    
    public void add(Variable<?> element){
//    	System.out.println("Add element: " + element.getName());
        symbols.add(element);
    }
    
    public boolean exists(String varName, Class<?> symbolType){
    	Symbol symbol = getSymbol(varName, symbolType);
    	if(symbol == null)
    		return false;
    	else
    		return true;
    }
    
    public Symbol getSymbol(String varName, Class<?> symbolType){
    	for(Symbol item: symbols){
    		if(symbolType.isInstance(item)){        		
    			if (item.getName().equals(varName)){
    				return item;
    			}
    		}
    	}
    	return null;
    }

	public void add(Function function) {
		symbols.add(function);
	}
}
