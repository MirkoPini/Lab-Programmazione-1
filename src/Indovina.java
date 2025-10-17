import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Indovina {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random rand = new Random();
        int numero_casuale = rand.nextInt(1, 101);
        System.out.println("Indovina il numero tra 1 e 100");
        int tentativo;
        int counter = 0;
        while (true) {
            try {
                System.out.print("Il tuo tentativo: ");
                tentativo = input.nextInt();
                counter ++;
                if (tentativo > numero_casuale) {
                    System.out.println("Troppo alto!");
                }else if (tentativo < numero_casuale) {
                    System.out.println("Troppo basso!");
                }else{
                    break;
                }
            }catch (NumberFormatException e){
                System.out.println("Devi inserire dei numeri interi!");
            }
        }
        System.out.println("Bravo! Hai indovinato in " + counter + " tentativi.");
        input.close();
    }
}
