package ch.samt.scadenze.model;

public class Libro extends Element{
    private int durataGiorni;

    public Libro(String idCode, Date creationDate, int durataGiorni){
        super(idCode, creationDate);
        this.durataGiorni = durataGiorni;
    }

    public int getDurataGiorni(){
        return this.durataGiorni;
    }

    public Date dataScadenza(){
        Date datascadenza = creationDate + durataGiorni;
        return datascadenza;
    }

    @Override
    public String toString(){
        return super(toString) + "Prestito:" + "\n" + "Durata giorni: " + getDurataGiorni() + "\n";
    }
}
