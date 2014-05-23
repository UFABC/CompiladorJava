
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author francisco.massetto
 */
public class RTSymbolTable {
    ArrayList<RTVar> listaVariaveis;
    
    public RTSymbolTable(){
        listaVariaveis = new ArrayList<RTVar>();
    }
    
    public void add(RTVar elemento){
        listaVariaveis.add(elemento);
    }
    
    public RTVar getByName(String varName){
        for(RTVar item: listaVariaveis){
            if (item.getNome().equals(varName)){
                return item;
            }
        }
        return null;
    }
    
}
