package tomasborsje.plugin.fantasymmo.entities.projectiles;

import org.bukkit.entity.Arrow;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomArrow;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

public class SimpleArrow extends AbstractCustomArrow {
    public SimpleArrow(Arrow arrowEntity, PlayerData player) {
        super(arrowEntity, player);
        this.damage = 15;
    }
}
