package ch.samt.scadenze.model;

import java.util.Date;

public class Abbonamento extends Element {
    private Date Durata;

    public Abbonamento(String idCode, Date creationDate, Date Durata){
        super(idCode, creationDate);
        this.Durata = Durata;
    }
}
