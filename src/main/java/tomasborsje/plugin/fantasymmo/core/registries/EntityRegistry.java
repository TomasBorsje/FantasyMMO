package tomasborsje.plugin.fantasymmo.core.registries;

import org.bukkit.Location;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.entities.entities.ForestSlime;

import java.util.function.Function;

public class EntityRegistry {
    public static final FuncRegistry<CustomEntity> ENTITIES = new FuncRegistry<CustomEntity>();

    public static final Function<Location, CustomEntity> FOREST_SLIME = ENTITIES.register("FOREST_SLIME", ForestSlime::new);

}
