/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simaqua.modelo;

/**
 * Essa classe é uma Thread responsável pela passagem do tempo.
 * @author Daniel
 */
public class Simulacao extends Thread {

    public static final int VELOCIDADE = 10;
    private boolean continuaSimulacao;
    private Aquario ambiente;

    public Simulacao(Aquario ambiente) {
        this.ambiente = ambiente;
        continuaSimulacao = true;
    }

    public void pararSimulacao() {
        this.continuaSimulacao = false;
    }

    @Override
    public void run() {
        while (continuaSimulacao) {
            ambiente.passagemDoTempo();
            try {
                Thread.sleep(100 / VELOCIDADE);
            } catch (InterruptedException ex) {
            }
        }
    }
}
