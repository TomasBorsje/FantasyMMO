package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IUsable;
import tomasborsje.plugin.fantasymmo.core.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

/**
 * Handles custom item use events.
 */
public class CustomItemUseListener implements Listener {

    @EventHandler
    public void OnPlayerUseItem(PlayerInteractEvent event) {

        // Doesn't apply to admins or off-hand items
        if(event.getHand() == EquipmentSlot.OFF_HAND) {
            event.setCancelled(true);
            return;
        }
        // Only handle right clicks
        if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) { return; }

        // Get NMS stack copy
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(event.getItem());
        if(nmsStack.isEmpty()) { return; }

        // Cancel any non-custom item events (no vanilla items allowed)
        if(!ItemUtil.IsCustomItem(nmsStack)) {
            //event.setCancelled(true);
            return;
        }

        // Get our player data
        PlayerData playerData = PlayerHandler.instance.loadPlayerData(event.getPlayer());

        // Get the custom item id
        String itemId = nmsStack.getTag().getString("ITEM_ID");

        // Get the custom item from the registry
        ICustomItem customItem = ItemRegistry.ITEMS.get(itemId);

        // Check if it implements IUsable
        if(customItem instanceof IUsable usableItem && playerData.useCooldown == 0) {
            // Cast to IUsable and use the item
            boolean success = usableItem.rightClick(event.getPlayer(), event.getItem());

            if(success) {
                // Set cooldown if item has a cooldown
                if(usableItem.getTickCooldown() > 0) {
                    playerData.useCooldown = usableItem.getTickCooldown();
                    event.getPlayer().setCooldown(event.getItem().getType(), usableItem.getTickCooldown());
                }
                // Reduce itemstack count if consumable
                if(usableItem.isConsumable()) {
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                }
                // Cancel vanilla event
                event.setCancelled(true);
            }
        }
        else {
            event.setCancelled(true);
        }
    }
}
