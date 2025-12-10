public class Room {
    private String nome;
    private double superficieMq;

    public Room(String nome, double superficieMq){
        this.nome = nome;
        this.superficieMq = superficieMq;
    }

    public String getNome() {
        return nome;
    }

    public double getSuperficieMq() {
        return superficieMq;
    }

    @Override
    public String toString() {
        return nome + " (" + superficieMq + " mq)";
    }
}
