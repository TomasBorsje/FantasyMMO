package tomasborsje.plugin.fantasymmo.core.interfaces;

import tomasborsje.plugin.fantasymmo.core.enums.EquipType;
import tomasborsje.plugin.fantasymmo.core.StatBoost;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

/**
 * Provides stats to the player while held or equipped.
 */
public interface IStatsProvider {
    public default StatBoost getStats() { return StatBoost.NONE; }
    public default EquipType getEquipType() { return EquipType.HELD; }
    public default void applyStats(PlayerData playerData) {
        StatBoost held = this.getStats();
        playerData.strength += held.strength;
        playerData.intelligence += held.intelligence;
        playerData.maxHealth += held.health;
        playerData.defense += held.defense;
    }
}
