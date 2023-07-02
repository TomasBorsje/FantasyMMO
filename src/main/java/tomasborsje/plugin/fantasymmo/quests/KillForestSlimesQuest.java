package tomasborsje.plugin.fantasymmo.quests;

import org.bukkit.util.Vector;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

public class KillForestSlimesQuest extends AbstractQuestInstance {
    public KillForestSlimesQuest(PlayerData pd) {
        super(pd,
                new KillEntityObjective("FOREST_SLIME","Forest Slime",3, new Vector(132.5,67,-18.5)),
                new KillEntityObjective("FOREST_SLIME","Forest Slime",5, new Vector(132.5,67,-18.5)));
        name = "Slimy Situation";
        description = "Kill Forest Slimes";
        moneyReward = 100;
        xpReward = 1000;
        id = "KILL_FOREST_SLIMES";
    }
}
