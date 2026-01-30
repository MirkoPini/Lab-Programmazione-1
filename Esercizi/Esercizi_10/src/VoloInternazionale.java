import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class VoloInternazionale extends Prenotazione{

    private String destinazione;
    private String nomePasseggero;
    private boolean bagaglioStiva;

    public VoloInternazionale(String codiceVolo, LocalDate dataPartenza, double prezzoBase, String destinazione, String nomePasseggero, boolean bagaglioStiva) {
        super(codiceVolo, dataPartenza, prezzoBase);
        this.destinazione = destinazione;
        this.nomePasseggero = nomePasseggero;
        this.bagaglioStiva = bagaglioStiva;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public String getNomePasseggero() {
        return nomePasseggero;
    }

    public void setNomePasseggero(String nomePasseggero) {
        this.nomePasseggero = nomePasseggero;
    }

    public boolean isBagaglioStiva() {
        return bagaglioStiva;
    }

    public void setBagaglioStiva(boolean bagaglioStiva) {
        this.bagaglioStiva = bagaglioStiva;
    }

    @Override
    public double calcolaCostoTotale() {
        if(isBagaglioStiva()) {
            return getPrezzoBase() + 25 + 40;
        }else{
            return getPrezzoBase() + 25;
        }
    }

    @Override
    public StringBuilder generaTicket() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.ITALIAN);
        StringBuilder ticket = new StringBuilder();
        ticket.append("**************************************************\n" +
                      "* BOARDING PASS                                  *\n" +
                      "**************************************************\n");
        ticket.append("PASSEGGERO:\t\t" + getNomePasseggero().toUpperCase() + "\n");
        ticket.append("VOLO:\t\t\t" + getCodiceVolo() + "\n");
        ticket.append("DESTINAZIONE:\t" + getDestinazione().toUpperCase() + "\n");
        ticket.append("DATA:\t\t\t" + getDataPartenza().format(formatter) + "\n");
        if(getDestinazione().equals("Tokyo") || getDestinazione().equals("New York") || getDestinazione().equals("Dubai"))
        ticket.append("--------------------------------------------------\n" +
                      " [INFO: Necessario Passaporto]\n" +
                      "--------------------------------------------------\n");
        if(isBagaglioStiva()){
            ticket.append("BAGAGLIO IN STIVA:  SI\n");
        }else {
            ticket.append("BAGAGLIO IN STIVA:  NO\n");
        }
        ticket.append("PREZZO FINALE:\t\t€ " + String.format("%.2f", calcolaCostoTotale()) + "\n");
        ticket.append("**************************************************\n");
        return ticket;
    }
}
