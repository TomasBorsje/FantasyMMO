package tomasborsje.plugin.fantasymmo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.registries.EntityRegistry;
import tomasborsje.plugin.fantasymmo.handlers.EntityHandler;

public class SpawnNPCCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player) {

            // Get the entity id from the first argument
            String npcId = args[0].toUpperCase();

            // Get the entity from our EntityRegistry
            if(!EntityRegistry.NPCS.exists(npcId)) {
                player.sendMessage(ChatColor.RED+"No NPC registered with ID "+npcId);
                return true;
            }

            // Spawn with custom ID
            EntityHandler.instance.spawnNPC(player.getLocation(), npcId);

            // Send message
            player.sendMessage(ChatColor.GREEN+"Spawned "+ChatColor.WHITE+npcId+"!");

            return true;
        }
        return false;
    }
}
