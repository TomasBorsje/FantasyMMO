package tomasborsje.plugin.fantasymmo.core.interfaces;

import net.minecraft.world.item.Item;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;

public interface ICustomItem extends IHasId {
    @Override
    public String getCustomId();

    public boolean canStack();

    /**
     * Get the name of this item.
     * @return The name of this item.
     */
    public String getName();

    public Item getBaseItem();

    /**
     * Get the rarity of this item.
     * @return The rarity of this item.
     */
    public Rarity getRarity();

    /**
     * Create a new ItemStack of this item.
     * @return A new ItemStack of this item.
     */
    public ItemStack createStack();

    /**
     * Create a new ItemStack of this item with a specified amount.
     * @param amount The amount of this item to create.
     * @return A new ItemStack of this item.
     */
    public default ItemStack createStack(int amount) {
        ItemStack stack = createStack();
        stack.setAmount(amount);
        return stack;
    }

    /**
     * An item's value in copper.
     * @return The value of this item in copper coins.
     */
    public int getValue();

    /**
     * Get the type of this item.
     * The type will be displayed alongside the item's rarity.
     */
    public default ItemType getType() {
        return ItemType.MISC;
    };
}
