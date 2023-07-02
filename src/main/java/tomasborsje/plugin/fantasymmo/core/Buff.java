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
    public int maxStacks;
    public final boolean negative;
    public int currentStacks;
    public boolean refreshOnApply = true;

    public boolean nonCombat = false;

    /**
     * Creates a new buff with the given name, duration, debuff status, and stackability.
     * @param name The name of the buff
     * @param duration The duration of the buff in ticks
     * @param isDebuff Whether or not the buff is a debuff
     * @param maxStacks The maximum number of stacks the buff can have
     */
    public Buff(String name, int duration, boolean isDebuff, int maxStacks) {
        this.name = name;
        this.duration = duration;
        this.ticksLeft = duration;
        this.negative = isDebuff;
        this.maxStacks = maxStacks;
        this.currentStacks = 1;
    }

    /**
     * Creates a new buff with the given name, duration, and debuff status.
     * @param name The name of the buff
     * @param duration The duration of the buff in ticks
     * @param isDebuff Whether or not the buff is a debuff
     */
    public Buff(String name, int duration, boolean isDebuff) {
        this(name, duration, isDebuff, 1);
    }

    /**
     * Creates a new buff with the given name and duration.
     * @param name The name of the buff
     * @param duration The duration of the buff in ticks
     */
    public Buff(String name, int duration) {
        this(name, duration, false, 1);
    }

    public void modifyStats(PlayerData playerStats) { }

    public void tick(IBuffable buffHolder) {
        ticksLeft--;
    }
    public boolean isExpired() {
        return ticksLeft <= 0;
    }
    public void onApply(IBuffable buffHolder) { }
    public void onRefresh(IBuffable buffHolder) { }
    public void onRemove(IBuffable buffHolder) { }

    /**
     * By default, buffs are removed when the holder enters combat if they are noncombat buffs.
     * @param buffHolder The entity that holds the buff
     */
    public void onEnterCombat(IBuffable buffHolder) {
        if(nonCombat) {
            ticksLeft = 0;
        }
    }
    public void onLeaveCombat(IBuffable buffHolder) { }
    public void onHitEnemy(IBuffable buffHolder, IBuffable victim) { }
    public void onHitAlly(PlayerData buffHolder, PlayerData ally) { }
    public void onReceiveDamage(IBuffable buffHolder, IBuffable attacker, int damage) { }
}
