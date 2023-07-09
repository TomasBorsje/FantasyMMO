package tomasborsje.plugin.fantasymmo.quests;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;

public class KillForestSlimesQuest extends AbstractQuestInstance {
    public KillForestSlimesQuest(PlayerData pd) {
        super(pd,
                new KillEntityObjective("FOREST_SLIME","Forest Slime",3, new Vector(132.5,67,-18.5)),
                new TalkToNPCQuestObjective("TEST_NPC", "Test NPC", new Vector(116.5,67,-43.5)));
        id = "KILL_FOREST_SLIMES";
        name = "Slimy Situation";
        description = "I see you've arrived, soldier. You might notice there are a lot of slimes around here.\nAs part of your training, I want you to kill 3 of them.";
        completionDescription = "Good job, soldier. You've proven you know how to fight.\nHere's your reward. You can keep the slushies, I don't exactly want them.";
        moneyReward = 100;
        xpReward = 1000;
        repeatable = true;
        itemRewards = new ItemStack[] {
                ItemRegistry.SLIME_JELLY.createStack(5),
                ItemRegistry.SLIME_SLUSHY.createStack(),
                ItemRegistry.SLIME_SLUSHY.createStack(),
                ItemRegistry.SLIME_SLUSHY.createStack()
        };
    }
}
