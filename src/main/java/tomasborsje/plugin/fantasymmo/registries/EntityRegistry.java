package tomasborsje.plugin.fantasymmo.registries;

import org.bukkit.Location;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.CustomNPC;
import tomasborsje.plugin.fantasymmo.core.FuncRegistry;
import tomasborsje.plugin.fantasymmo.entities.entities.BrambleSlime;
import tomasborsje.plugin.fantasymmo.entities.npcs.ShopNPC;
import tomasborsje.plugin.fantasymmo.entities.npcs.TestNPC;

import java.util.function.Function;

public class EntityRegistry {
    public static final FuncRegistry<CustomEntity> ENTITIES = new FuncRegistry<CustomEntity>();
    public static final FuncRegistry<CustomNPC> NPCS = new FuncRegistry<>();

    /* ENEMIES */
    public static final Function<Location, CustomEntity> BRAMBLE_SLIME = ENTITIES.register("BRAMBLE_SLIME", BrambleSlime::new);

    /* NPCs */
    public static final Function<Location, CustomNPC> TEST_NPC = NPCS.register("TEST_NPC", TestNPC::new);
    public static final Function<Location, CustomNPC> SHOP_NPC = NPCS.register("SHOP_NPC", ShopNPC::new);
}
