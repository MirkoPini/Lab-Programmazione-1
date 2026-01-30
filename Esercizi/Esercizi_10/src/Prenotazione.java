import java.time.LocalDate;

public abstract class Prenotazione implements Documentabile {
    private String codiceVolo;
    private LocalDate dataPartenza;
    private double prezzoBase;

    public Prenotazione(String codiceVolo, LocalDate dataPartenza, double prezzoBase) {
        if (codiceVolo.matches("[A-Z]{2}\\d{3}")) {
            this.codiceVolo = codiceVolo;
        }else{
            throw new IllegalArgumentException("Codice errato!");
        }
        if(dataPartenza.isAfter(LocalDate.now())) {
            this.dataPartenza = dataPartenza;
        }else{
            throw new IllegalArgumentException("Data errata!");
        }
        if(prezzoBase > 0) {
            this.prezzoBase = prezzoBase;
        }else{
            throw new IllegalArgumentException("Prezzo base sotto zero!");
        }
    }

    public String getCodiceVolo() {
        return codiceVolo;
    }

    public void setCodiceVolo(String codiceVolo) {
        if (!codiceVolo.matches("[A-Z]{2}\\d{3}")) {
            this.codiceVolo = codiceVolo;
        }else{
            throw new IllegalArgumentException("Codice errato!");
        }
    }

    public LocalDate getDataPartenza() {
        return dataPartenza;
    }

    public void setDataPartenza(LocalDate dataPartenza) {
        if(dataPartenza.isAfter(LocalDate.now())) {
            this.dataPartenza = dataPartenza;
        }else{
            throw new IllegalArgumentException("Data errata!");
        }
    }

    public double getPrezzoBase() {
        return prezzoBase;
    }

    public void setPrezzoBase(double prezzoBase) {
        if(prezzoBase > 0) {
            this.prezzoBase = prezzoBase;
        }else{
            throw new IllegalArgumentException("Prezzo base sotto zero!");
        }
    }

    public abstract double calcolaCostoTotale();
}
