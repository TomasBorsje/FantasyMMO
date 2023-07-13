package tomasborsje.plugin.fantasymmo.core.interfaces;

import org.bukkit.entity.Arrow;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomArrowProjectile;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

public interface IHasCustomArrow {
    public AbstractCustomArrowProjectile getCustomArrow(Arrow arrow, PlayerData player);
    public int getDisplayDamage();
}
