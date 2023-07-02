package tomasborsje.plugin.fantasymmo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.registries.EntityRegistry;
import tomasborsje.plugin.fantasymmo.handlers.EntityHandler;

public class SpawnCustomEntityCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player) {

            // Get the entity id from the first argument
            String customId = args[0].toUpperCase();

            // Get the entity from our EntityRegistry
            if(!EntityRegistry.ENTITIES.exists(customId)) {
                player.sendMessage(ChatColor.RED+"No entity registered with ID "+customId);
                return true;
            }

            // Spawn with custom ID
            EntityHandler.instance.spawnEntity(player.getLocation(), customId);

            // Send message
            player.sendMessage(ChatColor.GREEN+"Spawned "+ChatColor.WHITE+customId+"!");

            return true;
        }
        return false;
    }
}
