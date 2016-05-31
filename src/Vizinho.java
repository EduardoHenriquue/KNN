/**
 * Created by Eduardo on 30/05/2016.
 */
public class Vizinho {
    private Flor flor;
    private double distancia;

    public Vizinho(Flor flor, double distancia) {
        this.flor = flor;
        this.distancia = distancia;
    }

    public Flor getFlor() {
        return flor;
    }

    public double getDistancia() {
        return distancia;
    }
}
