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
    ArrayList<RTVar> symbols;
    
    public RTSymbolTable(){
        symbols = new ArrayList<RTVar>();
    }
    
    public void add(RTVar elemento){
        symbols.add(elemento);
    }
    
    public RTVar getByName(String varName){
        for(RTVar item: symbols){
            if (item.getNome().equals(varName)){
                return item;
            }
        }
        return null;
    }
    
}
