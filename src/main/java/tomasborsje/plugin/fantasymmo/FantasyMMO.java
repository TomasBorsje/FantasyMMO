package tomasborsje.plugin.fantasymmo;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tomasborsje.plugin.fantasymmo.commands.GiveItemCommand;
import tomasborsje.plugin.fantasymmo.commands.SpawnCustomEntityCommand;
import tomasborsje.plugin.fantasymmo.enchantments.GlowEnchantment;
import tomasborsje.plugin.fantasymmo.events.*;

import java.lang.reflect.Field;

public class FantasyMMO extends JavaPlugin {

    public static FantasyMMO Plugin = null;
    public static ServerTickRunner serverTick;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Enabled FantasyMMO.");

        Plugin = this;

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
        pluginManager.registerEvents(new EntityHurtListener(), this);
        pluginManager.registerEvents(new SlimeSplitListener(), this);

        Bukkit.getLogger().info("Registered event listeners.");
    }

    private void registerCommands() {
        getCommand("giveitem").setExecutor(new GiveItemCommand());
        getCommand("spawnentity").setExecutor(new SpawnCustomEntityCommand());

        Bukkit.getLogger().info("Registered commands.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Disabled FantasyMMO.");
    }
}
