/**
 * Created by Eduardo on 05/05/2016.
 */
public class Flor {

    private double petallength;
    private double sepallength;
    private double petalwidth;
    private double sepalwidth;
    private int rotulo;

    public Flor(double sepallength, double sepalwidth, double petallength, double petalwidth, int rotulo) {
        this.petallength = petallength;
        this.sepallength = sepallength;
        this.petalwidth = petalwidth;
        this.sepalwidth = sepalwidth;
        this.rotulo = rotulo;
    }

    public double getPetallength() {
        return petallength;
    }

    public double getSepallength() {
        return sepallength;
    }

    public double getPetalwidth() {
        return petalwidth;
    }

    public double getSepalwidth() {
        return sepalwidth;
    }

    public int getRotulo() {
        return this.rotulo;
    }
}
