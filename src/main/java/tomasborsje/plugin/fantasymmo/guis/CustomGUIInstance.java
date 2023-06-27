package tomasborsje.plugin.fantasymmo.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

/**
 * Represents an instance of a GUI open for a player.
 * This class is abstract, and should be extended for each GUI.
 * Provides methods to render and show the GUI, as well as handle slot clicks.
 */
public abstract class CustomGUIInstance {

    protected int size;
    protected String name;
    protected final PlayerData playerData;
    protected Inventory display;
    protected final ItemStack emptyPane = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);

    public CustomGUIInstance(PlayerData playerData, int size, String inventoryName) {
        this.playerData = playerData;
        this.size = size;
        this.name = inventoryName;
    }
    public void onClickSlot(int slot) { }

    public void show() {
        playerData.player.openInventory(renderInventory());
    }

    protected Inventory renderInventory() {
        display = Bukkit.createInventory(DenyInvHolder.INSTANCE, size, name);
        fillWithBlank(display);
        return display;
    }

    /**
     * By default, this fills the inventory with gray stained-glass panes.
     * @param i The inventory to fill
     */
    protected void fillWithBlank(Inventory i) {
        for(int j = 0; j < i.getSize(); j++) {
            i.setItem(j, emptyPane);
        }
    }
}
