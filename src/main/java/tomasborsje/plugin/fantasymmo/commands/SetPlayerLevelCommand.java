package tomasborsje.plugin.fantasymmo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.util.StatCalc;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

public class SetPlayerLevelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)) {
            return false;
        }
        if(args.length != 3) {
            return false;
        }

        String username = args[0];
        int level = Integer.parseInt(args[1]);
        double percentage = Double.parseDouble(args[2]); // 0-100

        PlayerData data = PlayerHandler.instance.getPlayerData(username);
        data.setExperience((int) (StatCalc.getExperienceForLevel(level) * percentage / 100));
        data.setLevel(level);

        return true;
    }
}
