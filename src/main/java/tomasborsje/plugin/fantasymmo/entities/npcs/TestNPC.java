package tomasborsje.plugin.fantasymmo.entities.npcs;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.bukkit.Location;
import tomasborsje.plugin.fantasymmo.core.CustomNPC;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.guis.PurchasableItem;
import tomasborsje.plugin.fantasymmo.guis.VendorGUI;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;

public class TestNPC extends CustomNPC {

    private final PurchasableItem[] itemsForSale = new PurchasableItem[] {
            new PurchasableItem(ItemRegistry.MISTWEAVE_ROBE, ItemUtil.Value(0,1,0)),
            new PurchasableItem(ItemRegistry.RECIPE_SCROLL_SLIME_TO_TRAINING_WAND, ItemUtil.Value(0,5,0)),
    };

    /**
     * Create a new instance of this entity at the given location.
     * Make sure you set id, name, location, etc. in any derived constructors.
     *
     * @param loc The location to spawn the entity at
     */
    public TestNPC(Location loc) {
        super(loc);
        this.id = "TEST_NPC";
        this.name = "Test NPC";
        this.heldItem = new ItemStack(Items.IRON_SWORD);
    }

    @Override
    public void interact(PlayerData playerData) {
        // Open GUI if not opened
        playerData.openGUI(new VendorGUI(playerData, "Test NPC Shop", itemsForSale));
    }
}
