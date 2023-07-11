package tomasborsje.plugin.fantasymmo.core;

import net.minecraft.world.item.Items;

public abstract class AbstractBowWeapon extends AbstractCustomItem {
    public final boolean instantFire = false;
    public final boolean infiniteAmmo = false;

    public AbstractBowWeapon() {
        this.baseItem = Items.BOW;
    }

    /**
     * Called when the player shoots an arrow. This allows a bow to modify the arrow's stats.
     * @param arrow The arrow that was fired.
     * @param playerData The player that fired the arrow.
     */
    public abstract void modifyFiredArrow(AbstractCustomArrow arrow, PlayerData playerData);
}
