package tomasborsje.plugin.fantasymmo.core.util;

public class StatCalc {
    // https://www.desmos.com/calculator/w2cwlqxnv1
    public static int getBaseHealthAtLevel(int level) {
        // Exponential growth curve, with 30,000 base health at level 100
        // and 20 base health at level 1
        return (int) ((Math.pow(level/0.31622776601, 2) * 0.3) + 17);
    }
    public static int getBaseManaAtLevel(int level) {
        // Exponential growth curve, with 2,000 base mana at level 100
        // and 30 base mana at level 1
        return (int) ((Math.pow(level/0.70710678118, 2) * 0.1) + 30);
    }

    /**
     * Returns the mana regen per second at the given level.
     * @param level The level to get the mana regen for.
     * @return The mana regen per second at the given level.
     */
    public static int getManaRegenAtLevel(int level) {
        // Linear curve with 1 mana per sec at level 1, and 20 mana per sec at level 100
        return (int) (level * 0.19 + 1);
    }

    public static int getExperienceForLevel(int level) {
        return level * 50;
    }

    public static String formatInt(int value) {
        return String.format("%,d", value);
    }
}
