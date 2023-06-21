package tomasborsje.plugin.fantasymmo.core;

import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IStatsProvider;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;

public abstract class AbstractArmourItem implements ICustomItem, IStatsProvider {
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
    public ItemStack createStack() {
        return ItemUtil.createDefaultStack(this);
    }

    @Override
    public int getValue() {
        return value;
    }
}
