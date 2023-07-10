package tomasborsje.plugin.fantasymmo.quests;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;

public class KillBrambleSlimesQuest extends AbstractQuestInstance {
    public KillBrambleSlimesQuest(PlayerData pd) {
        super(pd,
                new KillEntityObjective("BRAMBLE_SLIME","Bramble Slime",10, new Vector(-32,91,26.5)),
                new TalkToNPCQuestObjective("TEST_NPC", "Test NPC", new Vector(116.5,67,-43.5)));
        id = "KILL_BRAMBLE_SLIMES";
        name = "Slimy Situation";
        description = "I see you've arrived, recruit. Just in time.\nThe slimes over in the bramble have been getting out of hand.\nAs part of your training, I want you to go out there and thin their numbers.";
        completionDescription = "Good job, soldier. You've proven you know how to fight.\nTake these supplies as your reward.";
        moneyReward = 100;
        xpReward = 200;
        repeatable = true;
        itemRewards = new ItemStack[] {
                ItemRegistry.SLIME_JELLY.createStack(5),
                ItemRegistry.SLIME_SLUSHY.createStack(),
                ItemRegistry.SLIME_SLUSHY.createStack(),
                ItemRegistry.SLIME_SLUSHY.createStack()
        };
    }
}
