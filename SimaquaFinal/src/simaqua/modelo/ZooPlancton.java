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
public class ZooPlancton extends Plancton {
    public static final int ENERGIA_ZOOPLANCTON = 250;

    public ZooPlancton()
    {
        super(ENERGIA_ZOOPLANCTON,2,",", Color.RED);
    }
    


    @Override
    public boolean isPresa(SerMarinho sm) {
        return sm instanceof FitoPlancton;
    }

    @Override
    public int getDistanciaParaCacar() {
        return 5;
    }
}
