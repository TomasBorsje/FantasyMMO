package tomasborsje.plugin.fantasymmo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tomasborsje.plugin.fantasymmo.commands.*;
import tomasborsje.plugin.fantasymmo.database.DatabaseConnection;
import tomasborsje.plugin.fantasymmo.enchantments.GlowEnchantment;
import tomasborsje.plugin.fantasymmo.events.*;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

import java.io.File;
import java.lang.reflect.Field;

public class FantasyMMO extends JavaPlugin {

    public static FantasyMMO Plugin = null;
    public static ServerTickRunner serverTick;
    public static DatabaseConnection databaseConnection;
    public static File dataFolder;
    public static String motd = "";
    public static final String version = "0.0.1";

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Enabled FantasyMMO.");

        Plugin = this;

        // Build MOTD for server list
        buildMotd();

        // Store plugin data folder
        dataFolder = getDataFolder();

        // Create data folder if it doesn't exist
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        // Connect to database
        databaseConnection = new DatabaseConnection();

        // Register enchantments
        registerEnchantments();

        // Register commands
        registerCommands();

        // Register event listeners
        registerEventListeners();

        serverTick = new ServerTickRunner();
        serverTick.runTaskTimer(this,0,1);
    }

    private void buildMotd() {
        // Format: FantasyMMO <version> - BETA
        motd = ChatColor.YELLOW+""+ChatColor.BOLD+"Fantasy"+ChatColor.GOLD+""+ChatColor.BOLD+"MMO "+ChatColor.RESET
                + ChatColor.WHITE+""+ChatColor.BOLD+version + " - "+ChatColor.BLUE+ChatColor.BOLD+"BETA";
        motd += "\n"+ChatColor.GRAY+"-- TEST SERVER --";
    }

    private void registerEnchantments() {
        // Enable accepting of new enchantments with reflection
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Register enchantments
        Enchantment.registerEnchantment(new GlowEnchantment(new NamespacedKey(this, "glow")));
    }

    private void registerEventListeners() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new CustomItemUseListener(), this);
        pluginManager.registerEvents(new WorldLoadListener(), this);
        pluginManager.registerEvents(new EntityHurtEntityListener(), this);
        pluginManager.registerEvents(new EntityReceiveDamageListener(), this);
        pluginManager.registerEvents(new SlimeSplitListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new PlayerConnectionListener(), this);
        pluginManager.registerEvents(new PlayerInteractPlayerListener(), this);
        pluginManager.registerEvents(new InventoryOpenListener(), this);
        pluginManager.registerEvents(new InventoryCloseListener(), this);
        pluginManager.registerEvents(new InventoryItemClickListener(), this);
        pluginManager.registerEvents(new FoodLevelChangeListener(), this);
        pluginManager.registerEvents(new PlayerCraftListener(), this);
        pluginManager.registerEvents(new ServerListPingListener(), this);
        pluginManager.registerEvents(new PlayerDropItemListener(), this);

        Bukkit.getLogger().info("Registered event listeners.");
    }

    private void registerCommands() {
        getCommand("giveitem").setExecutor(new GiveItemCommand());
        getCommand("spawnentity").setExecutor(new SpawnCustomEntityCommand());
        getCommand("setlevel").setExecutor(new SetPlayerLevelCommand());
        getCommand("spawnnpc").setExecutor(new SpawnNPCCommand());
        getCommand("craft").setExecutor(new OpenCraftingMenuCommand());
        getCommand("learnrecipe").setExecutor(new LearnRecipeCommand());

        Bukkit.getLogger().info("Registered commands.");
    }

    @Override
    public void onDisable() {
        // Save player data
        PlayerHandler.instance.saveAllPlayerData();
        // Log
        Bukkit.getLogger().info("Disabled FantasyMMO.");
    }
}
