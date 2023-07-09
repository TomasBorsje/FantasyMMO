package tomasborsje.plugin.fantasymmo.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

/**
 * This class creates objective teams to show each player's level as a prefix.
 */
public class PlayerPrefixHandler {
    public static PlayerPrefixHandler instance = new PlayerPrefixHandler();
    private Scoreboard scoreboard;
    private PlayerPrefixHandler() { }

    public void init() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        // Create teams for each level 1-100
        for(int lvl = 1; lvl <= 100; lvl++) {
            Team team = scoreboard.getTeam("level"+lvl);
            if(team == null) {
                team = scoreboard.registerNewTeam("level"+lvl);
            }
            team.setPrefix(ChatColor.WHITE+"["+ChatColor.GOLD+lvl+ChatColor.WHITE+"] ");
        }
    }

    /**
     * Adds a player to the team that gives them the prefix for their level.
     * @param playerData The player whose level prefix to set
     */
    public void setPlayerPrefix(PlayerData playerData) {
        // Get the team for the player's level
        Team team = scoreboard.getTeam("level"+playerData.getLevel());
        // Remove player from existing teams
        scoreboard.getTeams().forEach(t -> t.removeEntry(playerData.player.getName()));
        // Add the player to the team
        team.addEntry(playerData.player.getName());
    }
}
