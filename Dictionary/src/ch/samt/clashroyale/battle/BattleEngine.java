package ch.samt.clashroyale.battle;

import ch.samt.clashroyale.model.Card;
import ch.samt.clashroyale.model.SpellCard;
import ch.samt.clashroyale.model.TroopCard;


public class BattleEngine {
    public void playCard(Card card){
        if(card instanceof TroopCard){
            TroopCard troop = (TroopCard) card;
            System.out.println(" -> Truppa: HP=" + troop.getHitPoints() + ", DMG=" + troop.getDamage());
        }else if(card instanceof SpellCard){
            SpellCard troop = (SpellCard) card;
            System.out.println(" -> Truppa: DMG=" + troop.getAreaDamage() + ", RAD=" + troop.getRadius());
        }
    }

    public static String rules() {
        return "Regole: ogni carta costa elisir; truppe hanno HP/DMG; incantesimi hanno area damage e raggio.";
    }
}
