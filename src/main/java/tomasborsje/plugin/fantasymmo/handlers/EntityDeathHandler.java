package tomasborsje.plugin.fantasymmo.handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.util.TooltipUtil;

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
            // We add -20 to 20% variation to the money dropped, minimum 1 copper
            int moneyDropped = (int) Math.max(1, (1+(Math.random()-0.5)*0.2)*deadEntity.killMoney);

            player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You killed " + deadEntity.name +
                    "! (+" + xp + " xp, " + TooltipUtil.GetValueStringItalic(moneyDropped) + ChatColor.GRAY+")");

            // Grab player data
            PlayerData playerData = PlayerHandler.instance.loadPlayerData(player);

            // Add xp to player
            playerData.gainExperience(xp);

            // Lookup custom loot tables
            ItemStack[] droppedLoot = deadEntity.getDroppedLoot(player);

            // Add item(s) to player inventory
            // TODO: Loot sharing, mobs track who hit them etc
            playerData.giveItems(droppedLoot);

            // Give player money
            playerData.addMoney(moneyDropped);

            // Register kill for quest progress
            playerData.registerKillForQuests(deadEntity);
        }
        // Else if a custom entity killed this, do something
        else if (EntityHandler.instance.hasEntity(killer.getEntityId())) {
            // Call onKill function
            EntityHandler.instance.getEntity(killer.getEntityId()).onKill(deadEntity);
        }
    }
}
