package ch.samt.biblioteca.model;

public class Libro extends ItemBiblioteca{
    private String autore;
    private int numeroPagine;

    public Libro(String codice,String titolo, int annoPubblicazione, String scaffale, String autore, int numeroPagine){
        super(codice, titolo, annoPubblicazione, scaffale);
        this.autore = autore;
        this.numeroPagine = numeroPagine;
    }

    @Override
    public String toString() {
        return String.format("Libro: %s, autore: %s, numero pagine: %d pagine", super.toString(), autore, numeroPagine);
    }

    public String getAutore() {
        return autore;
    }
}
