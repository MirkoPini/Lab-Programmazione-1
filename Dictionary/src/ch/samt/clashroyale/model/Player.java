package ch.samt.clashroyale.model;

public class Player {
    private String nickname;
    private Deck deck;

    public Player(String nikname, Deck deck){
        this.nickname = nikname;
        this.deck = deck;
    }

    public String getNickname() {
        return nickname;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        return "Player{nickname='" + nickname + "', deck=" + deck + "}";
    }
}
