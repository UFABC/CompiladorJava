package br.edu.compilador.commands;

import br.edu.compilador.IntegerVariable;
import br.edu.compilador.StringVariable;
import br.edu.compilador.Variable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juju
 */
public class CommandRead extends Command{
    private Variable var;

    public CommandRead(Variable var) {
        this.var = var;
    }
    
    public CommandRead(){
        
    }
    
    public void run(){
        String txt;
        txt = javax.swing.JOptionPane.showInputDialog(" Digite o valor de " + var.getName());
        if (var instanceof IntegerVariable)
        	((IntegerVariable) var).setValue(Integer.parseInt(txt));
        if (var instanceof StringVariable)
        	((StringVariable) var).setValue(txt);
    }

    /**
     * @return the var
     */
    public Variable getVar() {
        return var;
    }

    /**
     * @param var the var to set
     */
    public void setVar(Variable var) {
        this.var = var;
    }

	@Override
	public String toC() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
