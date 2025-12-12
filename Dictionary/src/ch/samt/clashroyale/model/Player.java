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

}
