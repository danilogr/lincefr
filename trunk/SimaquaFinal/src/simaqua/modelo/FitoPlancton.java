/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simaqua.modelo;

import java.awt.Color;

/**
 *
 * @author d4n1l0d
 */
public class FitoPlancton extends Plancton{
    
    public final static int ENERGIA_FITOPLANCTON = 100;
    
    public FitoPlancton()
    {
        super(ENERGIA_FITOPLANCTON,1,".", Color.BLUE);    
    }
    
    @Override
    public boolean comFome() {
        return false;
    }


    @Override
    protected void cacar() {
    }

    @Override
    public boolean isPresa(SerMarinho sm) {
        return false;
    }

    @Override
    public int getDistanciaParaCacar() {
        return 0;
    }
    
}
