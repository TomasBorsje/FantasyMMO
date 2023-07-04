package tomasborsje.plugin.fantasymmo.handlers;

import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.CustomNPC;
import tomasborsje.plugin.fantasymmo.registries.EntityRegistry;

import java.util.HashMap;

/**
 * Stores custom entities (enemies, etc.) and NPCs (interactable entities).
 */
public class EntityHandler {
    public static EntityHandler instance = new EntityHandler();
    public HashMap<Integer, CustomEntity> customEntities = new HashMap<>();
    public HashMap<Integer, CustomNPC> customNPCs = new HashMap<>();

    private EntityHandler() {

    }

    public void addEntity(CustomEntity customEntity) {
        customEntities.put(customEntity.nmsEntity.getBukkitEntity().getEntityId(), customEntity);
    }

    public void addNPC(CustomNPC customNPC) {
        customNPCs.put(customNPC.nmsEntity.getBukkitEntity().getEntityId(), customNPC);
    }

    public void tick() {
        // Tick each custom entity
        for(CustomEntity customEntity : customEntities.values()) {
            customEntity.tick();
        }
        // Tick each NPC
        for(CustomNPC customNPC : customNPCs.values()) {
            customNPC.tick();
        }
    }

    public CustomEntity spawnEntity(Location location, String customId) {
        // Get NMS level
        Level level = ((CraftWorld) location.getWorld()).getHandle();

        // Spawn custom entity at position
        CustomEntity customEntity = EntityRegistry.ENTITIES.get(customId).apply(location);

        // Spawn and get as bukkit Entity
        level.addFreshEntity(customEntity.nmsEntity);

        // Store new entity and it's ID in NPCHandler
        EntityHandler.instance.addEntity(customEntity);

        return customEntity;
    }

    public CustomNPC spawnNPC(Location location, String customId) {
        // Spawn custom entity at position
        CustomNPC customNPC = EntityRegistry.NPCS.get(customId).apply(location);

        // Spawn and get as bukkit Entity
        customNPC.spawnInWorld();

        // Store new entity and it's ID in NPCHandler
        EntityHandler.instance.addNPC(customNPC);

        return customNPC;
    }

    /**
     * Spawns all NPCs in world for the given player.
     * @param player The player to spawn the NPCs for
     */
    public void spawnNPCsForPlayer(Player player) {
        for(CustomNPC customNPC : customNPCs.values()) {
            customNPC.spawnForPlayer(player);
        }
    }

    public void removeNPC(int id) {
        if(!customNPCs.containsKey(id)) {
            return;
        }
        customNPCs.get(id).remove();
        customNPCs.remove(id);
    }

    public boolean hasEntity(int id) {
        return customEntities.containsKey(id);
    }
    public boolean hasNPC(int id) {
        return customNPCs.containsKey(id);
    }

    public CustomEntity getEntity(int id) {
        if(!customEntities.containsKey(id)) {
            return null;
        }
        return customEntities.get(id);
    }
    public CustomNPC getNPC(int id) {
        if(!customNPCs.containsKey(id)) {
            return null;
        }
        return customNPCs.get(id);
    }
}
