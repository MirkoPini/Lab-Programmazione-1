package ch.samt.main;

import ch.samt.dictionary.Dictionary;
import ch.samt.dictionary.Entry;

public class App {
    public static void main(String[] args) {

        Dictionary diz = new Dictionary();

        diz.aggiungi(new Entry("gatto", "cat"));
        diz.aggiungi(new Entry("cane", "dog"));
        diz.aggiungi(new Entry("tavolo", "table"));

        Entry trovata = diz.cerca("gatto");
        if (trovata != null) {
            System.out.println("Traduzione trovata: " + trovata);
        } else {
            System.out.println("Parola non trovata.");
        }

        System.out.println("\n--- Dizionario completo ---");
        diz.stampaTutto();
    }
}
