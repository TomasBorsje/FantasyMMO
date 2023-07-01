package tomasborsje.plugin.fantasymmo.core;

import org.bukkit.inventory.ItemStack;

public abstract class AbstractMeleeWeapon extends AbstractCustomItem {
    protected int damage = 1;
    protected int attackSpeed = 1; // Unused in favour of vanilla attack speed
    public void onAttack(PlayerData player, ItemStack stack, CustomEntity target) { }

    public String getAttackDescription() {
        return null;
    }

    public int getDamage() {
        return damage;
    }
}
