package ch.samt.clashroyale.model;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;

    public Deck(){
        this.cards = new ArrayList<Card>();
    }

    public void addCard(Card card){
        if(cards.size() < 8 && !cards.contains(card)){
            cards.add(card);
            System.out.println("Carta aggiunta!");
        }else {
            System.out.println("Deck pieno!");
        }
    }

    public void removeCard(Card card){
        if(cards.contains(card)){
            cards.remove(card);
        }else{
            System.out.println("Carta non presente nel Deck!");
        }
    }

    public void getCards(){
        if(!cards.isEmpty()){
            for (int i = 0; i < cards.size(); i++) {
                System.out.println(cards.get(i));
            }
        }else{
            System.out.println("Deck vuoto!");
        }
    }

    @Override
    public String toString() {
        return "Deck{cards=" + cards + "}";
    }
}
