public class VerificaCodiceProdotto {
    public String codiceProdotto;
    String codice = "PROD-";
    boolean passato = true;
    public VerificaCodiceProdotto(String _codiceProdotto) {
        codiceProdotto = _codiceProdotto;
    }
    public void Verifica(){
        try{
            String first_code = codiceProdotto.substring(0, 5);
            if (!codice.equals(first_code)){
                throw new IllegalArgumentException();
            }
            String second_code = codiceProdotto.substring(5, 9);
            int second_part = Integer.parseInt(second_code);
        }catch(NumberFormatException err){
            System.out.println("Codice non valido:" + codiceProdotto);
            System.out.println("Motivo: Il codice deve contenere almeno 4 cifre numeriche dopo 'PROD-'.");
            passato = false;
        }catch (StringIndexOutOfBoundsException e){
            System.out.println("Codice non valido:" + codiceProdotto);
            System.out.println("Motivo: devi inserire almeno 9 caratteri");
            passato = false;
        }catch (IllegalArgumentException er){
            System.out.println("Codice non valido:" + codiceProdotto);
            System.out.println("Motivo: Il codice deve iniziare con 'PROD-'.");
            passato = false;
        }
        if(passato == true) {
            System.out.println("Codice valido:" + codiceProdotto);
        }
    }
}
