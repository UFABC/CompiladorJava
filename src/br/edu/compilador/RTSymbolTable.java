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
    
    public void add(Symbol element){
        symbols.add(element);
    }
    
    public boolean exists(String varName, Class<?> symbolType){
        for(Symbol item: symbols){
        	if(symbolType.isInstance(item)){        		
        		if (item.getName().equals(varName)){
        			return true;
        		}
        	}
        }
        return false;
    }
    
}
