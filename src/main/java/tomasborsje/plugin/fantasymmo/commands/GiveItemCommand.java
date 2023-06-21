package tomasborsje.plugin.fantasymmo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.registries.ItemRegistry;

public class GiveItemCommand implements CommandExecutor {

    /**
     * Give a custom item to the player.
     * @param sender The sender of the command.
     * @param command The give command that was executed.
     * @param label The label of the command.
     * @param args The arguments of the command.
     * @return True if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Only give items to players
        if(sender instanceof Player player) {
            String itemId = args[0].toUpperCase(); // Item id is first arg
            int count = args.length == 2 ? Integer.parseInt(args[1]) : 1; // Item count is second arg or 1 if not specified

            // Check if custom item exists
            if(!ItemRegistry.ITEMS.exists(itemId)) {
                player.sendMessage(ChatColor.RED+"No item registered with ID "+itemId);
            }
            else {
                // Add item to player's inventory
                ItemStack stack = ItemRegistry.ITEMS.get(itemId).createStack();
                stack.setAmount(count);
                player.getInventory().addItem(stack);
                player.sendMessage(ChatColor.GREEN+"Gave "+ChatColor.WHITE+count+"x "+itemId+"!");
            }
        }
        return true;
    }
}
