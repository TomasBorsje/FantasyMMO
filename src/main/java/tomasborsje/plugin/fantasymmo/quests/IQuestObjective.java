package tomasborsje.plugin.fantasymmo.quests;

/**
 * An INSTANCE of objective for a quest. Should be extended for each type of objective (kill, talk to, etc.).
 */
public interface IQuestObjective {
    public boolean isCompleted();
    public String getStatusString();
}
