import java.util.ArrayList;

public class House {
    private ArrayList<Room> stanze;

    public House(){
        this.stanze = new ArrayList<>();
    }

    public void aggiungiStanza(String nome, double superficieMq){
        Room nuova = new Room(nome, superficieMq);
        stanze.add(nuova);
    }

    public void visualizzaStanze(){
        if(stanze.isEmpty()){
            System.out.println("La casa è vuota");
        }else{
            for (int i = 0; i < stanze.size(); i++) {
                System.out.println(stanze.get(i).toString());
            }
        }
    }

    public double getSuperficieTotale(){
        double totale = 0;
        if(stanze.isEmpty()){
            return totale;
        }else{
            for (int i = 0; i < stanze.size(); i++) {
                totale += stanze.get(i).getSuperficieMq();
            }
            return totale;
        }
    }

    public Room trovaStanza(String nome) {
        for (Room room : stanze) {
            if (room.getNome().equals(nome)) {
                return room;
            }
        }
        return null;
    }

    public void rimuoviStanza(String nome){
        Room daRimuovere = trovaStanza(nome);
        if (daRimuovere != null) {
            stanze.remove(daRimuovere);
        }
    }

}
