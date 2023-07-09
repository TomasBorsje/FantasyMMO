package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import tomasborsje.plugin.fantasymmo.core.CustomNPC;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.guis.QuestTurnInGUI;
import tomasborsje.plugin.fantasymmo.handlers.EntityHandler;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;
import tomasborsje.plugin.fantasymmo.quests.AbstractQuestInstance;
import tomasborsje.plugin.fantasymmo.quests.TalkToNPCQuestObjective;

public class PlayerInteractPlayerListener implements Listener {
    @EventHandler
    public void OnPlayerInteractPlayer(PlayerInteractEntityEvent event) {

        // Only handle players right-clicking players
        if(event.getHand() != EquipmentSlot.OFF_HAND || !(event.getRightClicked() instanceof Player)) { return; }

        event.setCancelled(true);

        // Store id of what was clicked
        int clickedEntityId = event.getRightClicked().getEntityId();

        // Get player data of clicker
        PlayerData playerData = PlayerHandler.instance.getPlayerData(event.getPlayer());

        // If the right-clicked entity is a custom NPC, interact with the NPC
        if(EntityHandler.instance.hasNPC(clickedEntityId)) {
            CustomNPC npc = EntityHandler.instance.getNPC(clickedEntityId);

            // First we try to open quest turn in prompts
            boolean alreadyInteracted = false;
            // See if any quests require interaction with this NPC
            for(AbstractQuestInstance quest : playerData.activeQuests) {
                if(quest.getCurrentObjective() instanceof TalkToNPCQuestObjective npcObj && npcObj.isCorrectNPC(npc)) {
                    // Show turn in dialog
                    playerData.openGUI(new QuestTurnInGUI(playerData, quest, npc));
                    alreadyInteracted = true;
                }
            }
            // If we didn't make progress, interact with the NPC instead
            if(!alreadyInteracted) {
                npc.interact(playerData);
            }
        }
        else {
            // Otherwise the clicked player is a real player
            // TODO: Open profile viewer, etc.
        }
    }
}
