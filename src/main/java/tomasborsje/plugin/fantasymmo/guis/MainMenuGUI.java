package tomasborsje.plugin.fantasymmo.guis;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.util.GUIUtil;

import java.util.List;

public class MainMenuGUI extends CustomGUI {
    private final static int TITLE_SLOT = GUIUtil.GetSlot(5, 2);
    public MainMenuGUI(PlayerData playerData) {
        super(playerData, 54, "Menu");
    }

    @Override
    protected Inventory renderInventory() {
        Inventory inv = super.renderInventory();

        // Put INFO/dialog in the center
        ItemStack infoBook = new ItemStack(Material.BOOK);
        // Set item description and title
        ItemMeta meta = infoBook.getItemMeta();
        meta.setLore(List.of("", ChatColor.WHITE+"Welcome to FantasyMMO!"));
        meta.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"FantasyMMO");
        infoBook.setItemMeta(meta);
        // Show the info book in the middle
        inv.setItem(TITLE_SLOT, infoBook);

        return inv;
    }

    @Override
    public void onClickSlot(int slot) {
        if(slot == TITLE_SLOT) {
            playerData.player.sendMessage(ChatColor.GOLD+"Welcome to FantasyMMO!");
        }
    }
}
