package forme;

public class Cerchio extends Forma{
    private double raggio;

    public Cerchio(double raggio){
        if(raggio <= 0){
            throw new IllegalArgumentException("Il raggio deve essere maggiore di 0");
        }

        this.raggio = raggio;
    }

    public double getRaggio() {
        return raggio;
    }

    public void setRaggio(double raggio) {
        if(raggio <= 0){
            throw new IllegalArgumentException("Il raggio deve essere maggiore di 0");
        }
        this.raggio = raggio;
    }

    @Override
    public double area() {
        return Math.PI * raggio * raggio;
    }

    @Override
    public double perimetro() {
        return 2 * Math.PI * raggio;
    }
}
