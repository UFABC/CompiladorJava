/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author francisco.massetto
 */
public class RTVar {
    private String  nome;
    private int     valor;
    private boolean initialized;

    public RTVar() {
        /*do nothing */
    }
    
    public RTVar(String nome, int valor, boolean initialized) {
        this.nome = nome;
        this.valor = valor;
        this.initialized = initialized;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * @return the initialized
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * @param initialized the initialized to set
     */
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
    
    
}
