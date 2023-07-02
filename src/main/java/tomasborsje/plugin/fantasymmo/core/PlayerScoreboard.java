package tomasborsje.plugin.fantasymmo.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;
import tomasborsje.plugin.fantasymmo.core.util.TooltipUtil;
import tomasborsje.plugin.fantasymmo.quests.AbstractQuestInstance;

public class PlayerScoreboard {
    private PlayerData playerData;
    private Scoreboard scoreboard;
    private boolean initialized;
    private Objective display;
    private Score firstEmpty;
    private Score money;
    private Score secondEmpty;
    private Score questsHeader;
    private Score thirdEmpty;
    private Score websiteURL;
    private Score quest1;
    private Score quest1Progress;

    public PlayerScoreboard(PlayerData playerData) {
        this.playerData = playerData;

        // Init scoreboard from ScoreboardManager
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        // Init display objective
        display = scoreboard.registerNewObjective("display", Criteria.DUMMY,ChatColor.YELLOW+""+ChatColor.BOLD+"Fantasy"+ChatColor.GOLD+""+ChatColor.BOLD+"MMO");
        display.setDisplaySlot(DisplaySlot.SIDEBAR);
        render();
        initialized = true;
    }

    /**
     * Re-renders the scoreboard and shows it to the player.
     */
    public void render() {
        if(initialized) {
            resetScores();
        }
        // Uses the following format
        // TITLE
        //
        // Money: 0
        //
        // Quests:
        // Quest1
        // - Quest1 Progress

        // ---- CODE ----
        int i = 15;

        // TODO: Stop flickering (looks like a whole undertaking, use team scoreboards instead)

        // Empty line 1
        firstEmpty = display.getScore("");
        firstEmpty.setScore(i--);

        // Money display
        money = display.getScore(ChatColor.WHITE+"Money: " + ChatColor.RESET + TooltipUtil.GetPlayerMoneyString(playerData.getMoney()));
        money.setScore(i--);

        // Show first quest
        if(playerData.activeQuests.size() > 0) {
            // Second empty line
            secondEmpty = display.getScore(" ");
            secondEmpty.setScore(i--);

            // Quests header
            questsHeader = display.getScore(ChatColor.GOLD+"Quest:");
            questsHeader.setScore(i--);

            AbstractQuestInstance quest = playerData.activeQuests.get(0);
            quest1 = display.getScore(ChatColor.WHITE+quest.getName());
            quest1.setScore(i--);

            quest1Progress = display.getScore(ChatColor.GRAY+"- "+quest.getQuestStatus());
            quest1Progress.setScore(i--);
        }

        // Third empty line
        thirdEmpty = display.getScore("  ");
        thirdEmpty.setScore(i--);

        // Show website URL
        websiteURL = display.getScore(ChatColor.GOLD+"www.fantasymmo.com");
        websiteURL.setScore(i--);

        playerData.player.setScoreboard(scoreboard);
    }

    private void resetScores() {
        scoreboard.resetScores(money.getEntry());
        scoreboard.resetScores(firstEmpty.getEntry());
        if(quest1 != null) {
            scoreboard.resetScores(secondEmpty.getEntry());
            scoreboard.resetScores(questsHeader.getEntry());
            scoreboard.resetScores(quest1.getEntry());
            scoreboard.resetScores(quest1Progress.getEntry());
        }
    }

    /**
     * Get the scoreboard.
     * @return The player's scoreboard
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
