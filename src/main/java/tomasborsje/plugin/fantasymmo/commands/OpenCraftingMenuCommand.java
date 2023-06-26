package tomasborsje.plugin.fantasymmo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.guis.CraftingGUI;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

public class OpenCraftingMenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player player) {
            PlayerData playerData = PlayerHandler.instance.getPlayerData(player);

            // Open crafting menu
            playerData.openGUI(new CraftingGUI(playerData));
            return true;
        }
        return false;
    }
}
