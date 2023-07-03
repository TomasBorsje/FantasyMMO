package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.enums.CustomDamageType;
import tomasborsje.plugin.fantasymmo.handlers.EntityHandler;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

public class EntityReceiveDamageListener implements Listener {
    @EventHandler
    public void EntityReceiveDamage(EntityDamageEvent event) {
        // No vanilla damage taking allowed
        event.setDamage(0);
        // If this is fall damage, use our custom damage system
        if(event.getCause() == EntityDamageEvent.DamageCause.FALL && event.getEntity() instanceof LivingEntity living) {
            double distFallen = living.getFallDistance();
            int entityId = living.getEntityId();
            // If it's a custom entity, apply fall damage based on it's max hp
            if(EntityHandler.instance.hasEntity(entityId)) {
                CustomEntity customEntity = EntityHandler.instance.getEntity(entityId);
                // Entity takes 3% max hp per block fallen
                int fallDamage = (int) (distFallen*0.03*customEntity.maxHealth);
                customEntity.hurt(null, CustomDamageType.FALL, fallDamage);
            }
            // Or if it's a player, do the same
            else if(living instanceof Player player) {
                PlayerData pd = PlayerHandler.instance.getPlayerData(player);
                int fallDamage = (int) (distFallen*0.03*pd.maxHealth);
                pd.hurt(null, CustomDamageType.FALL, fallDamage);
            }
        }

    }
}
