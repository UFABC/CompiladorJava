
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author francisco.massetto
 */
public class Programa {
    private ArrayList<Comando> comandos;
    
    public Programa(){
        comandos = new ArrayList<Comando>();
    }
    
    public void run(){
        for(Comando c: comandos){
            c.run();
        }
    }
    
    public void addComando(Comando c){
        comandos.add(c);
    }
    
    
}
