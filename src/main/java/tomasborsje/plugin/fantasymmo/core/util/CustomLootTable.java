package tomasborsje.plugin.fantasymmo.core.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;

import java.util.ArrayList;

public class CustomLootTable {
    public static final CustomLootTable EMPTY = new CustomLootTable();
    final ArrayList<LootTableEntry> entries = new ArrayList<>();

    /**
     * Creates an empty loot table.
     */
    private CustomLootTable() { }

    /**
     * Create a new loot table with an item.
     * @param item The item to add.
     * @param chance The chance of the item dropping.
     */
    public CustomLootTable(ICustomItem item, double chance) {
        this.entries.add(new LootTableEntry(item, chance));
    }

    /**
     * Add another entry to the loot table.
     * @param item The item to add.
     * @param chance The chance of the item dropping.
     * @return The loot table instance.
     */
    public CustomLootTable addEntry(ICustomItem item, double chance) {
        this.entries.add(new LootTableEntry(item, chance));
        return this;
    }

    /**
     * Return all the items dropped by this loot table.
     * @param player The player to roll for.
     * @return An array of items dropped.
     */
    public ItemStack[] roll(Player player) {
        ArrayList<ItemStack> items = new ArrayList<>();
        for(LootTableEntry entry : this.entries) {
            if(Math.random() < entry.chance) {
                items.add(entry.item.createStack());
            }
        }
        return items.toArray(new ItemStack[0]);
    }
}
class LootTableEntry {
    public final ICustomItem item;
    public final double chance;
    public LootTableEntry(ICustomItem item, double chance) {
        this.item = item;
        this.chance = chance;
    }
}
