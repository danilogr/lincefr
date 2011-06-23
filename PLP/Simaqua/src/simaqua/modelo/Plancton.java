/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simaqua.modelo;

import java.awt.Color;
import simaqua.utils.Utilitarios;

/**
 *
 * @author Daniel
 */
public class Plancton extends SerMarinho {
    public static final int ENERGIA_PLANCTON = 100;
    public Plancton() {
        super(ENERGIA_PLANCTON,".", Color.BLUE);
    }

    public void movimento() {
        int movX = Utilitarios.numeroAleatorio(-5, 5);
        int movY = Utilitarios.numeroAleatorio(-5, 5);
        setX(getX() + movX);
        setY(getY() + movY);
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
