package br.edu.compilador.commands;

import br.edu.compilador.Variable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juju
 */
public class CommandWrite extends Command {

    private Object elemento;

    public CommandWrite(Object elemento) {
        this.elemento = elemento;
    }

    public CommandWrite() {
    }

    @Override
    public void run() {
        if (elemento instanceof Variable) {
            Variable<?> var = (Variable<?>)elemento;
            javax.swing.JOptionPane.showMessageDialog(null,var.getName() + "=" + var.getValue());
        } else if (elemento instanceof java.lang.String) {
            String s = (String)elemento;
            javax.swing.JOptionPane.showMessageDialog(null, s);
        }

    }

	@Override
	public String toC() {
		// TODO Auto-generated method stub
		return null;
	}
}
