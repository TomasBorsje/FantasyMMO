package tomasborsje.plugin.fantasymmo.guis;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomasborsje.plugin.fantasymmo.core.CustomNPC;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.util.GUIUtil;
import tomasborsje.plugin.fantasymmo.core.util.TooltipUtil;
import tomasborsje.plugin.fantasymmo.quests.AbstractQuestInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestTurnInGUI extends CustomGUI {
    private static final int TURN_IN_SLOT = GUIUtil.GetSlot(5, 5);
    private static final int TURN_IN_SLOT_LEFT = GUIUtil.GetSlot(4, 5);
    private static final int TURN_IN_SLOT_RIGHT = GUIUtil.GetSlot(6, 5);
    private static final int DESCRIPTION_SLOT = GUIUtil.GetSlot(5, 2);
    private static final int REWARDS_SLOT = GUIUtil.GetSlot(5, 3);
    private final AbstractQuestInstance quest;
    private final CustomNPC npc;

    public QuestTurnInGUI(PlayerData playerData, AbstractQuestInstance quest, CustomNPC npc) {
        super(playerData, 54, quest.getName());
        this.quest = quest;
        this.npc = npc;
    }

    @Override
    protected Inventory renderInventory() {
        Inventory inv = super.renderInventory();

        // Show turn in button as 3 different gold blocks
        ItemStack accept = new ItemStack(Material.GOLD_BLOCK, 1);
        ItemMeta acceptMeta = accept.getItemMeta();
        acceptMeta.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"TURN IN");
        accept.setItemMeta(acceptMeta);
        inv.setItem(TURN_IN_SLOT, accept);
        inv.setItem(TURN_IN_SLOT_LEFT, accept);
        inv.setItem(TURN_IN_SLOT_RIGHT, accept);

        // Show the quest description as a book
        ItemStack book = new ItemStack(Material.BOOK, 1);
        ItemMeta m = book.getItemMeta();
        // Split description into newlines
        String[] description = quest.getCompletionDescription().split("\n");
        List<String> lore = Arrays.stream(description).map(s -> ChatColor.WHITE+s).collect(Collectors.toList());
        lore.add(0, "");

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
        if(slot == TURN_IN_SLOT || slot == TURN_IN_SLOT_LEFT || slot == TURN_IN_SLOT_RIGHT) {
            // Try to hand in the quest
            if(playerData.registerNPCInteractForQuests(this.npc)) {
                // If we make progress, close the GUI
                playerData.closeGUI();
            }
        }
    }
}
