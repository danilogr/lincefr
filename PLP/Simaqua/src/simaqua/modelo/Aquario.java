/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simaqua.modelo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import simaqua.utils.Utilitarios;

/**
 * Um aquário é um ambiente onde vivem seres marinhos
 * @author Daniel
 */
public class Aquario extends JPanel {

    /**
     * Um aquário possui nutrientes, os quais contém energia.
     * Essa energia pode eventualmente ser convertida em vida
     */
    private int energiaNutrientes;
    /**
     * Um aquário possui seres marinhos vivendo nele
     */
    private List<SerMarinho> seresMarinhos;

    /**
     * Construtor simples. Apenas inicializa a lista de
     * seres marinhos.
     */
    public Aquario() {
        this.seresMarinhos = new ArrayList<SerMarinho>();
    }

    /**
     * Método que "zera" o aquário, matando todos os seus
     * seres.
     * Isso é feito em duas etapas: o método matar()
     * força o encerramento da Thread
     * Numa segunda etapa, a lista é esvaziada e a energia
     * de nutrientes restabelecida
     */
    public void inicializarAquario() {
        for (SerMarinho sm : seresMarinhos) {
            sm.matar();
        }
        seresMarinhos.clear();
        energiaNutrientes = 100000;
    }

    /**
     * Adiciona um ser marinho ao aquário.
     * Este método estabelece o vínculo entre o ser e o
     * seu ambiente, e também dá vida à simulação
     * (iniciando a Thread do ser)
     * @param sm o ser marinho a ser incluído
     */
    public void adicionarSerMarinho(SerMarinho sm) {
        sm.setAmbiente(this);
        sm.setX(Utilitarios.numeroAleatorio(0, getLargura()));
        sm.setY(Utilitarios.numeroAleatorio(0, getAltura()));
        this.seresMarinhos.add(sm);
        sm.start();
    }

    /**
     * Encontra os seres marinhos próximos a um determinado ser
     * @param sm o ser de referência
     * @return os seres próximos a sm (considerando a distância de caça de sm)
     */
    public List<SerMarinho> getSeresMarinhosProximos(SerMarinho sm) {
        List<SerMarinho> seresProximos = new ArrayList<SerMarinho>();
        for (SerMarinho sm2 : seresMarinhos) {
            if (sm.proximo(sm2)) {
                seresProximos.add(sm2);
            }
        }
        return seresProximos;
    }

    /**
     * A energia total do aquário é a soma da energia dos
     * nutrientes com a soma das energias dos seres que
     * nele vivem
     * @return a energia total do aquário
     */
    public int getEnergiaTotal() {
        int energiaTotal = energiaNutrientes;
        for (SerMarinho sm : seresMarinhos) {
            energiaTotal += sm.getEnergia();
        }
        return energiaTotal;
    }

    /**
     * Este método acrescenta uma certa quantidade de energia
     * à energia de nutrientes do aquário
     * @param energiaDevolvida quantidade de energia a ser acrescentada ao aquário
     */
    public void devolverEnergia(int energiaDevolvida) {
        energiaNutrientes += energiaDevolvida;
    }

    public int getAltura() {
        return getHeight();
    }

    public int getLargura() {
        return getWidth();
    }

    /**
     * Desenha a tela, para representar os seres marinhos
     * em suas vidas
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 128));
        g.fillRect(0, 0, getWidth(), getHeight() - 50);
        g.setColor(new Color(0, 88, 0));
        g.fillRect(0, getHeight() - 50, getWidth(), 50);
        Font f = g.getFont();
        for (SerMarinho sm : seresMarinhos) {
            g.setColor(sm.getCor());
            if (sm.comFome()) {
                g.setFont(new Font(f.getFontName(), Font.PLAIN, f.getSize()));
            } else {
                g.setFont(new Font(f.getFontName(), Font.BOLD, f.getSize()));
            }
            g.drawString(sm.getIcone(), sm.getX(), sm.getY());
        }
        g.setColor(new Color(100, 100, 250));
        g.drawString("Nutrientes: " + energiaNutrientes, getLargura() - 250, getAltura() - 30);
        g.drawString("Energia total: " + getEnergiaTotal(), getLargura() - 250, getAltura() - 10);
        g.setColor(new Color(0, 180, 80));
        g.drawString("Num. Threads: " + Thread.activeCount(), 10, getAltura() - 10);
    }

    /**
     * Atualiza o "status" do aquário.
     * Caso haja energia suficiente, uma nova vida (Plancton)
     * é criada a partir dos nutrientes.
     */
    public void passagemDoTempo() {
        if (energiaNutrientes >= Plancton.ENERGIA_PLANCTON) {
            energiaNutrientes -= Plancton.ENERGIA_PLANCTON;
            adicionarSerMarinho(new Plancton());
        }
        repaint();
    }
    

    /**
     * Remove um ser do aquário. Sua energia é devolvida
     * ao ambiente em forma de nutrientes.
     * Caso um ser tenha sido devorado, sua energia é
     * zero, pois a mesma foi absorvida pelo devorador
     * no momento de sua morte
     * @param sm o ser que morreu e precisa ser removido do aquário
     */

    public void removerSerMarinho(SerMarinho sm) {
        seresMarinhos.remove(sm);
        energiaNutrientes += sm.getEnergia(); // Pega energia
        // lol
        
    }
   

}

