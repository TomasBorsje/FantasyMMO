package tomasborsje.plugin.fantasymmo.registries;

import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.PlayerDataFuncRegistry;
import tomasborsje.plugin.fantasymmo.quests.AbstractQuestInstance;
import tomasborsje.plugin.fantasymmo.quests.KillForestSlimesQuest;

import java.util.function.Function;

public class QuestRegistry {
    public static final PlayerDataFuncRegistry<AbstractQuestInstance> QUESTS = new PlayerDataFuncRegistry<AbstractQuestInstance>();
    // Starter Zone Quests
    public static final Function<PlayerData, AbstractQuestInstance> KILL_FOREST_SLIMES = QUESTS.register("KILL_FOREST_SLIMES", KillForestSlimesQuest::new);
}
