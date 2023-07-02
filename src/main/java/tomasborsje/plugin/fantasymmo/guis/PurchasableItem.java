package tomasborsje.plugin.fantasymmo.guis;

import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;

public class PurchasableItem {
    private final ICustomItem item;
    private final int price;

    public PurchasableItem(ICustomItem item, int price) {
        this.item = item;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public ICustomItem getItem() {
        return item;
    }

    public ItemStack getPurchasedItemStack() {
        return item.createStack();
    }
}
