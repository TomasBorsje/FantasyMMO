package tomasborsje.plugin.fantasymmo.quests;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasId;

import java.util.List;

/**
 * The base class for all quests.
 * This must be derived for each quest.
 */
public abstract class AbstractQuestInstance implements IHasId {
    protected final IQuestObjective[] objectives;
    private final PlayerData playerData;
    protected String name;
    protected String description;
    protected int moneyReward;
    protected int xpReward;
    protected int stage = 0;
    protected String id;
    boolean completed = false;
    protected ItemStack[] itemRewards;
    public AbstractQuestInstance(PlayerData pd, IQuestObjective... objectives) {
        this.playerData = pd;
        this.objectives = objectives;
    }

    public int getStage() {
        return stage;
    }

    /**
     * Loads quest data, populating the current stage and objective data.
     * @param stage The current stage of the quest.
     * @param loadArgs The data for the current objective.
     */
    public void load(int stage, List<Integer> loadArgs) {
        this.stage = stage;
        // Load the latest objective state
        this.objectives[stage].load(loadArgs);
        // Check if it's completed
        this.completed = this.objectives[stage].isCompleted();
    }

    /**
     * Returns the load data for the current objective.
     * @return The load data for the current objective.
     */
    public List<Integer> getLoadData() {
        return objectives[stage].getLoadData();
    }

    public boolean isCompleted() {
        return completed;
    }
    @Override
    public String getCustomId() {
        return id;
    }

    private IQuestObjective getCurrentObjective() {
        return objectives[stage];
    }

    public String getQuestStatus() {
        return completed ? ChatColor.YELLOW + "Complete!" : ChatColor.WHITE + getCurrentObjective().getStatusString();
    }

    /**
     * Attempt to progress this quest by killing a mob.
     * @param killed The mob that was killed.
     * @return True if progress was made on this quest.
     */
    public boolean registerKill(CustomEntity killed) {
        if (getCurrentObjective() instanceof KillEntityObjective killObjective) {
            // Check if we made progress
            if(killObjective.tryAddProgress(killed)) {
                // Progress to next stage if this objective is now completed
                if(killObjective.isCompleted()) {
                    // If we just completed the last objective, mark this quest as completed
                    if(stage == objectives.length - 1) {
                        completed = true;
                    }
                    else {
                        stage++;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Grants rewards of the quest to the player.
     * @param p The player to grant rewards to.
     */
    public void grantRewards(PlayerData p) {
        p.addMoney(moneyReward);
        p.gainExperience(xpReward);
        if(itemRewards.length > 0) {
            p.giveItem(itemRewards);
        }
    }

    public Vector getObjectiveLocation() {
        return getCurrentObjective().getLocation();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMoneyReward() {
        return moneyReward;
    }

    public int getXpReward() {
        return xpReward;
    }

    public ItemStack[] getItemRewards() {
        return itemRewards;
    }

}
