package tomasborsje.plugin.fantasymmo.handlers;

import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.registries.EntityRegistry;

import java.util.HashMap;

public class NPCHandler {
    public static NPCHandler instance = new NPCHandler();
    public HashMap<Integer, CustomEntity> customEntities = new HashMap<>();

    private NPCHandler() {

    }

    public void addNPC(CustomEntity customEntity) {
        customEntities.put(customEntity.nmsEntity.getBukkitEntity().getEntityId(), customEntity);
    }

    public void tick() {
        // Tick each custom entity
        for(CustomEntity customEntity : customEntities.values()) {
            customEntity.tick();
        }
    }

    public CustomEntity spawnNPC(Location location, String customId) {

        // Get NMS level
        Level level = ((CraftWorld) location.getWorld()).getHandle();

        // Spawn custom entity at position
        CustomEntity customEntity = EntityRegistry.ENTITIES.get(customId).apply(location);

        // Spawn and get as bukkit Entity
        level.addFreshEntity(customEntity.nmsEntity);

        // Store new entity and it's ID in NPCHandler
        NPCHandler.instance.addNPC(customEntity);

        return customEntity;
    }

    public boolean hasNPC(int id) {
        return customEntities.containsKey(id);
    }

    public CustomEntity getNPC(int id) {
        if(!customEntities.containsKey(id)) {
            return null;
        }
        return customEntities.get(id);
    }
}
