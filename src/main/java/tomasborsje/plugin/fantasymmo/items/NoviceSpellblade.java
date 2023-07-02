package tomasborsje.plugin.fantasymmo.items;

import net.minecraft.world.item.Items;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.buffs.CelerityI;
import tomasborsje.plugin.fantasymmo.core.AbstractMeleeWeapon;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.StatBoost;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.IGlowingItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasDescription;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasItemScore;
import tomasborsje.plugin.fantasymmo.core.interfaces.IStatProvider;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;

public class NoviceSpellblade extends AbstractMeleeWeapon implements IHasDescription, IHasItemScore, IGlowingItem, IStatProvider {
    private final StatBoost stats = new StatBoost(0, 1, 0, 0);
    public NoviceSpellblade() {
        this.customId = "NOVICE_SPELLBLADE";
        this.name = "Novice Spellblade";
        this.rarity = Rarity.UNCOMMON;
        this.value = ItemUtil.Value(0, 0, 50);
        this.baseItem = Items.GOLDEN_SWORD;
        this.damage = 9;
    }

    @Override
    public StatBoost getStats() {
        return stats;
    }

    @Override
    public String getDescription() {
        return "A rudimentary enchanted dagger, used by novice mages to enhance their magic.";
    }

    @Override
    public boolean canStack() {
        return false;
    }

    @Override
    public ItemType getType() {
        return ItemType.SWORD;
    }

    @Override
    public void onAttack(PlayerData player, ItemStack stack, CustomEntity target) {
        player.addBuff(new CelerityI());
    }

    @Override
    public String getAttackDescription() {
        return "Strike an enemy to gain "+ ChatColor.BLUE+"Celerity I"+ChatColor.WHITE+",\n" +
                "increasing Mana Regeneration by 2/s for 10 seconds.";
    }
}
