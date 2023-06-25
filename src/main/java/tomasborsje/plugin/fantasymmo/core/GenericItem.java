package tomasborsje.plugin.fantasymmo.core;

import net.minecraft.world.item.Item;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasDescription;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;

/**
 * A generic item class that does nothing.
 * Allows for easier creation of basic items without a new class each time.
 */
public class GenericItem implements ICustomItem, IHasDescription {
    private final String customId;
    private final String name;
    private final Item baseItem;
    private final String loreDescription;
    private final Rarity rarity;
    private final int value;
    public GenericItem(String customId, String name, Item baseItem, Rarity rarity, int value, String description) {
        this.customId = customId;
        this.name = name;
        this.baseItem = baseItem;
        this.rarity = rarity;
        this.value = value;
        // Add gray color to lore strings.
        this.loreDescription = description;
    }
    @Override
    public String getCustomId() {
        return customId;
    }

    @Override
    public boolean canStack() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Item getBaseItem() {
        return baseItem;
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

    @Override
    public ItemType getType() {
        return ItemType.MISC;
    }

    @Override
    public String getDescription() {
        return loreDescription;
    }
}
