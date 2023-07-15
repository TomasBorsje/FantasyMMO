package tomasborsje.plugin.fantasymmo.core;

import org.bukkit.ChatColor;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasTrackedCooldown;

public class CooldownInstance {
    private final String cooldownId;
    private final int duration;
    private final String cooldownDisplayName;
    private int ticksRemaining;
    public CooldownInstance(IHasTrackedCooldown cooldownSource) {
        this.cooldownId = cooldownSource.getCooldownId();
        this.duration = cooldownSource.getCooldownDuration();
        this.cooldownDisplayName = cooldownSource.getCooldownName();
        this.ticksRemaining = this.duration;
    }

    public String getCooldownId() {
        return this.cooldownId;
    }

    public String getCooldownDisplayName() {
        return this.cooldownDisplayName;
    }

    public int getTicksRemaining() {
        return ticksRemaining;
    }

    public boolean isExpired() {
        return this.ticksRemaining <= 0;
    }

    public void tick() {
        this.ticksRemaining--;
    }

    /**
     * Returns the cooldown display string in the format of "CooldownName (minutes:seconds)"
     * @return The cooldown display string
     */
    public String getDisplayString() {
        int seconds = this.ticksRemaining / 20;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%s "+ChatColor.GRAY+"(%d:%02d)", this.cooldownDisplayName, minutes, seconds);
    }
}
