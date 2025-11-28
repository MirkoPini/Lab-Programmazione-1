public class MainHouse {
    public static void main(String[] args) {
        House casa = new House();

        casa.aggiungiStanza("cucina", 15);
        casa.aggiungiStanza("camera", 20);
        casa.aggiungiStanza("garage", 25);

        casa.visualizzaStanze();
        System.out.println(casa.getSuperficieTotale() + " mq");

        casa.rimuoviStanza("cucina");

        casa.visualizzaStanze();
    }
}
