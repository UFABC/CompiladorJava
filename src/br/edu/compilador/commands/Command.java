package br.edu.compilador.commands;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author francisco.massetto
 */
public abstract class Command {
    public abstract void run();
    
    public abstract String toC();
}
