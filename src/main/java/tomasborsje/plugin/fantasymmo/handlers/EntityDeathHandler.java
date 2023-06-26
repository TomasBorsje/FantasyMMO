package tomasborsje.plugin.fantasymmo.handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.util.TooltipHelper;

/**
 * This class handles the deaths of custom entities.
 * It will give the player who killed the entity xp, loot, and money.
 */
public class EntityDeathHandler {
    public static EntityDeathHandler instance = new EntityDeathHandler();
    private EntityDeathHandler() { }
    public void handleDeath(CustomEntity deadEntity, Entity killer) {
        // If null killer, ignore.
        if(killer == null) { return; }

        // If a player killed this, send message
        if(killer instanceof Player player) {
            int xp = deadEntity.getKillXp();
            int moneyDropped = deadEntity.killMoney;

            player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You killed " + deadEntity.name +
                    "! (+" + xp + " xp, " + TooltipHelper.GetValueStringItalic(moneyDropped) + ChatColor.GRAY+")");

            // Grab player data
            PlayerData playerData = PlayerHandler.instance.loadPlayerData(player);

            // Add xp to player
            playerData.gainExperience(xp);

            // Lookup custom loot tables
            ItemStack[] droppedLoot = deadEntity.getDroppedLoot(player);

            // Add item(s) to player inventory
            playerData.giveItems(true, deadEntity, droppedLoot);

            // Give player money
            playerData.addMoney(moneyDropped);
        }
        // Else if a custom entity killed this, do something
        else if (EntityHandler.instance.hasEntity(killer.getEntityId())) {
            // Call onKill function
            EntityHandler.instance.getEntity(killer.getEntityId()).onKill(deadEntity);
        }
    }
}
