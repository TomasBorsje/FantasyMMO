package tomasborsje.plugin.fantasymmo.core;

import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import tomasborsje.plugin.fantasymmo.core.enums.EquipType;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IStatsProvider;
import tomasborsje.plugin.fantasymmo.core.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;

public class PlayerData {
    private final static int DEFAULT_MAX_HEALTH = 20;
    private final Player player;
    private final String username;
    private int level;
    private int experience;
    public int strength;
    public int intelligence;
    public int maxHealth;
    public int currentHealth;
    public int defense;
    private int copper;

    public PlayerData(Player player) {
        this.player = player;
        this.username = player.getDisplayName();
    }

    public void recalculateStats() {
        resetTransientStats();

        // Calculate equipped stat buffs
        applyHeldItem();
        applyEquippedArmor();
    }

    private void applyEquippedArmor() {
        PlayerInventory playerInv = player.getInventory();
        // Calculate held item stats
        ItemStack[] equipment = { playerInv.getHelmet(), playerInv.getChestplate(),
                                  playerInv.getLeggings(), playerInv.getBoots() };

        // Calculate for each equipped armor item
        for(int i = 0; i < equipment.length; i++) {
            // Get item's custom ID
            net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(equipment[i]);
            // Check it is actually a custom item
            if(nmsStack.isEmpty() || !ItemUtil.IsCustomItem(nmsStack)) { continue; }

            // Get the custom item id
            String itemId = nmsStack.getTag().getString("ITEM_ID");

            // Get the custom item from the registry
            ICustomItem customItem = ItemRegistry.ITEMS.get(itemId);

            // If it provides stats when equipped as armour, apply those stats
            if(customItem instanceof IStatsProvider statsProvider && statsProvider.getEquipType() == EquipType.ARMOUR) {
                // Cast to IStatsProvider and apply stats
                statsProvider.applyStats(this);
            }
        }
    }

    private void applyHeldItem() {
        // Calculate held item stats
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        // Get item's custom ID
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(heldItem);
        // Check it is actually a custom item
        if(nmsStack.isEmpty() || !ItemUtil.IsCustomItem(nmsStack)) { return; }

        // Get the custom item id
        String itemId = nmsStack.getTag().getString("ITEM_ID");

        // Get the custom item from the registry
        ICustomItem customItem = ItemRegistry.ITEMS.get(itemId);

        // If it provides stats when held, apply those stats
        if(customItem instanceof IStatsProvider statsProvider && statsProvider.getEquipType() == EquipType.HELD) {
            // Cast to IStatsProvider and apply stats
            statsProvider.applyStats(this);
        }
    }

    private void resetTransientStats() {
        // Reset all stats to defaults so we can recalculate
        this.strength = 0;
        this.intelligence = 0;
        this.maxHealth = DEFAULT_MAX_HEALTH;
        // Clamp current health to max hp if needed
        if(this.currentHealth > maxHealth) {
            this.currentHealth = maxHealth;
        }
        this.defense = 0;
    }

    @Override
    public String toString() {
        return "PlayerData{" +
                "strength=" + strength +
                ", intelligence=" + intelligence +
                ", maxHealth=" + maxHealth +
                ", defense=" + defense +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDefense() {
        return defense;
    }

    public int getCopper() {
        return copper;
    }

    public void tick() {
        // Tick each item in inventory if possible, etc
    }
}
