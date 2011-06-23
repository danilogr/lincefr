/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simaqua.modelo;

import java.awt.Color;

/**
 *
 * @author Daniel
 */
public class Atum extends Peixe{
    public Atum(){
        super(2000,">-)))>",Color.DARK_GRAY);
        
    }
    
    public int getDistanciaParaCacar(){
        return 10;
    }
    
    public boolean isPresa(SerMarinho sm){
        if (sm instanceof Plancton || sm instanceof Sardinha){
            return true;
        }
        return false;
    }
    
}
