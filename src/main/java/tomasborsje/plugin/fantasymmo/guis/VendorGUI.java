package tomasborsje.plugin.fantasymmo.guis;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.util.ItemGainReason;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;

public class VendorGUI extends CustomGUI {
    private final PurchasableItem[] items;

    public VendorGUI(PlayerData playerData, String inventoryName, PurchasableItem... items) {
        super(playerData, 54, inventoryName);
        this.items = items;
    }

    @Override
    protected Inventory renderInventory() {
        Inventory inv = super.renderInventory();
        // Fill borders with gray glass panes
        for (int i = 0; i < 54; i++) {
            if (i % 9 == 0 || i % 9 == 8 || i < 9 || i > 44) {
                inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            }
        }

        // Render each item for sale
        for (int i = 0; i < items.length; i++) {
            int slot = 1 + i % 7 + (i / 7) * 9 + 9;
            // Get itemstack to display
            ItemStack displayStack = items[i].getPurchasedItemStack();
            ItemUtil.addPurchaseLabel(displayStack, items[i].getPrice());
            inv.setItem(slot, displayStack);
        }
        return inv;
    }

    @Override
    public void onClickSlot(int slot) {
        // Calculate which item was clicked
        if(slot % 9 == 0 || slot % 9 == 8 || slot / 9 == 0 || slot / 9 == 5) return;
        int itemIndex = (slot % 9) - 1 + ((slot / 9) - 1) * 7;
        if(itemIndex >= items.length) return;
        PurchasableItem item = items[itemIndex];
        // Try to consume the player's money
        if(playerData.tryConsumeMoney(item.getPrice())) {
            // Create a new item and give it to the player
            playerData.giveItems(ItemGainReason.PURCHASE, item.getPurchasedItemStack());
        }
    }
}
