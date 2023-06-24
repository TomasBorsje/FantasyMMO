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
        StatBoost stats = this.getStats();
        playerData.strength += stats.strength;
        playerData.intelligence += stats.intelligence;
        playerData.maxHealth += stats.health;
        playerData.defense += stats.defense;
    }
}
