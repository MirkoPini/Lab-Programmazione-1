package ch.samt.biblioteca.data;

import ch.samt.biblioteca.model.ItemBiblioteca;
import ch.samt.biblioteca.model.Libro;

import java.util.*;

public class Biblioteca {
    private ArrayList<ItemBiblioteca> catalogo;
    private Set<String> codiciUsati;
    private Map<String, ArrayList<ItemBiblioteca>> elementiPerAutore;
    private Queue<ItemBiblioteca> prenotazioniFIFO;
    private Stack<ItemBiblioteca> consegneUrgentiLIFO;

    public Biblioteca() {
        catalogo = new ArrayList<>();
        codiciUsati = new HashSet<>();
        elementiPerAutore = new HashMap<>();
        prenotazioniFIFO = new LinkedList<>();
        consegneUrgentiLIFO = new Stack<>();
    }

    public boolean aggiungiItem(ItemBiblioteca item) {
        if (item == null){
            return false;
        }
        String codice = item.getCodice();
        if (codiciUsati.contains(codice)) {
            return false;
        }
        catalogo.add(item);
        codiciUsati.add(codice);
        if (item instanceof Libro) {
            Libro libro = (Libro) item;
            String autore = libro.getAutore();
            elementiPerAutore.putIfAbsent(autore, new ArrayList<>());
            elementiPerAutore.get(autore).add(item);
        }
        return true;
    }

    public ArrayList<ItemBiblioteca> getCatalogo() {
        return catalogo;
    }

    public ArrayList<ItemBiblioteca> getElementiDiAutore(String autore) {
        if (autore == null) {
            return new ArrayList<>();
        }
        return elementiPerAutore.get(autore);
    }

    public void aggiungiPrenotazioneFIFO(ItemBiblioteca item){
        if(item != null){
            prenotazioniFIFO.offer(item);
        }
    }

    public ItemBiblioteca prossimaPrenotazioneFIFO(){
        return prenotazioniFIFO.poll();
    }

    public void aggiungiConsegnaUrgenteLIFO(ItemBiblioteca item){
        if(item != null){
            consegneUrgentiLIFO.push(item);
        }
    }

    public ItemBiblioteca prossimaConsegnaLIFO(){
        if(consegneUrgentiLIFO == null){
            return null;
        }
        return consegneUrgentiLIFO.pop();
    }
}
