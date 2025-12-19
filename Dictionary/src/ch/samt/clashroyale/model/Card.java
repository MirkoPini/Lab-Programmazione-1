package ch.samt.clashroyale.model;

import java.util.Objects;

public class Card {
    private String name;
    private int elixirCost;
    private int level;

    public Card(String name, int elixirCost, int level){
        this.name = name;
        this.elixirCost = elixirCost;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getElixirCost() {
        return elixirCost;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if(level > 0 && level <= 16) {
            this.level = level;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return elixirCost == card.elixirCost && level == card.level && Objects.equals(name, card.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, elixirCost, level);
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "ElixirCost: " + elixirCost + "\n" +
                "Level: " + level + "\n";
    }
}
