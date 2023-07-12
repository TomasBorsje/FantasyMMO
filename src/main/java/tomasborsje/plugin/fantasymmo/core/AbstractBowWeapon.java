package tomasborsje.plugin.fantasymmo.core;

import net.minecraft.world.item.Items;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.interfaces.IUsable;

public abstract class AbstractBowWeapon extends AbstractCustomItem implements IUsable {
    public final boolean instantFire = false;
    public final boolean infiniteAmmo = false;
    protected int damage = 1;

    public AbstractBowWeapon() {
        this.baseItem = Items.BOW;
    }

    /**
     * Called when the player shoots an arrow. This allows a bow to modify the arrow's stats.
     *
     * @param arrow      The arrow that was fired.
     * @param playerData The player that fired the arrow.
     */
    public void modifyFiredArrow(AbstractCustomArrowProjectile arrow, PlayerData playerData) {
        arrow.damage += this.damage;
    }

    @Override
    public boolean rightClick(PlayerData playerData, ItemStack item) {
        return true;
    }

    @Override
    public boolean allowDefaultUse() {
        return true;
    }

    public int getDamage() {
        return damage;
    }
}
