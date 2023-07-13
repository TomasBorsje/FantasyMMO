package tomasborsje.plugin.fantasymmo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;
import tomasborsje.plugin.fantasymmo.registries.RecipeRegistry;

public class LearnRecipeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player player && args.length==1) {
            PlayerData playerData = PlayerHandler.instance.getPlayerData(player);
            String recipeId = args[0];

            if(!RecipeRegistry.Exists(recipeId)) {
                player.sendMessage(ChatColor.RED+"Recipe "+recipeId+" does not exist!");
                return true;
            }

            // Open crafting menu
            playerData.knownRecipeIds.add(recipeId);
            player.sendMessage(ChatColor.GREEN+"You learned recipe "+recipeId+"!");
            return true;
        }
        return false;
    }
}
