package forme;

import java.util.Objects;

public class Rettangolo extends Forma {

    private double base;
    private double altezza;

    public Rettangolo(double base, double altezza){
        if(altezza > 0 || base > 0) {
            this.base = base;
            this.altezza = altezza;
        } else {
            throw new IllegalArgumentException("Base e altezza devono essere maggiori di 0");
        }
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        if(base <= 0){
            throw new IllegalArgumentException("La base deve essere maggiore a 0");
        }
        this.base = base;
    }

    public double getAltezza() {
        return altezza;
    }

    public void setAltezza(double altezza) {
        if(altezza <= 0){
            throw new IllegalArgumentException("L'altezza deve essere maggiore a 0");
        }
        this.altezza = altezza;
    }

    @Override
    public double area() {
        return base * altezza;
    }

    @Override
    public double perimetro() {
        return 2 * (base + altezza);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rettangolo that = (Rettangolo) o;
        return Double.compare(base, that.base) == 0 && Double.compare(altezza, that.altezza) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, altezza);
    }
}
