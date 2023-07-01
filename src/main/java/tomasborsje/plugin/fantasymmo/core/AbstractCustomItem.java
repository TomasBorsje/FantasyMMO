package tomasborsje.plugin.fantasymmo.core;

import net.minecraft.world.item.Item;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;

public abstract class AbstractCustomItem implements ICustomItem {
    protected String customId;
    protected String name;
    protected Item baseItem;
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
    public Item getBaseItem() {
        return baseItem;
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
