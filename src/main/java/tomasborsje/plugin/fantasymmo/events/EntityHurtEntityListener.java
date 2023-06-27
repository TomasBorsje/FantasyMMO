package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.enums.CustomDamageType;
import tomasborsje.plugin.fantasymmo.handlers.EntityHandler;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

import javax.annotation.Nullable;

public class EntityHurtEntityListener implements Listener {
    @EventHandler
    public void OnEntityHurtEntity(EntityDamageByEntityEvent event) {

        // Never allow vanilla health changes
        event.setDamage(0);

        // Check if the hurt entity is in our NPCHandler
        @Nullable CustomEntity customEntity = EntityHandler.instance.getEntity(event.getEntity().getEntityId());

        // If the entity is custom, and the damager is a player, send a message
        if(customEntity != null && event.getDamager() instanceof Player player) {

            // Get player data
            PlayerData playerData = PlayerHandler.instance.getPlayerData(player);

            // Apply player on attack effects for each buff
            playerData.buffs.forEach((buff) -> {
                buff.onHitEnemy(playerData, customEntity);
            });

            // Apply on take damage effects for each buff the entity has
            customEntity.buffs.forEach((buff) -> {
                buff.onReceiveDamage(customEntity, playerData, 1);
            });

            // TODO: Calculate damage if using a custom item with an attack damage
            // Damage the custom entity
            customEntity.hurt(player, CustomDamageType.PHYSICAL, 1);

            // Set player in combat
            playerData.markCombat();

            return;
        }

        // Check if the damaging entity is in our NPCHandler
        CustomEntity customAttacker = EntityHandler.instance.getEntity(event.getDamager().getEntityId());

        // If the entity is custom, and the entity getting hurt is a player, send a message and deal them damage
        if(customAttacker != null && event.getEntity() instanceof Player player) {

            // Get player data
            PlayerData playerData = PlayerHandler.instance.getPlayerData(player);

            // Damage the player
            playerData.hurt(customAttacker, customAttacker.attackDamage, CustomDamageType.PHYSICAL);

            // Apply on hit effects for each buff the custom attacker has
            customAttacker.buffs.forEach((buff) -> {
                buff.onHitEnemy(customAttacker, playerData);
            });

            // Apply on take damage effects for each buff the player has
            playerData.buffs.forEach((buff) -> {
                buff.onReceiveDamage(playerData, customAttacker, customAttacker.attackDamage);
            });

            return;
        }
    }
}
