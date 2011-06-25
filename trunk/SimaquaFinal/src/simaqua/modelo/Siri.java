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
public class Siri extends SerMarinho{
    protected int distPercorrida,dir;
    private static int yAnterior;
    private int cont;
    //protected String iconeEsq, iconeDir;
    
    public Siri(){
        super(2000,2,"\\^/" ,Color.ORANGE);
        distPercorrida = 0;
        yAnterior = 0;
        // this.iconeDir = iconeDir;
        // this.iconeEsq = iconeEsq;
        dir = Utilitarios.numeroAleatorio(0,1000); // 0 a 500 = esquerda , 501 a 1000 = direita
        cont = 0;
    } 
       
    public void movimento(){
        int xAtual = getX();
        int yAtual = getY();
        
        if (yAtual != yAnterior)
        {
            yAnterior = getY();
            setY(yAtual + 5);
        }
        else {
            if ( cont < 90)
            {
               if (getIcone().equals("\\^/")){
                    setIcone("|^|");
               }
               else {
                   setIcone("\\^/");
               }
               cont += 10;
            }
            else{
                cont = Utilitarios.numeroAleatorio(0, 1000);
                 setIcone("\\^/");
                if (Utilitarios.numeroAleatorio(0,200) < distPercorrida){
                    if (dir <=500) {
                        dir = 501;
                    }
                    else {
                        dir = 500;
                    }
                    distPercorrida = 0;
                }
                else if (dir <= 500){
                     setX(xAtual-4);
                     distPercorrida += 4;
                }
                else{
                     setX(xAtual+4);
                     distPercorrida += 4;
                }
            }
        }
    }
    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

     public int getDistanciaParaCacar(){
        return 2;
    }
    
    public boolean isPresa(SerMarinho sm){
        return sm instanceof Plancton;
    }

   
}
