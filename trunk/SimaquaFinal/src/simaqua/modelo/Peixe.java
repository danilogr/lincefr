/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simaqua.modelo;

import java.awt.Color;
import simaqua.utils.Utilitarios;
import simaqua.modelo.Aquario;

/**
 *
 * @author Daniel
 */
public abstract class Peixe extends SerMarinho{
    protected int distPercorridaX,distPercorridaY, dirX, dirY;
    protected String iconeEsq, iconeDir;
    private static int xAnterior;

    public Peixe(int energia, int velocidade, String iconeEsq, String iconeDir, Color cor){
        super(energia, velocidade, iconeDir, cor);
        this.iconeDir = iconeDir;
        this.iconeEsq = iconeEsq;
        
        distPercorridaX = 0;
        distPercorridaY = 0;
        xAnterior = 0;
        
        int temp = Utilitarios.numeroAleatorio(0,1000);        
        if (temp < 500)  dirX = 0;
        else             dirX = 1;
        
        temp = Utilitarios.numeroAleatorio(0,1000);        
        if (temp < 500)  dirY = 0;
        else             dirY = 1;

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
                    setIcone(iconeEsq);
            }
            else{
                    setX(xAtual+3);
                    distPercorridaX += 3;
                    setIcone(iconeDir);
            }

            if (Utilitarios.numeroAleatorio(0,2200) < distPercorridaY){
                setDirY(1 - getDirY());
                distPercorridaY=0;
            }
            else if(getDirY() == 0){
                setY(yAtual-1);
                distPercorridaY += 1;
            }
            else{
                setY(yAtual+1);
                distPercorridaY += 1;
            }
        }

    }
    
}
