package tomasborsje.plugin.fantasymmo.core;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import tomasborsje.plugin.fantasymmo.core.util.TooltipUtil;

import static org.bukkit.Bukkit.getServer;

/**
 * Scoreboard class that handles the display of the GUI on the right side of the screen.
 */
public class PlayerScoreboard {
    private PlayerData playerData;
    int i = 99;
    int emptyLineCount = 0;

    public PlayerScoreboard(PlayerData playerData) {
        this.playerData = playerData;
    }

    /**
     * Re-renders the scoreboard and shows it to the player.
     */
    public void render() {
        // Reset counting vars
        i = 99;
        emptyLineCount = 0;

        // Alias
        Player p = playerData.player;

        // Get or create scoreboard
        if(p.getScoreboard().equals(getServer().getScoreboardManager().getMainScoreboard())) p.setScoreboard(getServer().getScoreboardManager().getNewScoreboard());
        Scoreboard score = p.getScoreboard();
        Objective objective = score.getObjective(p.getName()) == null ? score.registerNewObjective(p.getName(), "dummy") : score.getObjective(p.getName());

        // Set display name of the scoreboard
        objective.setDisplayName(ChatColor.YELLOW+""+ChatColor.BOLD+"Fantasy"+ChatColor.GOLD+ChatColor.BOLD+"MMO");

        // -- Rows of the scoreboard --
        addEmptyLine(objective); // Empty line

        // Money
        replaceScore(objective, i--, ChatColor.WHITE+"Money: "+ TooltipUtil.GetPlayerMoneyString(playerData.getMoney()));
        addEmptyLine(objective);

        // Current quest display
        if(playerData.getCurrentQuest() != null) {
            replaceScore(objective, i--, ChatColor.YELLOW+"Quests:");
            replaceScore(objective, i--, ChatColor.WHITE+playerData.getCurrentQuest().getName());
            replaceScore(objective, i--, ChatColor.GRAY+"- "+playerData.getCurrentQuest().getQuestStatus());
            addEmptyLine(objective);
        }

        // Buff indicators
        if(!playerData.buffs.isEmpty()) {
            replaceScore(objective, i--, ChatColor.YELLOW+"Buffs:");
        }
        for(Buff buff : playerData.buffs) {
            replaceScore(objective, i--, buff.getDisplayString());
        }
        if(!playerData.buffs.isEmpty()) {
            addEmptyLine(objective);
        }

        // Cooldown indicators
        if(!playerData.cooldowns.isEmpty()) {
            replaceScore(objective, i--, ChatColor.YELLOW+"Cooldowns:");
        }
        for(CooldownInstance cd : playerData.cooldowns) {
            replaceScore(objective, i--, cd.getDisplayString());
        }
        if(!playerData.cooldowns.isEmpty()) {
            addEmptyLine(objective);
        }

        // Website URL
        replaceScore(objective, i--, ChatColor.GOLD+"www.fantasymmo.co.nz");


        // Replace all leftover lines afterwards
        for(int j = i; j > 50; j--) {
            if(getEntryFromScore(objective, j) != null) {
                replaceScore(objective, j, "");
            }
        }
        replaceScore(objective, 99, "");

        // Show it to the player
        if(objective.getDisplaySlot() != DisplaySlot.SIDEBAR) objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        p.setScoreboard(score);
    }

    void addConditionalEmptyLine(Objective objective, boolean show) {
        emptyLineCount++;
        if(show) {
            replaceScore(objective, i--, " ".repeat(emptyLineCount));
        }
        else{
            replaceScore(objective, 99, " ".repeat(emptyLineCount));
        }
    }

    void addEmptyLine(Objective objective) {
        emptyLineCount++;
        replaceScore(objective, i--, " ".repeat(emptyLineCount));
    }

    /* Code below adapted from https://www.spigotmc.org/threads/scoreboard-flickering.213720/ */
    public static String getEntryFromScore(Objective o, int score) {
        if(o == null) return null;
        if(!hasScoreTaken(o, score)) return null;
        for (String s : o.getScoreboard().getEntries()) {
            if(o.getScore(s).getScore() == score) return o.getScore(s).getEntry();
        }
        return null;
    }

    public static boolean hasScoreTaken(Objective o, int score) {
        for (String s : o.getScoreboard().getEntries()) {
            if(o.getScore(s).getScore() == score) return true;
        }
        return false;
    }

    public static void replaceScore(Objective o, int score, String name) {
        if(hasScoreTaken(o, score)) {
            if(getEntryFromScore(o, score).equalsIgnoreCase(name)) return;
            if(!(getEntryFromScore(o, score).equalsIgnoreCase(name))) o.getScoreboard().resetScores(getEntryFromScore(o, score));
        }
        o.getScore(name).setScore(score);
    }
}
