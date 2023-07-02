package tomasborsje.plugin.fantasymmo.quests;

import org.bukkit.ChatColor;
import org.bukkit.util.Vector;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;

import java.util.List;

/**
 * A quest objective for killing mobs.
 */
public class KillEntityObjective implements IQuestObjective {
    public final String mobId;
    public final String mobName;
    private final String plural;
    public final int amount;
    public int progress = 0;
    public final Vector location;
    public KillEntityObjective(String mobId, String mobName, int amount, Vector loc) {
        this.mobId = mobId;
        this.mobName = mobName;
        this.amount = amount;
        this.location = loc;
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
        return ChatColor.GRAY+"Kill "+amount+" "+mobName+plural+ ChatColor.DARK_GRAY+" ("+progress+"/"+amount+")";
    }

    @Override
    public void load(List<Integer> loadArgs) {
        if(loadArgs.size() != 1) {
            throw new IllegalArgumentException("KillEntityObjective requires 1 argument");
        }
        this.progress = loadArgs.get(0);
    }

    @Override
    public List<Integer> getLoadData() {
        return List.of(progress);
    }

    @Override
    public Vector getLocation() {
        return location;
    }
}
