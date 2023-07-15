package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.AbstractBowWeapon;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasTrackedCooldown;
import tomasborsje.plugin.fantasymmo.core.interfaces.IUsable;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.core.util.TooltipUtil;
import tomasborsje.plugin.fantasymmo.guis.MainMenuGUI;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

/**
 * Handles custom item use events.
 */
public class PlayerUseItemListener implements Listener {

    @EventHandler
    public void OnPlayerUseItem(PlayerInteractEvent event) {

        // Only handle right clicks
        if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) { return; }

        ItemStack usedStack = event.getItem();

        // Don't apply to non-custom items
        if(!ItemUtil.IsCustomItem(usedStack)) { return; }

        // Get the custom item
        ICustomItem customItem = ItemUtil.GetAsCustomItem(usedStack);

        // Get our player data
        PlayerData playerData = PlayerHandler.instance.loadPlayerData(event.getPlayer());

        // Doesn't apply to offhand items
        if(event.getHand() == EquipmentSlot.OFF_HAND) {
            event.setCancelled(true);
            return;
        }

        // If the used item is the world map, open the info GUI
        if(usedStack.hasItemMeta() && usedStack.getItemMeta().getDisplayName().equals(TooltipUtil.worldMapName)) {
            // Open the info GUI
            playerData.openGUI(new MainMenuGUI(playerData));
            return;
        }

        // If it's a bow, allow the event
        if(customItem instanceof AbstractBowWeapon) {
            return;
        }

        // Check if it implements IUsable
        if(customItem instanceof IUsable usableItem && playerData.useCooldown == 0) {
            // If the item has a specific cooldown, check it isn't on cooldown and can actually be used
            if(customItem instanceof IHasTrackedCooldown cooldownSource) {
                // If the cooldown is still active, cancel the event
                if(!playerData.tryAddCooldown(cooldownSource)) {
                    event.setCancelled(true);
                    return;
                }
            }

            // Cast to IUsable and use the item
            boolean success = usableItem.rightClick(playerData, event.getItem());
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
                // Cancel vanilla event if not a default effect item
                if(!usableItem.allowDefaultUse()) {
                    event.setCancelled(true);
                }
            }
        }
        else {
            event.setCancelled(true);
        }
    }
}
