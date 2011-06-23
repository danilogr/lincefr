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
    protected int posicaoInicialX,posicaoInicialY,dir;
    
    public CavaloMarinho(){
        super(500,"S" ,Color.YELLOW);
        posicaoInicialX = getX();
        posicaoInicialY= getY();
        
        dir = Utilitarios.numeroAleatorio(0,3); // 0 = esquerda , 1 = direita , 2 = cima, 3 = baixo
        
    } 
    
    
    public void movimento(){
        int xAtual = getX();
        int yAtual = getY();
        
        if (dir == 0) {
            setX(xAtual-1);
            
        }
        else if (dir == 1) {
            setX(xAtual+1);
        }
        else if (dir == 2){
            setY(yAtual+1);
        }
        else {
            setY(yAtual-1);
        }
                    
        
        
        int distPercorridaX = xAtual - getPosicaoInicialX();
        if (Utilitarios.numeroAleatorio(0,1000) < distPercorrida){
            setDir(1 - dir);
            setPosicaoInicial(xAtual);
        }
        else if(dir == 0){
            setX(xAtual-2);
        }
        else{
            setX(xAtual+2);
        }
        
        int movY = Utilitarios.numeroAleatorio(-10, 10);
        setY(getY() + movY);

    }
    
}