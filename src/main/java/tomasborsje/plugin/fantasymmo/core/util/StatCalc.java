package tomasborsje.plugin.fantasymmo.core.util;

public class StatCalc {
    // https://www.desmos.com/calculator/w2cwlqxnv1
    public static int getBaseHealthAtLevel(int level) {
        // Exponential growth curve, with 30,000 base health at level 100
        // and 20 base health at level 1
        return (int) ((Math.pow(level/0.31622776601, 2) * 0.3) + 14);
    }
    public static int getBaseManaAtLevel(int level) {
        // Exponential growth curve, with 2,000 base mana at level 100
        // and 30 base mana at level 1
        return (int) ((Math.pow(level/0.70710678118, 2) * 0.1) + 30);
    }
}
