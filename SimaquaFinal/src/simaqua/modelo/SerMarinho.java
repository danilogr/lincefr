/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simaqua.modelo;

import java.awt.Color;
import java.awt.Point;
import java.util.List;
import simaqua.utils.Utilitarios;

/**
 * Classe que representa um ser marinho
 * @author Daniel
 */
public abstract class SerMarinho extends Thread {

    /*
       Variaveis utilizadas para resolver problemas de concorrência
    */
    
    
    
    
    /**
     * Posição do ser marinho
     */
    private int x, y;
    /**
     * String a ser desenhada no aquário para o ser marinho.
     */
    private String icone;
    /**
     * Cor a ser desenhada no aquário para o ser marinho
     */
    private Color cor;
    /**
     * Quantidade de energia inata que o ser marinho possui.
     * A quantidade de energia é fixa, não sofrendo alteração
     * ao longo de sua vida.
     * Quando um ser morre, sua energia retorna ao ambiente,
     * na forma de nutrientes.
     * Quanto um ser é devorado, sua energia é absorvida
     * pelo devorador.
     */
    private int energia;
    /**
     * Quantidade de energia agregada ao ser marinho
     * por meio de alimentação.
     * Assim que um ser devora outro, toda a energia do
     * ser devorado é passada para o devorador.
     * Essa energia vai sendo metabolizada e devolvida
     * ao ambiente.
     */
    private int energiaAlimento;
    /**
     * Cada alimento demora um certo tempo para ser metabolizado
     * A velocidade do metabolismo depende da diferença de
     * energia entre devorador e devorado.
     */
    
    
    private int velocidade;
    
    
    private int velocidadeMetabolismo;
    /**
     * Seres marinhos envelhecem e eventuamente morrem.
     * A vitalidade armazena o quanto um ser já viveu.
     * Na verdade, é um valor invertido, todo ser começa
     * com vitalidade = 255, e a vitalidade vai decaindo
     * até chegar a zero, quando então o ser morre.
     * Nesse momento, sua energia é devolvida ao ambiente,
     * em forma de nutrientes.
     */
    protected double vitalidade;
    
    
    
    /**
     * Essa variável é uma referência ao ambiente em que
     * vive o ser marinho. É utilizada para que o ser possa
     * interagir com o ambiente (ex: caçar, devolver energia
     * através de metabolismo, etc)
     */
    private Aquario ambiente;

    /**
     * Construtor de um ser marinho
     * @param energia a energia inata do ser
     * @param icone o ícone que representa o ser
     * @param cor a cor do ser
     */
    
    public SerMarinho(int energia, int velocidade, String icone, Color cor) {
        this.energia = energia;
        this.icone = icone;
        this.cor = cor;
        this.vitalidade = 255;
        this.energiaAlimento = 0;
        this.velocidade = velocidade;
    }

    /**
     * Cada ser marinho é uma Thread!
     * Sua vida se resume a:
     * - Movimentar-se
     * - Fazer metabolismo
     * - Caçar
     * - Envelhecer
     * Veja como isso é implementado no método abaixo.
     */
   // @Override
    public void run() {
        while (vitalidade > 0) {
            for (int i = 0; i < velocidade; i++)
                movimento();
            metabolizar();
            cacar();
            envelhecer();
        }
        getAmbiente().removerSerMarinho(this);
    }

    /**
     * Cada ser se move de maneira diferente. Por isso
     * um método abstrato.
     * Ao sobrescrever este método, atualize as posições x e y através
     * dos métodos setX e setY!
     */
    public abstract void movimento();

    /**
     * O metabolismo consiste em processar uma determinada
     * quantidade de energia de alimento, devolvendo-a
     * ao ambiente
     */
    protected void metabolizar() {
        int quantidadeMetabolizada = Utilitarios.numeroAleatorio(1, velocidadeMetabolismo);
        if (quantidadeMetabolizada > energiaAlimento) {
            quantidadeMetabolizada = energiaAlimento;
        }
        if (energiaAlimento > 0) {
            energiaAlimento -= quantidadeMetabolizada;
        }
        getAmbiente().devolverEnergia(quantidadeMetabolizada);
    }

