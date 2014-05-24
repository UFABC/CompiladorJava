package br.edu.compilador;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author francisco.massetto
 */
public class ComandoLeitura extends Comando{
    private RTVar var;

    public ComandoLeitura(RTVar var) {
        this.var = var;
    }
    
    public ComandoLeitura(){
        
    }
    
    public void run(){
        String txt;
        txt = javax.swing.JOptionPane.showInputDialog(" Digite o valor de "+var.getNome());
        var.setValor(Integer.parseInt(txt));
    }

    /**
     * @return the var
     */
    public RTVar getVar() {
        return var;
    }

    /**
     * @param var the var to set
     */
    public void setVar(RTVar var) {
        this.var = var;
    }
    
    
}
