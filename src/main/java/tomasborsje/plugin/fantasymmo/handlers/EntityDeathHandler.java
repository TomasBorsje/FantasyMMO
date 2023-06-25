package tomasborsje.plugin.fantasymmo.handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

public class EntityDeathHandler {
    public static EntityDeathHandler instance = new EntityDeathHandler();
    private EntityDeathHandler() { }
    public void handleDeath(CustomEntity deadEntity, Entity killer) {
        // If null killer, ignore.
        if(killer == null) { return; }

        // If a player killed this, send message
        if(killer instanceof Player player) {
            int xp = deadEntity.getKillXp();
            player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You killed " + deadEntity.name + "! (+" + xp + " xp)");

            // Grab player data
            PlayerData playerData = PlayerHandler.instance.loadPlayerData(player);

            // Add xp to player
            playerData.gainExperience(xp);

            // Lookup custom loot tables
            ItemStack[] droppedLoot = deadEntity.getDroppedLoot(player);

            // Add item(s) to player inventory
            playerData.giveItems(true, deadEntity, droppedLoot);
        }
        // Else if a custom entity killed this, do something
        else if (NPCHandler.instance.hasNPC(killer.getEntityId())) {
            // Call onKill function
            NPCHandler.instance.getNPC(killer.getEntityId()).onKill(deadEntity);
        }
    }
}
