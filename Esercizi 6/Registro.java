import java.util.Scanner;

public class Registro {
    private String[] studenti;
    private double[][] voti;
    private int numStudenti;
    private int numMaterie;
    private int counter = 1;
    private int counter1 = 1;

    public Registro(int numStudenti, int numMaterie) {
        this.numStudenti = numStudenti;
        this.numMaterie = numMaterie;
        studenti = new String[numStudenti];
        voti = new double[numStudenti][numMaterie];
    }

    public void inserisciVoti() {
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < numStudenti; i++) {
            System.out.println("Nome dello studente " + counter + ": ");
            studenti[i] = input.nextLine();
            counter++;
            counter1 = 1;
            for (int j = 0; j < numMaterie; j++) {
                System.out.println("Inserisci voto " + counter1 + ": ");
                voti[i][j] = input.nextDouble();
                counter1++;
            }
            input.nextLine();
        }
    }

    public void stampaVoti() {
        System.out.println("=== Tabella Voti ===");
        for (int i = 0; i < numStudenti; i++) {
            System.out.print(studenti[i] + ": ");
            for (int j = 0; j < numMaterie; j++) {
                System.out.print(voti[i][j] + " ");
            }
            System.out.println();
        }
    }

    public double mediaStudente(int index) {
        double somma = 0;
        for (int j = 0; j < numMaterie; j++) {
            somma += voti[index][j];
        }
        return somma / numMaterie;
    }

    public void stampaMedie(){
        System.out.println("=== Tabella Medie ===");
        for (int i = 0; i < numStudenti; i++) {
            System.out.println(studenti[i] + ": " + mediaStudente(i));
        }
    }
}
