package ch.samt.scadenze.model;

import java.util.Calendar;
import java.util.Date;

public class Abbonamento extends Element {

    private int durata;
    private boolean inMesi; // true = mesi, false = anni

    public Abbonamento(String codice, Date dataCreazione, int durata, boolean inMesi) {
        super(codice, dataCreazione);
        this.durata = durata;
        this.inMesi = inMesi;
    }

    @Override
    public Date calcolaDataScadenza() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataCreazione);

        if (inMesi)
            cal.add(Calendar.MONTH, durata);
        else
            cal.add(Calendar.YEAR, durata);

        return cal.getTime();
    }

    @Override
    public String toString() {
        String tipo = inMesi ? "mesi" : "anni";
        return "ABBONAMENTO -> " + super.toString() +
                ", Durata: " + durata + " " + tipo;
    }
}
