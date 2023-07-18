package tomasborsje.plugin.fantasymmo.core;

import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.enums.EquipType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasItemScore;
import tomasborsje.plugin.fantasymmo.core.interfaces.IStatProvider;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;

public abstract class AbstractArmourItem implements ICustomItem, IStatProvider, IHasItemScore {
    protected String customId;
    protected String name;
    protected Rarity rarity;
    protected int value;

    @Override
    public String getCustomId() {
        return customId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public EquipType getEquipType() {
        return EquipType.ARMOUR;
    }

    @Override
    public ItemStack createStack() {
        return ItemUtil.createDefaultStack(this);
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public boolean canStack() {
        return false;
    }
}
