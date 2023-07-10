package tomasborsje.plugin.fantasymmo.guis;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.util.GUIUtil;
import tomasborsje.plugin.fantasymmo.core.util.TooltipUtil;
import tomasborsje.plugin.fantasymmo.quests.AbstractQuestInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestPromptGUI extends CustomGUI {
    private static final int ACCEPT_SLOT = GUIUtil.GetSlot(4, 5);
    private static final int DECLINE_SLOT = GUIUtil.GetSlot(6, 5);
    private static final int DESCRIPTION_SLOT = GUIUtil.GetSlot(5, 2);
    private static final int REWARDS_SLOT = GUIUtil.GetSlot(5, 3);
    private final AbstractQuestInstance quest;
    public QuestPromptGUI(PlayerData playerData, AbstractQuestInstance quest, String title) {
        super(playerData, 54, ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+title);
        this.quest = quest;
    }

    @Override
    protected Inventory renderInventory() {
        Inventory inv = super.renderInventory();

        // Show accept button with custom name
        ItemStack accept = new ItemStack(Material.LIME_CONCRETE, 1);
        ItemMeta acceptMeta = accept.getItemMeta();
        acceptMeta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"ACCEPT");
        accept.setItemMeta(acceptMeta);
        inv.setItem(ACCEPT_SLOT, accept);

        // Show decline button with custom name
        ItemStack decline = new ItemStack(Material.RED_CONCRETE, 1);
        ItemMeta declineMeta = decline.getItemMeta();
        declineMeta.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+"DECLINE");
        decline.setItemMeta(declineMeta);
        inv.setItem(DECLINE_SLOT, decline);

        // Show the quest description as a book
        ItemStack book = new ItemStack(Material.BOOK, 1);
        ItemMeta m = book.getItemMeta();
        // Split description into newlines
        String[] description = quest.getDescription().split("\n");
        List<String> lore = Arrays.stream(description).map(s -> ChatColor.WHITE+s).collect(Collectors.toList());
        lore.add(0, "");

        // Add objective list
        lore.add("");
        lore.add(ChatColor.YELLOW+"Objectives:");
        lore.add("");
        for(int i = 0; i < quest.objectives.length; i++) {
            lore.add(ChatColor.WHITE+""+(i+1)+". "+quest.objectives[i].getStatusString());
        }
        // Make each line white and add it to the lore
        m.setLore(lore);
        m.setDisplayName(ChatColor.YELLOW +""+ ChatColor.UNDERLINE + "Quest" + ChatColor.RESET + ChatColor.YELLOW +": "+ quest.getName());

        // Set description
        book.setItemMeta(m);
        inv.setItem(DESCRIPTION_SLOT, book);

        // Add quest reward indicator
        ItemStack rewardDisplay = new ItemStack(Material.GOLD_NUGGET, 1);
        ItemMeta rewardMeta = rewardDisplay.getItemMeta();
        rewardMeta.setDisplayName(ChatColor.YELLOW+"Rewards:");
        List<String> rewardLore = new ArrayList<String>(2);
        rewardLore.add(TooltipUtil.GetValueString(quest.getMoneyReward()));
        rewardLore.add(ChatColor.GREEN + "" + quest.getXpReward() + " XP");
        // Add item rewards
        if(quest.getItemRewards() != null) {
            rewardLore.add("");
            rewardLore.add(ChatColor.YELLOW+"Items:");
            for(ItemStack item : quest.getItemRewards()) {
                rewardLore.add(ChatColor.WHITE+"- "+item.getAmount()+"x "+item.getItemMeta().getDisplayName());
            }
        }
        rewardMeta.setLore(rewardLore);
        rewardDisplay.setItemMeta(rewardMeta);
        inv.setItem(REWARDS_SLOT, rewardDisplay);
        return inv;
    }

    @Override
    public void onClickSlot(int slot) {
        if(slot == ACCEPT_SLOT) {
            // Accept the quest
            playerData.addQuest(quest);
            playerData.closeGUI();
            playerData.player.sendMessage(ChatColor.WHITE+"You accepted the quest: " +ChatColor.YELLOW+ quest.getName());
        } else if(slot == DECLINE_SLOT) {
            // Decline the quest
            playerData.closeGUI();
        }
    }
}
