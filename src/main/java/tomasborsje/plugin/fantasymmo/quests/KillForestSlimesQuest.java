package tomasborsje.plugin.fantasymmo.quests;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;

public class KillForestSlimesQuest extends AbstractQuestInstance {
    public KillForestSlimesQuest(PlayerData pd) {
        super(pd,
                new KillEntityObjective("FOREST_SLIME","Forest Slime",3, new Vector(132.5,67,-18.5)));
        id = "KILL_FOREST_SLIMES";
        name = "Slimy Situation";
        description = "Kill Forest Slimes";
        moneyReward = 100;
        xpReward = 1000;
        itemRewards = new ItemStack[] {
                ItemRegistry.SLIME_JELLY.createStack(5),
                ItemRegistry.SLIME_SLUSHY.createStack(),
                ItemRegistry.SLIME_SLUSHY.createStack(),
                ItemRegistry.SLIME_SLUSHY.createStack()
        };
    }
}
