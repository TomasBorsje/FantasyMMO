package tomasborsje.plugin.fantasymmo.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import tomasborsje.plugin.fantasymmo.FantasyMMO;
import tomasborsje.plugin.fantasymmo.core.enums.CustomDamageType;
import tomasborsje.plugin.fantasymmo.handlers.ProjectileHandler;

/**
 * Represents a custom arrow fired by a player.
 */
public abstract class AbstractCustomArrow {
    public final Arrow arrowEntity;
    protected PlayerData owner;
    protected int damage = 1;
    public AbstractCustomArrow(Arrow arrowEntity, PlayerData player) {
        this.arrowEntity = arrowEntity;
        this.owner = player;
    }

    /**
     * Called when the arrow hits a custom entity.
     * By default, it will deal damage to the entity.
     * @param entity The entity that was hit.
     */
    public void onHitEntity(CustomEntity entity) {
        entity.hurt(owner.player, CustomDamageType.PHYSICAL, damage);
    }

    /**
     * Called when the arrow hits a block.
     */
    public void onHitBlock() {
        // Remove and unregister the arrow after 20 ticks
        Bukkit.getScheduler().runTaskLater(FantasyMMO.Plugin,
                () -> { arrowEntity.remove(); ProjectileHandler.instance.unregisterArrow(this); },
                20);
    }
}
