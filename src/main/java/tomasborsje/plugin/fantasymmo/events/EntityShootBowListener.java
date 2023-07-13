package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.AbstractBowWeapon;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomArrowProjectile;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasCustomArrow;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;
import tomasborsje.plugin.fantasymmo.handlers.ProjectileHandler;

public class EntityShootBowListener implements Listener {
    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        // Only handle players firing arrows
        if(event.getEntity() instanceof Player player) {

            // Get player data
            PlayerData playerData = PlayerHandler.instance.getPlayerData(player);

            // Get consumed stack
            ItemStack consumed = event.getConsumable();

            // Get held item stack
            ItemStack bow = event.getBow();

            // Only handle custom items
            if(!ItemUtil.IsCustomItem(consumed) || !ItemUtil.IsCustomItem(bow)) {
                event.setCancelled(true);
                return;
            }

            // If an arrow was fired, get which type of arrow it is and register it with the projectile handler
            if (event.getProjectile() instanceof Arrow arrow) {

                ICustomItem customProjectile = ItemUtil.GetAsCustomItem(consumed);

                // If it's a custom projectile, get its custom arrow projectile
                if(customProjectile instanceof IHasCustomArrow hasCustomProjectile) {

                    // Get the custom arrow
                    AbstractCustomArrowProjectile customArrowProjectile = hasCustomProjectile.getCustomArrow(arrow, playerData);

                    // Get the custom bow
                    ICustomItem bowItem = ItemUtil.GetAsCustomItem(bow);

                    // If it's a bow, call the arrow fired method of the bow
                    if(bowItem instanceof AbstractBowWeapon customBow) {
                        // Call on-attack functions
                        customBow.rightClick(playerData, bow);
                        customBow.modifyFiredArrow(customArrowProjectile, playerData);
                    }

                    // Register arrow with projectile handler
                    ProjectileHandler.instance.registerArrow(arrow, customArrowProjectile);
                }
            }
        }
    }
}
