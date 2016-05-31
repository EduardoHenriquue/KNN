import java.util.Comparator;

/**
 * Created by Eduardo on 30/05/2016.
 */
public class Vizinho implements Comparable<Vizinho>{
    private int rotulo;
    private double distancia;

    public Vizinho(int rotulo, double distancia) {
        this.rotulo = rotulo;
        this.distancia = distancia;
    }

    public int getRotulo() {
        return rotulo;
    }

    public double getDistancia() {
        return distancia;
    }

    @Override
    public int compareTo(Vizinho vizinho) {
        if(this.distancia > vizinho.getDistancia()) {
            return 1;
        } else if(this.distancia < vizinho.getDistancia()) {
            return -1;
        }
        return 0;
    }
}
