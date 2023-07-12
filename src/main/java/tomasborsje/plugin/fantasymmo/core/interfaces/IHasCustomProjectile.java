package tomasborsje.plugin.fantasymmo.core.interfaces;

import org.bukkit.entity.Arrow;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomArrowProjectile;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

public interface IHasCustomProjectile {
    public AbstractCustomArrowProjectile getCustomArrow(Arrow arrow, PlayerData player);
}
