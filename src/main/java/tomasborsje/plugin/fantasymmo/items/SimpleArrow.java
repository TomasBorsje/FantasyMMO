package tomasborsje.plugin.fantasymmo.items;

import net.minecraft.world.item.Items;
import org.bukkit.entity.Arrow;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomArrowProjectile;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomItem;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasCustomProjectile;
import tomasborsje.plugin.fantasymmo.entities.projectiles.SimpleArrowProjectile;

public class SimpleArrow extends AbstractCustomItem implements IHasCustomProjectile {

    public SimpleArrow() {
        super();
        this.name = "Simple Arrow";
        this.customId = "SIMPLE_ARROW";
        this.baseItem = Items.ARROW;
        this.value = 1;
        this.rarity = Rarity.COMMON;
    }

    @Override
    public boolean canStack() {
        return true;
    }

    @Override
    public ItemType getType() {
        return super.getType();
    }

    @Override
    public AbstractCustomArrowProjectile getCustomArrow(Arrow arrow, PlayerData player) {
        return new SimpleArrowProjectile(arrow, player);
    }
}
