import java.util.Arrays;
import java.util.Random;

public class MatrixSorter {
    public int[][] matrix;
    Random rnd = new Random();

    public MatrixSorter() {
        this.matrix = new int[3][3];
    }

    public void StampaMatrice() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

    public int[][] PopolaMatrice() {
        int[] num = new int[9];
        for (int i = 1; i < num.length+1; i++) {
            num[i] = i;
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                num = rnd.nextInt(1, 10);
                matrix[i][j] = num;
            }
        }
        return matrix;
    }

    public int[][] SortRow(int direzione) {
        int[] sorting = new int[9];
        if (direzione == 0) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    sorting[i] = matrix[i][j];
                }
            }
            Arrays.sort(sorting);
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[i][j] = sorting[i];                }
            }
        } else if (direzione == 1) {

        } else {
            throw new IllegalArgumentException("Devi inserire 0 o 1");
        }
        return matrix;
    }

}
