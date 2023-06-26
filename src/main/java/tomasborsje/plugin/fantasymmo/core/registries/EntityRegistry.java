package tomasborsje.plugin.fantasymmo.core.registries;

import org.bukkit.Location;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.CustomNPC;
import tomasborsje.plugin.fantasymmo.entities.entities.ForestSlime;
import tomasborsje.plugin.fantasymmo.entities.npcs.TestNPC;

import java.util.function.Function;

public class EntityRegistry {
    public static final FuncRegistry<CustomEntity> ENTITIES = new FuncRegistry<CustomEntity>();
    public static final FuncRegistry<CustomNPC> NPCS = new FuncRegistry<>();

    public static final Function<Location, CustomEntity> FOREST_SLIME = ENTITIES.register("FOREST_SLIME", ForestSlime::new);

    public static final Function<Location, CustomNPC> TEST_NPC = NPCS.register("TEST_NPC", TestNPC::new);
}
