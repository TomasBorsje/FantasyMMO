package tomasborsje.plugin.fantasymmo.core;

/**
 * Wrapper class for stat benefits when an item is held or equipped.
 */
public class StatBoost {
    public static final StatBoost NONE = new StatBoost();
    public int strength = 0;
    public int intelligence = 0;
    public int agility = 0;
    public int health = 0;
    public int defense = 0;

    /**
     * Creates a new StatBoost with all stats set to 0.
     */
    public StatBoost() { }

    public StatBoost withStrength(int strength) {
        this.strength = strength;
        return this;
    }

    public StatBoost withIntelligence(int intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    public StatBoost withAgility(int agility) {
        this.agility = agility;
        return this;
    }

    public StatBoost withHealth(int health) {
        this.health = health;
        return this;
    }

    public StatBoost withDefense(int defense) {
        this.defense = defense;
        return this;
    }

}
