package ch.samt.clashroyale.model;

public class TroopCard extends Card{
    private int hitPoints;
    private int damage;


    public TroopCard(String name, int elixirCost, int level, int hitPoints, int damage) {
        super(name, elixirCost, level);
        this.hitPoints = hitPoints;
        this.damage = damage;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Hitpoints: " + hitPoints + "\n" +
                "Damage: " + damage + "\n";
    }
}
