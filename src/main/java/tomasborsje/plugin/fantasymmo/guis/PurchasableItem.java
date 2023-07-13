package tomasborsje.plugin.fantasymmo.guis;

import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;

/**
 * A single entry in a shop.
 * Contains the item, its quantity, and the price.
 */
public class PurchasableItem {
    private final ICustomItem item;
    private final int price;
    private final int quantity;

    public PurchasableItem(ICustomItem item, int price) {
        this.item = item;
        this.price = price;
        this.quantity = 1;
    }
    public PurchasableItem(ICustomItem item, int price, int quantity) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public ICustomItem getItem() {
        return item;
    }

    public ItemStack getPurchasedItemStack() {
        return item.createStack(quantity);
    }
}
