package tomasborsje.plugin.fantasymmo.guis;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

import java.util.List;

public class InfoGUI extends CustomGUIInstance {
    private final static int MIDDLE_SLOT = 22;
    public InfoGUI(PlayerData playerData) {
        super(playerData, 54, "Info");
    }

    @Override
    protected Inventory renderInventory() {
        Inventory i = super.renderInventory();

        // Put INFO/dialog in the center
        ItemStack infoBook = new ItemStack(Material.BOOK);
        // Set item description and title
        ItemMeta meta = infoBook.getItemMeta();
        meta.setLore(List.of("", ChatColor.WHITE+"Welcome to FantasyMMO!"));
        meta.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"FantasyMMO");
        infoBook.setItemMeta(meta);
        // Show the info book in the middle
        i.setItem(MIDDLE_SLOT, infoBook);

        return i;
    }

    @Override
    public void onClickSlot(int slot) {
        if(slot == MIDDLE_SLOT) {
            playerData.player.sendMessage(ChatColor.GOLD+"Welcome to FantasyMMO!");
        }
    }
}
