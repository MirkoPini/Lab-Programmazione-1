import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        MatrixBuilder matrix = new MatrixBuilder();
        matrix.PopolaMatrice();
        matrix.StampaMatrice();

        ArrayList<String> test = new ArrayList<String>();
        test.add("albero");
        test.add("banana");
        test.add("cuscino");
        test.add("denti");
        test.add("elevatore");

        for (int i = 0; i < test.size(); i++) {
            System.out.println("Elemento " + (i + 1) + " : " + test.get(i));
        }

        System.out.println("Primo: " + test.get(1));

        System.out.println("Ultimo: " + test.getLast());
    }
}