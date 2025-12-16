package ch.samt.clashroyale.app;

import ch.samt.clashroyale.battle.BattleEngine;
import ch.samt.clashroyale.model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(BattleEngine.rules());
        System.out.println();

        TroopCard knight = new TroopCard("Knight", 3, 11, 1500, 160);
        TroopCard musketeer = new TroopCard("Musketeer", 4, 11, 900, 220);
        SpellCard fireball = new SpellCard("Fireball", 4, 11, 325, 2.5);

        Deck deck = new Deck();
        deck.addCard(knight);
        deck.addCard(musketeer);
        deck.addCard(fireball);

        TroopCard knightClone = new TroopCard("Knight", 3, 1, 1, 1);
        deck.addCard(knightClone);

        Player player = new Player("ProPlayer320", deck);

        System.out.println("=== PLAYER ===");
        System.out.println(player.toString());
        System.out.println();

    }
}
