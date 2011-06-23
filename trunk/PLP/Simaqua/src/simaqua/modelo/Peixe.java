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
public abstract class Peixe extends SerMarinho{
    protected int distPercorridaX,distPercorridaY, dirX, dirY;
    protected String iconeEsq, iconeDir;

    public Peixe(int energia, String iconeEsq, String iconeDir, Color cor){
        super(energia, iconeDir, cor);
        this.iconeDir = iconeDir;
        this.iconeEsq = iconeEsq;
        
        distPercorridaX = 0;
        distPercorridaY = 0;
        
        dirX = Utilitarios.numeroAleatorio((int)0,(int)1); // 0 = esquerda, 1 = direita
        dirY = Utilitarios.numeroAleatorio((int)0,(int)1);
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
  
    //teste
    
    public void movimento(){
        int xAtual = getX(); 
        int yAtual = getY();
       
        if (Utilitarios.numeroAleatorio(0,5000) < distPercorridaX){
            setDirX(1 - getDirX());
        }
        else if(getDirX() == 0){
            setX(xAtual-3);
            distPercorridaX += 3;
            setIcone(iconeEsq);
        }
        else{
            setX(xAtual+3);
            distPercorridaX += 3;
            setIcone(iconeDir);
        }
        
        if (Utilitarios.numeroAleatorio(0,600) < distPercorridaY){
            setDirY(1 - getDirY());
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
