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
public class CavaloMarinho extends SerMarinho{
    protected int dir,distPercorridaX,distPercorridaY;
    
    public CavaloMarinho(){
        super(1000,1,"S" ,Color.YELLOW);
        distPercorridaX = 0;
        distPercorridaY = 0;
                
        
        dir = Utilitarios.numeroAleatorio(0,1000); // 0 = esquerda , 1 = direita , 2 = baixo, 3 = cima
        
    } 
     public int getDistanciaParaCacar(){
        return 2;
    }
    
    public boolean isPresa(SerMarinho sm){
        return sm instanceof Plancton;
    }
    
    public void movimento(){
        int xAtual = getX();
        int yAtual = getY();
        
    if (dir <= 500){
        if (Utilitarios.numeroAleatorio(0,100) < distPercorridaX){
            dir = Utilitarios.numeroAleatorio(501,1000);
            distPercorridaX = 0;
        }
        else if(dir <= 250){
            setX(xAtual-2);
            distPercorridaX += 2;
            int movY = Utilitarios.numeroAleatorio(-2, 2);
            setY(getY() + movY);
        }
        else{
            setX(xAtual+2);
            distPercorridaX += 2;
            int movY = Utilitarios.numeroAleatorio(-2, 2);
            setY(getY() + movY);
        }
    }
    else {
        if (Utilitarios.numeroAleatorio(0,100) < distPercorridaY){
                dir = Utilitarios.numeroAleatorio(0,500);
                distPercorridaY = 0;
            }
            else if(dir >= 750){
                setY(yAtual-2);
                 distPercorridaY += 2;
                int movX = Utilitarios.numeroAleatorio(-2, 2);
                setX(getX() + movX);
            }
            else{
                setY(yAtual+2);
                distPercorridaY += 2;
                int movX = Utilitarios.numeroAleatorio(-2, 2);
                setX(getX() + movX);
            }
    }
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    

   
}
