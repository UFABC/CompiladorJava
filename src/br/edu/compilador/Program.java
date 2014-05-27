package br.edu.compilador;

import java.util.ArrayList;

import br.edu.compilador.commands.Command;
import br.edu.compilador.commands.CommandFunction;
import br.edu.compilador.commands.CommandNew;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juju
 */
public class Program {
    
	private ArrayList<Command> comandos;
    
    private RTSymbolTable symbolTable;
    
    public Program(){
        comandos = new ArrayList<Command>();
        symbolTable = new RTSymbolTable();
    }
    
    public void run(){
        for(Command c: comandos){
            c.run();
        }
    }
    
    public String convert() {
    	String convertedCode = "#include <iostream>#include <string>using namespace std;";
    	for(Command c: comandos) {
            convertedCode += c.toC();
        }
    	return convertedCode;
    }
    
    public boolean existsVariable(String variableName)
    {
    	return symbolTable.exists(variableName, Variable.class);
    }
    
    public Variable<?> getVariable(String variableName)
    {
    	return (Variable<?>) symbolTable.getSymbol(variableName, Variable.class);
    }
    
    public void addCommand(Command c) {
    	if (c instanceof CommandFunction)
    		symbolTable.add(((CommandFunction) c).getFunction());
    	else if (c instanceof CommandNew)
    		symbolTable.add(((CommandNew) c).getVariable());
    		
        comandos.add(c);
    }
    
    
}
