package ch.samt.scadenze.model;

import java.util.Calendar;
import java.util.Date;

public class Libro extends Element {

    private int durataGiorni;

    public Libro(String codice, Date dataCreazione, int durataGiorni) {
        super(codice, dataCreazione);
        this.durataGiorni = durataGiorni;
    }

    @Override
    public Date calcolaDataScadenza() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataCreazione);
        cal.add(Calendar.DAY_OF_MONTH, durataGiorni);
        return cal.getTime();
    }

    @Override
    public String toString() {
        return "PRESTITO LIBRO -> " + super.toString() +
                ", Durata: " + durataGiorni + " giorni";
    }
}