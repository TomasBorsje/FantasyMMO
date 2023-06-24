package tomasborsje.plugin.fantasymmo.core;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import tomasborsje.plugin.fantasymmo.core.enums.CustomDamageType;
import tomasborsje.plugin.fantasymmo.core.enums.EquipType;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IStatsProvider;
import tomasborsje.plugin.fantasymmo.core.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.core.util.StatCalc;

public class PlayerData {
    private final static int VANILLA_MAX_HEALTH = 20;
    private final static int LEVEL_CAP = 100;
    private final static int MONEY_CAP = ItemUtil.Value(100000,0,0); // Gold cap of 100,000 gold
    private final Player player;
    private final String username;
    private int level;
    private int experience;
    public int strength;
    public int intelligence;
    public int maxHealth;
    public int maxMana;
    public int currentHealth;
    public int currentMana;
    public float spellDamageMultiplier;
    public int useCooldown;
    public int defense;
    private int copper;
    private int regenTimer = 0;

    public PlayerData(Player player) {
        this.player = player;
        this.username = player.getDisplayName();

        // TODO: Load persistent stats from DB
        this.level = 1;
        this.experience = 0;

        // Recalc stats
        recalculateStats();
        // Start with max health and mana
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;

        // Show level to player
        player.setLevel(level);
        player.setExp((float)experience / (level * 50));
    }

    public void tick() {
        // Tick each item in inventory if possible, etc
        recalculateStats();

        // Regen health and mana
        regenStats();

        // Clamp stats so they aren't higher than max
        clampStats();

        // Visually display current health using vanilla health bar
        updateVanillaHealthBar();

        if(useCooldown > 0) {
            useCooldown--;
        }

        // Display to player
        showActionBarStats();
    }

    public void gainExperience(int xp) {
        // Don't gain experience if player is max level
        if(level >= LEVEL_CAP) {
            return;
        }

        experience += xp;

        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        // If player has enough experience to level up, level up
        if(experience >= level * 50) {
            experience -= level * 50;
            level++;
            player.sendMessage(ChatColor.YELLOW+""+ChatColor.BOLD+"DING!" +ChatColor.RESET + " " +
                    ChatColor.GREEN + "You leveled up to level " + level + "!");
            player.playSound(player, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            if(level == LEVEL_CAP) {
                player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"CONGRATS!"+ChatColor.RESET + "" + ChatColor.GOLD + " You've reached max level'!");
                experience = 0;
            }
        }
        // Update experience bar
        player.setLevel(level);
        player.setExp((float)experience / (level * 50));
    }

    private void regenStats() {
        // Every second, regen
        if(++regenTimer > 20) {
            regenTimer = 0;

            // Regen 3% of health per second
            heal((int) Math.max(maxHealth*0.03f, 1));

            // Regen 5% of mana per second
            currentMana += Math.max(maxMana*0.05f, 1);
            // Clamp mana
            if(currentMana > maxMana) {
                currentMana = maxMana;
            }
        }
    }

    private void clampStats() {
        // Clamp health
        if(this.currentHealth > maxHealth) {
            this.currentHealth = maxHealth;
        }
        // Clamp mana
        if(this.currentMana > maxMana) {
            this.currentMana = maxMana;
        }
    }

    private void showActionBarStats() {
        // Build action bar message
        String message = "";

        // Health
        message += ChatColor.RED + "❤ " + ChatColor.WHITE + currentHealth + "/" + maxHealth + " ";
        // Shield emoji for defense
        message += ChatColor.GREEN + "🛡 " + ChatColor.WHITE + defense + " ";
        // Mana
        message += ChatColor.BLUE + "⭐ " + ChatColor.WHITE + currentMana + "/" + maxMana + " ";
        // Spell damage multiplier
        message += ChatColor.LIGHT_PURPLE + "⚡ " + ChatColor.WHITE + (int)(spellDamageMultiplier*100) + "%";

        // Send to player
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public void heal(int amount) {
        // Increase current health
        this.currentHealth += amount;
        // Clamp current health to max hp if needed
        if(this.currentHealth > maxHealth) {
            this.currentHealth = maxHealth;
        }
    }

    public void hurt(CustomEntity attacker, int amount, CustomDamageType type) {
        this.currentHealth -= amount;
        if(this.currentHealth <= 0) {
            onDeath();
        }
    }

    public boolean tryConsumeMana(int amount) {
        if(currentMana >= amount) {
            currentMana -= amount;
            return true;
        }
        return false;
    }

    /**
     * Consumes money from this player. Returns true if the player had enough money.
     * @param copper The amount of copper to consume
     * @return True if the player had enough money
     */
    public boolean tryConsumeMoney(int copper) {
        if(this.copper >= copper) {
            this.copper -= copper;
            return true;
        }
        return false;
    }

    /**
     * Adds money to this player. Returns the amount of money that was actually added.
     * @param copper The amount of copper to add
     * @return The amount of copper that was actually added
     */
    public int addMoney(int copper) {
        // Clamp copper to money cap
        if(this.copper + copper > MONEY_CAP) {
            copper = MONEY_CAP - this.copper;
        }
        this.copper += copper;
        return copper;
    }

    public void onDeath() {
        player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"You died!");
        currentHealth = maxHealth;
    }

    public void recalculateStats() {
        resetTransientStats();
        // Calculate equipped stat buffs
        applyHeldItem();
        applyEquippedArmor();

        // Calculate final stats
        calculateFinalStats();
    }

    private void calculateFinalStats() {
        this.maxMana += (int) (intelligence*2.5f);
        this.spellDamageMultiplier += intelligence * 0.75/100; // 75% more spell damage for every 100 intelligence
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
        this.maxHealth = StatCalc.getBaseHealthAtLevel(level);
        this.maxMana = StatCalc.getBaseManaAtLevel(level);
        this.defense = 0;
        this.spellDamageMultiplier = 1;
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
    public boolean isValid() {
        return player.isValid();
    }
    private void updateVanillaHealthBar() {
        // Note we add a minimum health of 0.001 so we can't die on Vanilla's side
        double vanillaHealth = Math.min((double)currentHealth / maxHealth * VANILLA_MAX_HEALTH + 0.001, VANILLA_MAX_HEALTH);
        player.setHealth(vanillaHealth);
    }
}
