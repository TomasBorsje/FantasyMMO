package tomasborsje.plugin.fantasymmo.handlers;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;

public class EntityDeathHandler {
    public static EntityDeathHandler instance = new EntityDeathHandler();
    private EntityDeathHandler() { }
    public void handleDeath(CustomEntity deadEntity, Entity killer) {
        // If null killer, ignore.
        if(killer == null) { return; }

        // If a player killed this, send message
        if(killer instanceof Player player) {
            player.sendMessage("You killed " + deadEntity.name + "!");

            // Grant player xp
            PlayerHandler.instance.loadPlayerData(player).gainExperience(deadEntity.getKillXp());

            // Lookup custom loot tables
            ItemStack[] droppedLoot = deadEntity.getDroppedLoot(player);
            // Add item(s) to inventory, storing left over items that don't fit
            var leftover = player.getInventory().addItem(droppedLoot);

            // TODO: Put leftover items in lost and found or something

        }
        // Else if a custom entity killed this, do something
        else if (NPCHandler.instance.hasNPC(killer.getEntityId())) {
            // Call onKill function
            NPCHandler.instance.getNPC(killer.getEntityId()).onKill(deadEntity);
        }
    }
}
