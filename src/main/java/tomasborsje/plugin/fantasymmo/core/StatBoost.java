package tomasborsje.plugin.fantasymmo.core;

/**
 * Wrapper class for stat benefits when an item is held or equipped.
 */
public class StatBoost {
    public static final StatBoost NONE = new StatBoost();
    public int strength;
    public int intelligence;
    public int health;
    public int defense;
    private StatBoost() {
        this.strength = 0;
        this.intelligence = 0;
        this.health = 0;
        this.defense = 0;
    }
    public StatBoost(int strength, int intelligence, int health, int defense) {
        this.strength = strength;
        this.intelligence = intelligence;
        this.health = health;
        this.defense = defense;
    }
}
