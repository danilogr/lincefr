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
public class Sardinha extends Peixe{
    public Sardinha(){
        super(1000,"<(-<",">-)>",Color.WHITE);
        
    }
    
    public int getDistanciaParaCacar(){
        return 5;
    }
    
    public boolean isPresa(SerMarinho sm){
        return sm instanceof Plancton;
    }
    
}
