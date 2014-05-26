package br.edu.compilador;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author francisco.massetto
 */
public class ComandoEscrita extends Comando {

    private Object elemento;

    public ComandoEscrita(Object elemento) {
        this.elemento = elemento;
    }

    public ComandoEscrita() {
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
