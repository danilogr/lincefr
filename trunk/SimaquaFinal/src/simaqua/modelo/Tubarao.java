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
public class Tubarao extends Peixe{

    public Tubarao() {
        super(4000,3, "<@)))=<", ">=(((@>", Color.cyan);
    }
    
    
    public int getDistanciaParaCacar(){
        return 30;
    }
    
    public boolean isPresa(SerMarinho sm){
        if (sm instanceof Sardinha      || 
            sm instanceof Atum          ||
            sm instanceof CavaloMarinho || 
            sm instanceof Golfinho      ||
            sm instanceof Siri) return true;
        return false;
    }    
    
}
