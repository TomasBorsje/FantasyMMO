package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.enums.CustomDamageType;
import tomasborsje.plugin.fantasymmo.handlers.NPCHandler;

import javax.annotation.Nullable;

public class EntityHurtListener implements Listener {
    @EventHandler
    public void OnEntityHurt(EntityDamageByEntityEvent event) {
        // Check if the hurt entity is in our NPCHandler
        @Nullable CustomEntity customEntity = NPCHandler.instance.getNPC(event.getEntity().getEntityId());

        // If the entity is custom, and the damager is a player, send a message
        if(customEntity != null && event.getDamager() instanceof Player player) {
            event.setDamage(0);

            // Send message
            player.sendMessage("You hurt a "+customEntity.name+"!");

            customEntity.hurt(player, CustomDamageType.PHYSICAL, 3);
        }
    }
}
