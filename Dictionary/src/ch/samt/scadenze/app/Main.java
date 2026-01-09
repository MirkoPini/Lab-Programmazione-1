package ch.samt.scadenze.app;
import java.util.*;
import ch.samt.scadenze.model.*;

public class Main {

    public static void main(String[] args) {

        List<Element> lista = new ArrayList<>();

        Date oggi = new Date();

        lista.add(new Libro("A1", oggi, 10));
        lista.add(new Abbonamento("B1", oggi, 6, true));
        lista.add(new Libro("C1", oggi, -5)); // già scaduto
        lista.add(new Abbonamento("D1", oggi, -1, false)); // già scaduto

        System.out.println("=== Tutti gli elementi ===");
        for (Element e : lista) {
            System.out.println(e); // polimorfismo
        }

        System.out.println("\n=== Elementi scaduti ===");
        for (Element e : lista) {
            if (e.isScaduto(oggi)) {
                System.out.println(e);
            }
        }

        // Parte 6 – confronto equals
        Element p = new Libro("X1", oggi, 5);
        Element a = new Abbonamento("X1", oggi, 1, true);

        System.out.println("\nConfronto equals:");
        System.out.println(p.equals(a));
    }
}
