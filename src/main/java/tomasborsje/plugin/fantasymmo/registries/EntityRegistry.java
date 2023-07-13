package tomasborsje.plugin.fantasymmo.registries;

import org.bukkit.Location;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.CustomNPC;
import tomasborsje.plugin.fantasymmo.core.FuncRegistry;
import tomasborsje.plugin.fantasymmo.content.entities.BrambleSlime;
import tomasborsje.plugin.fantasymmo.content.entities.ForestWolf;
import tomasborsje.plugin.fantasymmo.content.npcs.ShopNPC;
import tomasborsje.plugin.fantasymmo.content.npcs.TestNPC;

import java.util.function.Function;

/**
 * TODO: Maybe move this into a holder class?
 */
public class EntityRegistry {
    public static final FuncRegistry<CustomEntity> ENTITIES = new FuncRegistry<CustomEntity>();
    public static final FuncRegistry<CustomNPC> NPCS = new FuncRegistry<>();

    /* ENEMIES */
    public static final Function<Location, CustomEntity> BRAMBLE_SLIME = ENTITIES.register("BRAMBLE_SLIME", BrambleSlime::new);
    public static final Function<Location, CustomEntity> FOREST_WOLF = ENTITIES.register("FOREST_WOLF", ForestWolf::new);

    /* NPCs */
    public static final Function<Location, CustomNPC> TEST_NPC = NPCS.register("TEST_NPC", TestNPC::new);
    public static final Function<Location, CustomNPC> SHOP_NPC = NPCS.register("SHOP_NPC", ShopNPC::new);
}
