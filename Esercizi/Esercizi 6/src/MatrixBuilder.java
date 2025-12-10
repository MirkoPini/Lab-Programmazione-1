import java.util.Random;

public class MatrixBuilder {
    public int[][] matrice;
    Random rnd = new Random();
    public int num;

    public MatrixBuilder(int[][] matrice, int colonne, int righe) {
        matrice = new int[colonne][righe];
        this.matrice = matrice;
    }

    public MatrixBuilder() {
        this.matrice = new int[5][5];
    }

    public void Getter() {
        System.out.println("Righe: " + matrice.length);
        System.out.println("Colonne: " + matrice[0].length);
    }

    public void StampaMatrice() {
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                System.out.print(matrice[i][j]);
            }
            System.out.println();
        }
    }

    public int[][] PopolaMatrice() {
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                num = rnd.nextInt(0, 2);
                matrice[i][j] = num;
            }
        }
        return matrice;
    }

    public void GetCella(int colonna, int riga) {
        System.out.println(matrice[colonna][riga]);
    }

    public int[][] SetCella(int colonna, int riga, int valore) {
        if (valore > 1 || valore < 0) {
            throw new IllegalArgumentException("Valore non accettabile");
        }
        matrice[colonna][riga] = valore;
        return matrice;
    }
}
