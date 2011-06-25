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
public class Golfinho extends SerMarinho{
    protected int distPercorridaX,distPercorridaY,dirX,dirY;
    private static int xAnterior;
     
    
    public Golfinho(){
        super(3500,2,"}=O-" ,Color.MAGENTA);
        xAnterior = 0;
       
        distPercorridaX = 0;
        distPercorridaY = 0;
        
        int temp = Utilitarios.numeroAleatorio(0,1000);        
        if (temp < 500)  dirX = 0;
        else             dirX = 1;
        
        temp = Utilitarios.numeroAleatorio(0,1000);        
        if (temp < 500)  dirY = 0;
        else             dirY = 1;
    }
    
    public void movimento(){
        int xAtual = getX(); 
        int yAtual = getY();
       
        if (Utilitarios.numeroAleatorio(0,12000) < distPercorridaX){
            setDirX(1 - getDirX());
            distPercorridaX = 0;
        }
        else{
            if(xAnterior == xAtual){
                setDirX(1-getDirX());
                distPercorridaX = 0;
            }
            
            xAnterior = getX();
            
            if(getDirX() == 0){
                    setX(xAtual-3);                
                    distPercorridaX += 3;
                    setIcone("-(O>={");
            }
            else{
                    setX(xAtual+3);
                    distPercorridaX += 3;
                    setIcone("}=<O)-");
            }

            if (Utilitarios.numeroAleatorio(0,12000) < distPercorridaY){
                setDirY(1 - getDirY());
            }
            else if(getDirY() == 0){
                setY(yAtual-3);
                distPercorridaY += 3;
            }
            else{
                setY(yAtual+3);
                distPercorridaY += 3;
            }
        }
    }
        
    public int getDistanciaParaCacar(){
        return 15;
    }
    
    public boolean isPresa(SerMarinho sm){
        if (sm instanceof Sardinha ||
            sm instanceof Atum ||
            sm instanceof Siri ||
            sm instanceof CavaloMarinho) return true;
        return false;
        
    }
    
     public int getDirX() {
        return dirX;
    }

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

    public int getDirY() {
        return dirY;
    }

    public void setDirY(int dirY) {
        this.dirY = dirY;
    }

    public int getDistPercorridaX() {
        return distPercorridaX;
    }

    public void setDistPercorridaX(int distPercorridaX) {
        this.distPercorridaX = distPercorridaX;
    }

    public int getDistPercorridaY() {
        return distPercorridaY;
    }

    public void setDistPercorridaY(int distPercorridaY) {
        this.distPercorridaY = distPercorridaY;
    }

    public static int getyAnterior() {
        return xAnterior;
    }

    public static void setyAnterior(int yAnterior) {
        Golfinho.xAnterior = xAnterior;
    }
}
   

