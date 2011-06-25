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
public abstract class Plancton extends SerMarinho {
    
    public Plancton(int energia, int velocidade, String icone, Color cor) {
        super(energia,velocidade,icone,cor);
        
    }

    public void movimento() {
        int movX = Utilitarios.numeroAleatorio(-5, 5);
        int movY = Utilitarios.numeroAleatorio(-5, 5);
        setX(getX() + movX);
        setY(getY() + movY);
    }


}
