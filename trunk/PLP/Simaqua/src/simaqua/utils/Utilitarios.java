/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simaqua.utils;

/**
 *
 * @author Daniel
 */
public class Utilitarios {
    public static int numeroAleatorio(int min, int max) {
        return (int)(min + Math.random()*(double)(max-min));
    }
    public static double numeroAleatorio(double min, double max) {
        return (min + Math.random()*(max-min));
    }
}
