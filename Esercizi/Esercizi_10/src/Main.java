import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            VoloInternazionale volo1 = new VoloInternazionale("AZ123", LocalDate.of(2026, 2, 26), 156.43, "Tokyo", "Maurizio", true);
            System.out.println(volo1.generaTicket().toString());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}