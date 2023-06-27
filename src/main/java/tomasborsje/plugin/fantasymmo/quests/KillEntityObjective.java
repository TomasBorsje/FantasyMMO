package tomasborsje.plugin.fantasymmo.quests;

import tomasborsje.plugin.fantasymmo.core.CustomEntity;

/**
 * A quest objective for killing mobs.
 */
public class KillEntityObjective implements IQuestObjective {
    public final String mobId;
    public final String mobName;
    private final String plural;
    public final int amount;
    public int progress = 0;

    public KillEntityObjective(String mobId, String mobName, int amount) {
        this.mobId = mobId;
        this.mobName = mobName;
        this.amount = amount;
        plural = amount > 1 ? "s" : "";
    }

    @Override
    public boolean isCompleted() {
        return progress >= amount;
    }

    /**
     * Adds progress to this quest objective if the killed mob is the correct one.
     * @param killed The mob that was killed.
     * @return True if progress was made.
     */
    public boolean tryAddProgress(CustomEntity killed) {
        if (killed.getCustomId().equals(mobId)) {
            if(progress < amount) {
                progress++;
                return true;
            }
        }
        return false;
    }

    @Override
    public String getStatusString() {
        return "Kill "+amount+" "+mobName+plural+ " ("+progress+"/"+amount+")";
    }
}
