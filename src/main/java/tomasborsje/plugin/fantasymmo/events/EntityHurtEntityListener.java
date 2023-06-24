package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.enums.CustomDamageType;
import tomasborsje.plugin.fantasymmo.handlers.NPCHandler;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

import javax.annotation.Nullable;

public class EntityHurtEntityListener implements Listener {
    @EventHandler
    public void OnEntityHurtEntity(EntityDamageByEntityEvent event) {

        // Check if the hurt entity is in our NPCHandler
        @Nullable CustomEntity customEntity = NPCHandler.instance.getNPC(event.getEntity().getEntityId());

        // If the entity is custom, and the damager is a player, send a message
        if(customEntity != null && event.getDamager() instanceof Player player) {
            event.setDamage(0);

            // Send message
            player.sendMessage("You hurt a "+customEntity.name+"!");

            customEntity.hurt(player, CustomDamageType.PHYSICAL, 3);

            return;
        }

        // Check if the damaging entity is in our NPCHandler
        CustomEntity customAttacker = NPCHandler.instance.getNPC(event.getDamager().getEntityId());

        // If the entity is custom, and the entity getting hurt is a player, send a message and deal them damage
        if(customAttacker != null && event.getEntity() instanceof Player player) {
            event.setDamage(0);

            // Send message
            player.sendMessage("You got hurt by a "+customAttacker.name+"!");

            // Get player data
            PlayerData playerData = PlayerHandler.instance.getPlayerData(player);

            playerData.hurt(customAttacker, customAttacker.attackDamage, CustomDamageType.PHYSICAL);

            return;
        }
    }
}
