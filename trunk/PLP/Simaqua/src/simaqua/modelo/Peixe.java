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
    protected int posicaoInicial,dir;
    protected String iconeEsq, iconeDir;
    
    public Peixe(int energia, String iconeEsq, String iconeDir, Color cor){
        super(energia, iconeDir, cor);
        this.iconeDir = iconeDir;
        this.iconeEsq = iconeEsq;
        posicaoInicial = getX();
        dir = Utilitarios.numeroAleatorio(0,1); // 0 = esquerda, 1 = direita
    } 
    
    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getPosicaoInicial() {
        return posicaoInicial;
    }

    public void setPosicaoInicial(int posicaoInicial) {
        this.posicaoInicial = posicaoInicial;
    }
    
    
    
    
    public void movimento(){
        int xAtual = getX();  
        
        int distPercorrida = xAtual - getPosicaoInicial();
        if (Utilitarios.numeroAleatorio(0,1000) < distPercorrida){
            setDir(1 - dir);
            setPosicaoInicial(xAtual);
        }
        else if(dir == 0){
            setX(xAtual-2);
            setIcone(iconeEsq);
        }
        else{
            setX(xAtual+2);
            setIcone(iconeDir);
        }
        
        int movY = Utilitarios.numeroAleatorio(-10, 10);
        setY(getY() + movY);

    }
    
}
