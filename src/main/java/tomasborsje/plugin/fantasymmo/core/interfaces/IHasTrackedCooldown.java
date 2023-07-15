package tomasborsje.plugin.fantasymmo.core.interfaces;

public interface IHasTrackedCooldown {
    /**
     * Get the name of the cooldown, displayed on the scoreboard.
     * Chat colors should also be specified.
     * @return The name of the cooldown.
     */
    public String getCooldownName();

    /**
     * Get the id of the cooldown.
     * Used to identify the cooldown in the player's cooldowns.
     * @return The id of the cooldown.
     */
    public String getCooldownId();

    /**
     * Get the duration of the cooldown in ticks.
     * @return The duration of the cooldown, in ticks.
     */
    public int getCooldownDuration();
}
