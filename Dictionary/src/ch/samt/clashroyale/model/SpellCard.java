package ch.samt.clashroyale.model;

public class SpellCard extends Card{
    private int areaDamage;
    private double radius;


    public SpellCard(String name, int elixirCost, int level, int areaDamage, double radius) {
        super(name, elixirCost, level);
        this.areaDamage = areaDamage;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Area Damage: " + areaDamage + "\n" +
                "Radius: " + radius + "\n";
    }
}
