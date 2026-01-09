package ch.samt.scadenze.model;

import java.util.Date;

public abstract class Element {

    protected String codice;
    protected Date dataCreazione;

    public Element(String codice, Date dataCreazione) {
        this.codice = codice;
        this.dataCreazione = dataCreazione;
    }

    public String getCodice() {
        return codice;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    // Metodo astratto
    public abstract Date calcolaDataScadenza();

    // Verifica se è scaduto rispetto a una data
    public boolean isScaduto(Date dataRiferimento) {
        return calcolaDataScadenza().before(dataRiferimento);
    }

    @Override
    public String toString() {
        return "Codice: " + codice +
                ", Creazione: " + dataCreazione +
                ", Scadenza: " + calcolaDataScadenza();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Element)) return false;

        Element other = (Element) obj;
        return this.codice.equals(other.codice);
    }
}

