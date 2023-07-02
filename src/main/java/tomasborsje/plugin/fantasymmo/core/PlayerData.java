package tomasborsje.plugin.fantasymmo.core;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import tomasborsje.plugin.fantasymmo.core.enums.CustomDamageType;
import tomasborsje.plugin.fantasymmo.core.enums.EquipType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.IBuffable;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IStatProvider;
import tomasborsje.plugin.fantasymmo.quests.KillForestSlimesQuest;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.core.util.StatCalc;
import tomasborsje.plugin.fantasymmo.guis.CustomGUIInstance;
import tomasborsje.plugin.fantasymmo.quests.AbstractQuestInstance;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PlayerData implements IBuffable {
    private final static int VANILLA_MAX_HEALTH = 20;
    private final static int LEVEL_CAP = 100;
    private final static int MONEY_CAP = ItemUtil.Value(100000,0,0); // Gold cap of 100,000 gold
    private final static int COMBAT_COOLDOWN = 20 * 10; // 10 seconds
    private final static float DEFAULT_MOVESPEED = 0.2f;
    public final Player player;
    private final String username;
    private long ticksPlayed = 0;
    private int level;
    private int experience;
    public int strength;
    public int intelligence;
    public int maxHealth;
    public int maxMana;
    public int currentHealth;
    public int currentMana;
    public float spellDamageMultiplier; // Spell damage multiplier (1.0 = 100%)
    private float healthRegenMultiplier; // Health regen per tick multiplier
    public float manaRegenMultiplier; // Mana regen per tick multiplier
    public int healthRegenFlat; // Health regen per tick increase above natural
    public int manaRegenFlat; // Mana regen per tick increase above natural
    public float moveSpeedMultiplier;
    public int useCooldown;
    public int defense;
    private int money; // The player's money, in copper
    private int regenTimer = 0;
    private int timeSinceLastCombat;
    private PlayerScoreboard scoreboard;
    private PlayerBossBar bossBar;
    public List<String> knownRecipeIds = new ArrayList<>(); // Stores IDs of recipes the player knows
    private @Nullable CustomGUIInstance currentGUI = null; // The currently open GUI
    public final ArrayList<Buff> buffs = new ArrayList<>(); // List of buffs currently active on the player
    public final ArrayList<AbstractQuestInstance> activeQuests = new ArrayList<>(); // List of active quests

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

        this.timeSinceLastCombat = COMBAT_COOLDOWN;

        // Show level to player
        updateExpBar();

        addQuest(new KillForestSlimesQuest(this));

        scoreboard = new PlayerScoreboard(this);
        bossBar = new PlayerBossBar(this);
    }

    /**
     * Gives item(s) to the player. If they don't have room in their inventory,
     * they will receive the leftover items in the mail.
     * @param items The item stacks to give
     */
    public void giveItem(ItemStack... items) {
        // Print a received message for each item
        for (ItemStack item : items) {
            if(item.hasItemMeta()) {
                player.sendMessage(ChatColor.GRAY + "You received " + item.getAmount() + "x " + item.getItemMeta().getDisplayName() + ChatColor.GRAY + ".");
            }
        }

        // Add items to the player's inventory and get the leftover items
        var leftoverItems = player.getInventory().addItem(items);

        if(!leftoverItems.isEmpty()) {
            // TODO: Send item in mail
            return;
        }
    }

    /**
     * Adds a quest to the player's active quest list.
     * @param quest The quest to add
     */
    public void addQuest(AbstractQuestInstance quest) {
        // If we don't have this quest yet, add it
        if(activeQuests.stream().noneMatch(q -> q.getCustomId().equals(quest.getCustomId()))) {
            activeQuests.add(quest);
        }
    }

    public void openGUI(CustomGUIInstance gui) {
        if(currentGUI != null) {
            closeGUI();
        }
        gui.show();
        currentGUI = gui;
    }

    @Nullable
    public CustomGUIInstance getCurrentGUI() {
        return currentGUI;
    }

    public void closeGUI() {
        this.currentGUI = null;
        // TODO Figure out how to force close GUI (packet?)
    }

    public void tick() {
        ticksPlayed++;
        // Tick each item in inventory if possible, etc
        recalculateStats();

        // Tick combat
        timeSinceLastCombat++;

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
        updateVanillaHealthBarDisplay();

        // Update scoreboard and boss bar every 5 ticks
        if(ticksPlayed % 2 == 0) {
            // Update scoreboard to show money, quests, etc.
            scoreboard.render();
            bossBar.render();
        }

        // Update xp bar to show level and progress
        // TODO: Don't do this every tick if we can avoid it, only on xp gain
        updateExpBar();

        // Reduce player use cooldown if on cooldown
        if(useCooldown > 0) {
            useCooldown--;
        }

        // Display to player
        showActionBarStats();
    }

    /**
     * Progresses each quest the player has using the killed entity, if applicable.
     * @param killed The entity that was killed
     */
    public void registerKillForQuests(CustomEntity killed) {
        // Attempt progress on each quest with this killed enemy
        for (AbstractQuestInstance quest : activeQuests) {
            quest.registerKill(killed);
        }
    }

    public void fillHealthAndMana() {
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
        updateVanillaHealthBarDisplay();
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
                Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "CONGRATS! "+ChatColor.YELLOW+""+ChatColor.BOLD+username+" has reached Level 100!");
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
        // Much less regen if the player is in combat
        if(++regenTimer > 20) {
            regenTimer = 0;

            // 3% hp regen out of combat, 3% mana regen at all times
            float healthRegenAmount = 0.03f;
            float manaRegenAmount = 0.03f;

            // Health regen reduced to 10% in combat
            if(isInCombat()) {
                healthRegenAmount *= 0.1f;
            }

            // Regen health based on flat amount and multiplier
            heal((int) ((maxHealth*healthRegenAmount+healthRegenFlat)*healthRegenMultiplier));

            // Regen mana based on flat amount and multiplier
            currentMana += (int)((maxMana*manaRegenAmount+manaRegenFlat)*manaRegenMultiplier);
        }
    }

    /**
     * Clamps health and mana to their maximum values.
     */
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

    /**
     * Displays the player's health, mana, armor, and spell damage multiplier in the action bar.
     */
    private void showActionBarStats() {
        boolean inCombat = isInCombat();
        // Build action bar message, with a red exclamation mark if in combat
        String message = inCombat ? ChatColor.RED + "!! " : "";

        // Health
        message += ChatColor.RED + "❤ " + ChatColor.WHITE + StatCalc.formatInt(currentHealth) + "/" + StatCalc.formatInt(maxHealth) + " ";
        // Shield emoji for defense
        message += ChatColor.GREEN + "🛡 " + ChatColor.WHITE + StatCalc.formatInt(defense) + " ";
        // Mana
        message += ChatColor.BLUE + "⭐ " + ChatColor.WHITE + StatCalc.formatInt(currentMana) + "/" + StatCalc.formatInt(maxMana) + " ";
        // Spell damage multiplier
        message += ChatColor.LIGHT_PURPLE + "⚡ " + ChatColor.WHITE + (int)(spellDamageMultiplier*100) + "%";

        if(inCombat) {
            // Show combat status
            message += ChatColor.RED + " !!";
        }

        // Send to player
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    /**
     * Heals a player, increasing their health.
     * @param amount The amount of health to heal the player
     */
    public void heal(int amount) {
        // Increase current health
        this.currentHealth += amount;
        // Clamp current health to max hp if needed
        if(this.currentHealth > maxHealth) {
            this.currentHealth = maxHealth;
        }
    }

    /**
     * Damages a player, reducing their health.
     * Marks them as being in combat if the attacker is not null.
     * @param attacker The entity that attacked the player
     * @param amount The amount of damage to deal to the player
     * @param type The type of damage to deal to the player
     */
    public void hurt(@Nullable CustomEntity attacker, int amount, CustomDamageType type) {
        if(attacker != null) {
            markCombat();
        }
        this.currentHealth -= amount;
        if(this.currentHealth <= 0) {
            onDeath();
        }
    }

    /**
     * Returns if a player is in combat.
     * This is if they attacked or were hurt in the last 10 seconds.
     * @return True if the player is in combat
     */
    public boolean isInCombat() {
        return timeSinceLastCombat < COMBAT_COOLDOWN;
    }

    /**
     * Marks a player as in combat.
     */
    public void markCombat() {
        timeSinceLastCombat = 0;
    }

    /**
     * Tries to consume mana from this player. Returns true if mana was consumed.
     * @param amount The amount of mana to consume
     * @return True if mana was consumed
     */
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
        if(this.money >= copper) {
            this.money -= copper;
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
        if(this.money + copper > MONEY_CAP) {
            copper = MONEY_CAP - this.money;
        }
        this.money += copper;
        return copper;
    }

    /**
     * Returns the amount of money this player has, in copper.
     * @return The amount of money this player has, in copper
     */
    public int getMoney() {
        return money;
    }

    /**
     * Called when the player dies.
     * Respawns the player and applies penalties.
     */
    public void onDeath() {
        player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"You died!");
        // TODO: Lose money and send player to their spawn
        currentHealth = maxHealth;
    }

    /**
     * Calculates the player's stats for this tick.
     */
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

    /**
     * Applies any stat changes from the player's buffs and debuffs.
     * Called every tick.
     */
    private void applyStatBuffs() {
        // Apply any stat changes from buffs
        for(Buff buff : buffs) {
            buff.modifyStats(this);
        }
    }

    /**
     * Calculate mana, spell damage multipliers, etc. from the player's primary stats.
     */
    private void calculateFinalStats() {
        this.maxMana += (int) (intelligence*2.5f);
        this.spellDamageMultiplier += intelligence * 0.75/100; // 75% more spell damage for every 100 intelligence
        // Set move speed
        player.setWalkSpeed(DEFAULT_MOVESPEED * moveSpeedMultiplier);
    }

    /**
     * Applies the stats from the player's equipped armor for this tick.
     */
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
            if(customItem instanceof IStatProvider statsProvider && statsProvider.getEquipType() == EquipType.ARMOUR) {
                // Cast to IStatsProvider and apply stats
                statsProvider.applyStats(this);
            }
        }
    }

    /**
     * Applies the stats from the player's held item for this tick.
     */
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
        if(customItem instanceof IStatProvider statsProvider && statsProvider.getEquipType() == EquipType.HELD) {
            // Cast to IStatsProvider and apply stats
            statsProvider.applyStats(this);
        }
    }

    /**
     * Resets all transient stats to their default values for this tick.
     */
    private void resetTransientStats() {
        // Reset all stats to defaults so we can recalculate
        this.strength = 0;
        this.intelligence = 0;
        this.maxHealth = StatCalc.getBaseHealthAtLevel(level);
        this.maxMana = StatCalc.getBaseManaAtLevel(level);
        this.defense = 0;
        this.spellDamageMultiplier = 1;
        this.healthRegenMultiplier = 1;
        this.manaRegenMultiplier = 1;
        this.moveSpeedMultiplier = 1;
        this.healthRegenFlat = 1; //  We regen 1 health flat per tick by default, as sometimes 3% of max hp is <1
        this.manaRegenFlat = 1; // Ditto for mana
    }
    public String getUsername() {
        return username;
    }
    public boolean isValid() {
        return player.isValid();
    }
    private void updateVanillaHealthBarDisplay() {
        // Note we add a minimum health of 0.001 so we can't die on Vanilla's side
        double vanillaHealth = Math.min((double)currentHealth / maxHealth * VANILLA_MAX_HEALTH + 0.001, VANILLA_MAX_HEALTH);
        player.setHealth(vanillaHealth);
    }

    /**
     * Adds a buff to the player.
     * @param newBuff The buff to add
     */
    @Override
    public void addBuff(Buff newBuff) {
        // Check if an instance of the buff already exists first (to stack, refresh, etc.)
        for(Buff existingBuff : buffs) {
            if(existingBuff.getClass().equals(newBuff.getClass())) {
                // Buff already exists, don't add it again
                // Instead refresh duration if the new buff has a longer duration than what is left
                if(existingBuff.currentStacks+1<=existingBuff.maxStacks) {
                    existingBuff.currentStacks++;
                }
                if(existingBuff.refreshOnApply && existingBuff.ticksLeft < newBuff.duration) {
                    existingBuff.ticksLeft = newBuff.duration;
                    existingBuff.onRefresh(this);
                }
                return;
            }
        }

        // No existing instance, just add a new one
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

    @Override
    public String toString() {
        return "PlayerData{" +
                "strength=" + strength +
                ", intelligence=" + intelligence +
                ", maxHealth=" + maxHealth +
                ", defense=" + defense +
                '}';
    }
}
