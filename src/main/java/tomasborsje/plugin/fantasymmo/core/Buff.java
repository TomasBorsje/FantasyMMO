package tomasborsje.plugin.fantasymmo.core;

import tomasborsje.plugin.fantasymmo.core.interfaces.IBuffable;

/**
 * Represents a buff or debuff that can be applied to a player or custom entity.
 * If the buff is not stackable, re-applying it will instead refresh the duration of
 * any existing buffs.
 * By default, buffs are not stackable and are not debuffs.
 */
public abstract class Buff {
    public final String name;
    public int duration;
    public int ticksLeft;
    public final boolean negative;
    public final boolean isStackable;

    /**
     * Creates a new buff with the given name, duration, debuff status, and stackability.
     * @param name The name of the buff
     * @param duration The duration of the buff in ticks
     * @param isDebuff Whether or not the buff is a debuff
     * @param isStackable Whether or not the buff is stackable
     */
    public Buff(String name, int duration, boolean isDebuff, boolean isStackable) {
        this.name = name;
        this.duration = duration;
        this.ticksLeft = duration;
        this.negative = isDebuff;
        this.isStackable = isStackable;
    }

    /**
     * Creates a new buff with the given name, duration, and debuff status.
     * @param name The name of the buff
     * @param duration The duration of the buff in ticks
     * @param isDebuff Whether or not the buff is a debuff
     */
    public Buff(String name, int duration, boolean isDebuff) {
        this(name, duration, isDebuff, false);
    }

    /**
     * Creates a new buff with the given name and duration.
     * @param name The name of the buff
     * @param duration The duration of the buff in ticks
     */
    public Buff(String name, int duration) {
        this(name, duration, false, false);
    }

    public void modifyStats(PlayerData playerStats) { }

    public void tick(IBuffable buffHolder) {
        ticksLeft--;
    }
    public boolean isExpired() {
        return ticksLeft <= 0;
    }
    public void onApply(IBuffable buffHolder) { }
    public void onRemove(IBuffable buffHolder) { }
    public void onHitEnemy(IBuffable buffHolder, IBuffable victim) { }
    public void onHitAlly(PlayerData buffHolder, PlayerData ally) { }
    public void onReceiveDamage(IBuffable buffHolder, IBuffable attacker, int damage) { }
}
