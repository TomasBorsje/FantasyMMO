package tomasborsje.plugin.fantasymmo.entities.npcs;

import org.bukkit.util.Vector;

public class NPCSpawnPoint {
    public Vector position;
    public String npcId;

    public NPCSpawnPoint(Vector pos, String npcId) {
        this.position = pos;
        this.npcId = npcId;
    }
}