    /**
     * Um ser marinho precisa cacar para sobreviver.
     * Ele só caça se estiver com fome.
     * A caça consiste em varrer os seres próximos a ele, e
     * se encontrar alguma presa, devorar um deles (qualquer um,
     * desde que esteja próximo)
     */
    protected void cacar() {
        if (comFome()) {
            List<SerMarinho> seresProximos = getAmbiente().getSeresMarinhosProximos(this);
            for (SerMarinho sm : seresProximos) {
                synchronized(sm)
                {
                    if (sm.getEnergia() > 0 && isPresa(sm)) {
                        devorar(sm);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Um ser marinho só fica com fome se sua energia
     * de alimentação estiver menor ou igual a zero
     * @return energiaAlimento <= 0
     */
    public boolean comFome() {
        return energiaAlimento <= 0;
    }

    /**
     * Cada ser marinho tem uma forma diferente de identificar
     * uma presa. Por isso o método é abstrato.
     * Dica: ao implementar esse método, utilize o operador "instanceof"
     * para testar o tipo de sm.
     * @param sm o ser marinho "caçado"
     * @return true se sm é uma presa deste ser
     */
    public abstract boolean isPresa(SerMarinho sm);

    /**
     * Ao devorar outro, um ser incorpora a energia deste
     * à sua energia de alimentação. A mesma não é devolvida
     * ao ambiente.
     * Neste método, a velocidade do metabolismo também é
     * calculada
     * @param alimento o ser marinho a ser devorado
     */
    private void devorar(SerMarinho alimento) {
           velocidadeMetabolismo = Math.abs(energia - alimento.getEnergia()) / 10;
           energiaAlimento += alimento.getEnergia();
        /*
            O alimento é morto antes de ser digerido, assim evitamos que o mesmo seja
         * comido por outro animal
         */
        alimento.matar();
        digerir();
    }

    /**
     * A digestão durante alimentação consome um tempo de
     * vida do ser devorador.
     */
    private void digerir() {
        try {
            int tempoDigestao = Utilitarios.numeroAleatorio(50, 100);
            Thread.sleep(tempoDigestao / Simulacao.VELOCIDADE);
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Todo ser envelhece até morrer. Ao morrer, sua energia
     * retorna ao ambiente em forma de nutrientes.
     * Se um ser marinho não estiver alimentado, ele
     * irá padecer mais rapidamente.
     */
    protected void envelhecer() {
        if (comFome()) {
                vitalidade -= 5000 / (1 + energia);
        } else {
            vitalidade -= 500 / (energia + 1);
        }
        try {
            Thread.sleep(1000 / Simulacao.VELOCIDADE);
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Esse método é utilizado para encerrar a Thread.
     * Corresponde a "matar" um ser marinho
     */
    public void matar() {
        vitalidade = 0;
        energia = 0;
        energiaAlimento = 0;
    }

    /**
     * Determina se um ser marinho está próximo (em termos
     * de distância de caça) de outro
     * @param outro o ser marinho
     * @return true se outro está em uma distância de caça deste ou menor
     */
    public boolean proximo(SerMarinho outro) {
        Point p1 = new Point(getX(), getY());
        Point p2 = new Point(outro.getX(), outro.getY());
        double distancia = p1.distance(p2);
        if (distancia <= getDistanciaParaCacar()) {
            return true;
        }
        return false;
    }

    /**
     * Cada ser caça de forma diferente.
     * Ao implementar esse método, retorne um inteiro que é a distância,
     * em pixels, na qual o ser consegue abrir a boca e devorar a presa.
     * @return a distância de caça do ser
     */
    public abstract int getDistanciaParaCacar();

    /**
     * Link entre um ser e seu ambiente
     * @return o ambiente do ser marinho
     */
    protected Aquario getAmbiente() {
        return ambiente;
    }

    /**
     * Estabelece um elo entre o ser marinho e seu
     * ambiente
     * @param ambiente o ambiente do ser marinho
     */
    public void setAmbiente(Aquario ambiente) {
        this.ambiente = ambiente;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x < 10) {
            x = 10;
        }
        if (x > ambiente.getLargura() - 50) {
            x = ambiente.getLargura() - 50;
        }
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y < 10) {
            y = 10;
        }
        if (y > ambiente.getAltura() - 50) {
            y = ambiente.getAltura() - 50;
        }
        this.y = y;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getIcone() {
        return icone;
    }

    /**
     * Note como este método retorna a cor, mas também
     * acrescenta um tom de brilho conforme a vitalidade.
     * Seres jovens são mais "brilhantes", enquanto
     * seres velhos aparecem de forma "apagada"
     * @return a cor a ser usada na renderização do ser
     */
    public Color getCor() {
        double brilho = (double) vitalidade / 255;
        if (brilho < 0) {
            brilho = 0;
        }   
        Color corVitalidade = new Color((int) (cor.getRed() * brilho), (int) (cor.getGreen() * brilho), (int) (cor.getBlue() * brilho));
        return corVitalidade;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    /**
     * A energia de um ser consiste de sua energia inata
     * mais a energia do alimento que o mesmo está metabolizando
     * @return a energia de um ser marinho
     */
    public int getEnergia() {
        return energia + energiaAlimento;
    }

    public double getVitalidade() {
        return vitalidade;
    }
}
