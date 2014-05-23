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
        if (elemento instanceof RTVar) {
            RTVar var = (RTVar)elemento;
            javax.swing.JOptionPane.showMessageDialog(null,var.getNome() + "="+var.getValor());
        } else if (elemento instanceof java.lang.String) {
            String s = (String)elemento;
            javax.swing.JOptionPane.showMessageDialog(null, s);
        }

    }
}
