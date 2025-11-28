import java.util.InputMismatchException;
import java.util.Scanner;

public class MainRegistro {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numStudenti;
        int numMaterie;
        try {
            System.out.println("Numero di studenti: ");
            numStudenti = in.nextInt();
            System.out.println("Numero di materie: ");
            numMaterie = in.nextInt();
            Registro registro = new Registro(numStudenti, numMaterie);
            registro.inserisciVoti();
            registro.stampaVoti();
            registro.stampaMedie();
        }catch(InputMismatchException e){
            System.out.println("Devi inserire un numero intero!");
        }
        in.close();
    }
}
