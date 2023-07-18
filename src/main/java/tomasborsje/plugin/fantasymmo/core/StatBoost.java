package tomasborsje.plugin.fantasymmo.core;

import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.MMOClass;

/**
 * Wrapper class for stat benefits when an item is held or equipped.
 */
public class StatBoost {
    public static final StatBoost NONE = new StatBoost();
    public final int itemScore;
    public final float durabilityMultiplier;
    public final float statMultiplier;
    public int strength = 0;
    public int intelligence = 0;
    public int focus = 0;
    public int health = 0;
    public int defense = 0;

    /**
     * Creates a new StatBoost with the given item score
     * and item type. The item type is used to determine
     * how each stat is applied.
     */
    public StatBoost(int itemScore, ItemType type, MMOClass mmoClass) {
        this.itemScore = itemScore;
        // Get stat multiplier based on item type
        switch (type) {
            case HELMET -> this.statMultiplier = 0.7f;
            case CHESTPLATE -> this.statMultiplier = 0.9f;
            case LEGGINGS -> this.statMultiplier = 0.8f;
            case BOOTS -> this.statMultiplier = 0.6f;
            case WAND, LONGBOW, SWORD, STAFF, SHORTBOW -> this.statMultiplier = 1f;
            default -> throw new IllegalArgumentException("Invalid item type for stats: " + type);
        }
        // Get durability multiplier based on class
        switch (mmoClass) {
            case ARCHER -> this.durabilityMultiplier = 0.8f;
            case WARRIOR -> this.durabilityMultiplier = 1.2f;
            case MAGE -> this.durabilityMultiplier = 0.9f;
            default -> throw new IllegalArgumentException("Invalid MMO class for durability: " + mmoClass);
        }
    }
    private StatBoost() {
        this.itemScore = 0;
        this.statMultiplier = 0;
        this.durabilityMultiplier = 0;
    }

    /**
     * Calculates bonus stat values for this stat boost based on the item type and item score.
     * @return The bonus stat values for this stat boost.
     */
    private int getStatForItemScore() {
        // TODO: Exponential stuff here, etc.
        return Math.max(1, (int) (this.itemScore * this.statMultiplier));
    }

    /**
     * Calculates bonus health values for this stat boost based on the MMO class and item score.
     * @return The bonus health values for this stat boost.
     */
    private int getHealthForItemScore() {
        return Math.max(1, (int) (this.itemScore * this.statMultiplier * this.durabilityMultiplier * 1.5));
    }

    /**
     * Calculates defense values for this stat boost based on the MMO class and item score.
     * @return The defense values for this stat boost.
     */
    private int getDefenseForItemScore() {
        return Math.max(1, (int) (this.itemScore * this.statMultiplier * this.durabilityMultiplier * 0.5));
    }

    public StatBoost withStrength() {
        this.strength = getStatForItemScore();
        return this;
    }

    public StatBoost withIntelligence() {
        // Calculate intelligence given itemlevel and type
        this.intelligence = getStatForItemScore();
        return this;
    }

    public StatBoost withFocus() {
        this.focus = getStatForItemScore();
        return this;
    }

    public StatBoost withHealth() {
        this.health = getHealthForItemScore();
        return this;
    }

    public StatBoost withDefense() {
        this.defense = getDefenseForItemScore();
        return this;
    }

}
