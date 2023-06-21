package tomasborsje.plugin.fantasymmo.core.registries;

import net.minecraft.world.item.Items;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.GenericItem;
import tomasborsje.plugin.fantasymmo.items.MistweaveRobe;
import tomasborsje.plugin.fantasymmo.items.TrainingWand;

public class ItemRegistry {
    public static final Registry<ICustomItem> ITEMS = new Registry<>();

    public static final ICustomItem SLIMEBALL = ITEMS.register(new GenericItem("SLIMEBALL", "Slimeball", Items.SLIME_BALL, Rarity.TRASH, 507399, "A ball of slime.\nGross..."));

    // Usable items
    public static final ICustomItem TRAINING_WAND = ITEMS.register(new TrainingWand());
    public static final ICustomItem MISTWEAVE_ROBE = ITEMS.register(new MistweaveRobe());
}
