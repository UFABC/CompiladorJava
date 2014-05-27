package br.edu.compilador.commands;

import br.edu.compilador.Expression;
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
		String cCode = "cout << ";
        if (elemento instanceof Variable) {
            Variable<?> var = (Variable<?>)elemento;
            cCode += var.getName();
        } else if (elemento instanceof Expression<?>) {
            String s = ((Expression<?>) elemento).toC();
            cCode += s;
        }		
        return cCode + ";";
	}
}
