package tomasborsje.plugin.fantasymmo.quests;

import org.bukkit.ChatColor;
import org.bukkit.util.Vector;
import tomasborsje.plugin.fantasymmo.core.CustomNPC;

import java.util.List;

public class TalkToNPCQuestObjective implements IQuestObjective {
    public final String npcName;
    private boolean completed;
    private final Vector location;

    public TalkToNPCQuestObjective(String npcName, Vector location) {
        this.npcName = npcName;
        this.location = location;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    public boolean tryAddProgress(CustomNPC npc) {
        if(npc.name.equals(npcName)) {
            completed = true;
            return true;
        }
        return false;
    }

    @Override
    public String getStatusString() {
        return ChatColor.GRAY+"Talk to "+npcName;
    }

    @Override
    public void load(List<Integer> loadArgs) {
        if(loadArgs.size() != 1) {
            throw new IllegalArgumentException("TalkToNPCQuestObjective requires 1 argument");
        }
        this.completed = loadArgs.get(0) == 1;
    }

    @Override
    public List<Integer> getLoadData() {
        return List.of(completed ? 1 : 0);
    }

    @Override
    public Vector getLocation() {
        return location;
    }
}
