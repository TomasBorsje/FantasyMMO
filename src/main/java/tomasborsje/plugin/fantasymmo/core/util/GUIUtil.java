package tomasborsje.plugin.fantasymmo.core.util;

import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.guis.QuestPromptGUI;
import tomasborsje.plugin.fantasymmo.quests.AbstractQuestInstance;

import java.util.function.Function;

public class GUIUtil {
    /**
     * Returns the slot number for the given x and y coordinates.
     * Note that x starts with 1 and y starts with 1.
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The slot number
     */
    public static int GetSlot(int x, int y) {
        return (y-1) * 9 + x-1;
    }

    /**
     * Shows a quest if the player has not yet completed it, or if it is repeatable.
     * @param player The player to show the quest to.
     * @param questRegistryEntry A function that returns the quest to show.
     * @return True if the quest was shown, false otherwise.
     */
    public static boolean ShowQuestPromptIfNotCompleted(PlayerData player, Function<PlayerData, AbstractQuestInstance> questRegistryEntry) {
        // Get quest
        AbstractQuestInstance quest = questRegistryEntry.apply(player);
        String questId = quest.getCustomId();
        // If player doesn't have the quest, and it's either repeatable or not already done
        if(!player.hasQuestActive(questId) && (!player.hasCompletedQuest(questId) || quest.isRepeatable())) {
            // Show quest if not yet completed
            player.openGUI(new QuestPromptGUI(player, quest));
            return true;
        }
        return false;
    }
}
