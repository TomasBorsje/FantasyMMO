package tomasborsje.plugin.fantasymmo;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tomasborsje.plugin.fantasymmo.commands.GiveItemCommand;
import tomasborsje.plugin.fantasymmo.commands.SetPlayerLevelCommand;
import tomasborsje.plugin.fantasymmo.commands.SpawnCustomEntityCommand;
import tomasborsje.plugin.fantasymmo.commands.SpawnNPCCommand;
import tomasborsje.plugin.fantasymmo.database.DatabaseConnection;
import tomasborsje.plugin.fantasymmo.enchantments.GlowEnchantment;
import tomasborsje.plugin.fantasymmo.events.*;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

import java.lang.reflect.Field;

public class FantasyMMO extends JavaPlugin {

    public static FantasyMMO Plugin = null;
    public static ServerTickRunner serverTick;
    public static DatabaseConnection databaseConnection;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Enabled FantasyMMO.");

        Plugin = this;

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
        pluginManager.registerEvents(new SlimeSplitListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new PlayerConnectionListener(), this);
        pluginManager.registerEvents(new PlayerInteractPlayerListener(), this);
        pluginManager.registerEvents(new InventoryItemClickListener(), this);
        pluginManager.registerEvents(new InventoryCloseListener(), this);

        Bukkit.getLogger().info("Registered event listeners.");
    }

    private void registerCommands() {
        getCommand("giveitem").setExecutor(new GiveItemCommand());
        getCommand("spawnentity").setExecutor(new SpawnCustomEntityCommand());
        getCommand("setlevel").setExecutor(new SetPlayerLevelCommand());
        getCommand("spawnnpc").setExecutor(new SpawnNPCCommand());

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
