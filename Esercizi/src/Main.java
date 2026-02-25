public class Main {
    public static void main(String[] args) {
        System.out.println(Operazione.somma(3, 6));
        Math.sqrt(12);

        Prodotto prod1 = new Prodotto("Banana", 5);
        Prodotto prod2 = new Prodotto("Mela", 4);

        System.out.println(prod1.codiceProduttore);
        System.out.println(prod2.codiceProduttore);

        Prodotto.codiceProduttore = 2026;

        System.out.println(prod1.codiceProduttore);
        System.out.println(prod2.codiceProduttore);
    }
}