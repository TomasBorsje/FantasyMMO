package tomasborsje.plugin.fantasymmo.core;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import tomasborsje.plugin.fantasymmo.core.enums.CustomDamageType;
import tomasborsje.plugin.fantasymmo.core.enums.EquipType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.IBuffable;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IStatsProvider;
import tomasborsje.plugin.fantasymmo.core.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.core.util.StatCalc;
import tomasborsje.plugin.fantasymmo.guis.CustomGUIInstance;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class PlayerData implements IBuffable {
    private final static int VANILLA_MAX_HEALTH = 20;
    private final static int LEVEL_CAP = 100;
    private final static int MONEY_CAP = ItemUtil.Value(100000,0,0); // Gold cap of 100,000 gold
    public final Player player;
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
    private @Nullable CustomGUIInstance currentGUI = null;
    public final ArrayList<Buff> buffs = new ArrayList<>();

    public PlayerData(Player player) {
        this.player = player;
        this.username = player.getName();

        this.level = 1;
        this.experience = 0;

        // Recalc stats
        recalculateStats();

        // Start with max health and mana
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;

        // Show level to player
        player.setLevel(level);
        if(level == LEVEL_CAP) {
            player.setExp(0.999f);
        }
        else {
            player.setExp((float)experience / (level * 50));
        }

        initScoreboard();
    }

    public void openGUI(CustomGUIInstance gui) {
        if(currentGUI != null) {
            closeGUI();
        }
        currentGUI = gui;
        currentGUI.show();
    }

    public CustomGUIInstance getCurrentGUI() {
        return currentGUI;
    }

    public void closeGUI() {
        this.currentGUI = null;
        // TODO Figure out how to force close GUI (packet?)
    }
    private void initScoreboard() {
        ServerPlayer nmsPlayer = ((CraftPlayer)player).getHandle();
        // TODO
    }
    private void updateScoreboard() {

    }

    public void tick() {
        // Tick each item in inventory if possible, etc
        recalculateStats();

        // Regen health and mana
        regenStats();

        // Tick buffs
        for (int i = 0; i < buffs.size(); i++) {
            Buff buff = buffs.get(i);
            // Tick buff
            buff.tick(this);
            // Remove buff if expired
            if(buff.isExpired()) {
                buff.onRemove(this);
                buffs.remove(i);
                i--;
            }
        }

        // Clamp stats so they aren't higher than max
        clampStats();

        // Visually display current health using vanilla health bar
        updateVanillaHealthBar();

        // Update scoreboard
        updateScoreboard();

        // Update xp bar to show level and progress
        // TODO: Don't do this every tick if we can avoid it...
        updateExpBar();

        if(useCooldown > 0) {
            useCooldown--;
        }

        // Display to player
        showActionBarStats();
    }

    public void fillHealthAndMana() {
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
        updateVanillaHealthBar();
    }

    public void setLevel(int level) {
        this.level = level;
        player.setLevel(level);
        player.setExp((float)experience / (level * 50));
    }

    public void setExperience(int experience) {
        this.experience = experience;
        player.setExp((float)experience / (level * 50));
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    /**
     * Gives items to a player. Any leftover items are stored in lost and found.
     * Returns true if any items are leftover and couldn't be added.
     * @param stacks The items to give to the player
     * @return True if any items are leftover and couldn't be added
     */
    public boolean giveItems(boolean displayChatNotif, @Nullable CustomEntity source, ItemStack... stacks) {
        if(displayChatNotif) {
            // Display a chat message showing any loot above common rarity
            for(ItemStack stack : stacks) {
                ICustomItem item = ItemUtil.GetAsCustomItem(stack);
                Rarity rarity = item.getRarity();
                if(rarity == Rarity.COMMON || rarity == Rarity.JUNK) {
                    continue;
                }

                String message = rarity.getColor() + "" + ChatColor.BOLD + rarity.name() + " DROP! " + ChatColor.RESET
                        + ChatColor.WHITE;
                if(source != null) {
                    message += source.name + " dropped "; // Add entity source if applicable
                }
                message += rarity.getColor() + stack.getItemMeta().getDisplayName() + "!"; // Add item name

                player.sendMessage(message);
            }
        }

        // Add stacks to the player's inventory
        var leftover = player.getInventory().addItem(stacks);

        // TODO: Add leftover stacks to lost and found or something
        return !leftover.isEmpty();
    }

    public void gainExperience(int xp) {
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        // Don't gain experience if player is max level
        if(level >= LEVEL_CAP) {
            return;
        }

        experience += xp;

        // If player has enough experience to level up, level up
        if(experience >= level * 50) {
            experience -= level * 50;
            level++;
            player.sendMessage(ChatColor.YELLOW+""+ChatColor.BOLD+"DING!" +ChatColor.RESET + " " +
                    ChatColor.YELLOW + "You've reached Level " + level + "!");
            player.playSound(player, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            if(level == LEVEL_CAP) {
                player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"CONGRATS! You've reached max level!");
                experience = 0;
            }
        }
        // Update experience bar
        updateExpBar();
    }

    private void updateExpBar() {
        player.setExp(level == LEVEL_CAP ? 0.999f : (float)experience / StatCalc.getExperienceForLevel(level));
        player.setLevel(level);
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
        message += ChatColor.RED + "❤ " + ChatColor.WHITE + StatCalc.formatInt(currentHealth) + "/" + StatCalc.formatInt(maxHealth) + " ";
        // Shield emoji for defense
        message += ChatColor.GREEN + "🛡 " + ChatColor.WHITE + StatCalc.formatInt(defense) + " ";
        // Mana
        message += ChatColor.BLUE + "⭐ " + ChatColor.WHITE + StatCalc.formatInt(currentMana) + "/" + StatCalc.formatInt(maxMana) + " ";
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

    public int getMoney() {
        return copper;
    }

    public void onDeath() {
        player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"You died!");
        // TODO: Lose money and send player to their spawn
        currentHealth = maxHealth;
    }

    public void recalculateStats() {
        resetTransientStats();
        // Calculate equipped stat buffs
        applyHeldItem();
        applyEquippedArmor();

        // Apply buffs and debuffs
        applyStatBuffs();

        // Calculate final stats
        calculateFinalStats();
    }

    private void applyStatBuffs() {
        // Apply any stat changes from buffs
        for(Buff buff : buffs) {
            buff.modifyStats(this);
        }
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

    @Override
    public void addBuff(Buff newBuff) {
        // If the buff isn't stackable, check the buff doesn't already exist first
        if(!newBuff.isStackable) {
            for(Buff existingBuff : buffs) {
                if(existingBuff.getClass().equals(newBuff.getClass())) {
                    // Buff already exists, don't add it again
                    // Instead refresh duration if the new buff has a longer duration than what is left
                    if(existingBuff.ticksLeft < newBuff.duration) {
                        existingBuff.ticksLeft = newBuff.duration;
                    }
                    return;
                }
            }
        }
        // Otherwise, apply buff
        newBuff.onApply(this);
        this.buffs.add(newBuff);
    }

    @Override
    public void removeAllBuffs() {
        for(Buff buff : buffs) {
            buff.onRemove(this);
        }
        this.buffs.clear();
    }

    public boolean hasGUIOpen() {
        return this.currentGUI != null;
    }
}
