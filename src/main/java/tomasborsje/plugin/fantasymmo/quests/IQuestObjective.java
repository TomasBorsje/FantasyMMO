package tomasborsje.plugin.fantasymmo.quests;

import org.bukkit.util.Vector;

import java.util.List;

/**
 * An INSTANCE of objective for a quest. Should be extended for each type of objective (kill, talk to, etc.).
 */
public interface IQuestObjective {
    /**
     * Returns true if the objective is completed.
     * @return True if the objective is completed.
     */
    public boolean isCompleted();

    /**
     * Returns a string describing the current status of the objective.
     * @return A string describing the current status of the objective.
     */
    public String getStatusString();

    /**
     * Loads the objective state from the given data.
     * Data is provided from getLoadData().
     * @param loadArgs The data for the current objective.
     */
    public void load(List<Integer> loadArgs);

    /**
     * Returns the load data for the current objective.
     * Used to load the objective state again in load().
     * @return The load data used to recreate the objective state.
     */
    public List<Integer> getLoadData();

    /**
     * Get the location of the objective.
     * Used for the compass.
     * @return The location of the objective.
     */
    public Vector getLocation();
}
