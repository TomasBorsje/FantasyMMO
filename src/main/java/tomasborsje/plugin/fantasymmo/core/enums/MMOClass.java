package tomasborsje.plugin.fantasymmo.core.enums;

public enum MMOClass {
    ARCHER("Archer"),
    WARRIOR("Warrior"),
    MAGE("Mage");

    public final String displayName;

    MMOClass(String displayName) {
        this.displayName = displayName;
    }
}
