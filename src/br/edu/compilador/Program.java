package br.edu.compilador;

import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juju
 */
public class Program {
    private ArrayList<Comando> comandos;
    
    public Program(){
        comandos = new ArrayList<Comando>();
    }
    
    public void run(){
        for(Comando c: comandos){
            c.run();
        }
    }
    
    public String convert()
    {
    	return "ConvertedCode;";
    }
    
    public void addComando(Comando c){
        comandos.add(c);
    }
    
    
}
