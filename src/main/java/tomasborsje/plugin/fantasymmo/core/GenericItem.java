package tomasborsje.plugin.fantasymmo.core;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasDescription;
import tomasborsje.plugin.fantasymmo.core.util.TooltipHelper;

import java.util.List;

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
        net.minecraft.world.item.ItemStack nmsStack = new net.minecraft.world.item.ItemStack(baseItem);

        // Create new CompoundTag
        CompoundTag nbt = nmsStack.getOrCreateTag();
        // Add ITEM_ID tag
        nbt.putString("ITEM_ID", this.getCustomId());
        nmsStack.setTag(nbt);

        // Convert back to CraftItemStack
        ItemStack newStack = CraftItemStack.asBukkitCopy(nmsStack);

        List<String> lore = TooltipHelper.getTooltip(this);

        // Set lore tooltip
        ItemMeta meta = newStack.getItemMeta();
        meta.setLore(lore); // Set tooltip lore
        meta.setDisplayName(rarity.getColor() + name); // Set colored name
        newStack.setItemMeta(meta);

        return newStack;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public ItemType getType() {
        return null;
    }

    @Override
    public String getDescription() {
        return loreDescription;
    }
}
