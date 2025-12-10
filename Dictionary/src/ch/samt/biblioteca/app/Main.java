package ch.samt.biblioteca.app;


import ch.samt.biblioteca.data.Biblioteca;
import ch.samt.biblioteca.model.Dvd;
import ch.samt.biblioteca.model.ItemBiblioteca;
import ch.samt.biblioteca.model.Libro;


import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Biblioteca bib1 = new Biblioteca();

        Libro l1 = new Libro("L001", "Il giovane", 2010, "A1", "Mario Rossi", 320);
        Libro l2 = new Libro("L002", "La strada", 2015, "A2", "Lucia Bianchi", 210);
        Dvd d1 = new Dvd("D001", "Avventura", 2018, "D1", "Giovanni Verdi", 120);

        System.out.println("l1 aggiunto? " + bib1.aggiungiItem(l1));
        System.out.println("l2 aggiunto? " + bib1.aggiungiItem(l2));
        System.out.println("d1 aggiunto? " + bib1.aggiungiItem(d1));

        Libro l1dup = new Libro("L001", "Il giovane - copia", 2010, "A1", "Mario Rossi", 320);
        System.out.println("l1dup aggiunto? " + bib1.aggiungiItem(l1dup));

        System.out.println("Catalogo completo:");
        ArrayList<ItemBiblioteca> catalogo = bib1.getCatalogo();
        for (ItemBiblioteca item : catalogo) {
            System.out.println(item);
        }

        System.out.println("Libri di Mario Rossi:");
        ArrayList<ItemBiblioteca> diMario = bib1.getElementiDiAutore("Mario Rossi");
        for (ItemBiblioteca item : diMario){
            System.out.println(item);
        }

        // Test FIFO
        System.out.println("--- Test FIFO (Prenotazioni) ---");
        bib1.aggiungiPrenotazioneFIFO(l1);
        bib1.aggiungiPrenotazioneFIFO(l2);
        bib1.aggiungiPrenotazioneFIFO(d1);
        ItemBiblioteca p = bib1.prossimaPrenotazioneFIFO();
        System.out.println("Estratta (FIFO): " + p);

        // Test LIFO
        System.out.println("--- Test LIFO (Consegne urgenti) ---");
        bib1.aggiungiConsegnaUrgenteLIFO(l1);
        bib1.aggiungiConsegnaUrgenteLIFO(l2);
        bib1.aggiungiConsegnaUrgenteLIFO(d1);
        ItemBiblioteca u = bib1.prossimaConsegnaLIFO();
        System.out.println("Estratta (LIFO): " + u);
    }
}
