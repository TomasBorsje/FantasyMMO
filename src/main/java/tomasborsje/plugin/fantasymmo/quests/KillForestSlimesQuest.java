package tomasborsje.plugin.fantasymmo.quests;

public class KillForestSlimesQuest extends AbstractQuestInstance {
    public KillForestSlimesQuest() {
        super(new KillEntityObjective("FOREST_SLIME","Forest Slime",3),
                new KillEntityObjective("FOREST_SLIME","Forest Slime",5));
        name = "Kill Forest Slimes";
        description = "Kill 5 Forest Slimes";
        moneyReward = 100;
        xpReward = 1000;
    }
}
