package tomasborsje.plugin.fantasymmo.content.npcs;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.bukkit.Location;
import tomasborsje.plugin.fantasymmo.content.items.holders.CustomArrows;
import tomasborsje.plugin.fantasymmo.content.items.holders.CustomEquipment;
import tomasborsje.plugin.fantasymmo.content.items.holders.CustomRecipeScrolls;
import tomasborsje.plugin.fantasymmo.core.CustomNPC;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.guis.PurchasableItem;
import tomasborsje.plugin.fantasymmo.guis.VendorGUI;

public class ShopNPC extends CustomNPC {

    /**
     * Create a new instance of this entity at the given location.
     * Make sure you set id, name, location, etc. in any derived constructors.
     *
     * @param loc The location to spawn the entity at
     */
    public ShopNPC(Location loc) {
        super(loc);
        this.id = "SHOP_NPC";
        this.name = "Shop NPC";
        this.heldItem = new ItemStack(Items.GOLD_INGOT);
    }

    @Override
    public void interact(PlayerData playerData) {
        super.interact(playerData);
        // Open quest if not completed
        playerData.openGUI(new VendorGUI(playerData, "Shop NPC's Shop",
                new PurchasableItem(CustomRecipeScrolls.RECIPE_SCROLL_SLIME_TO_TRAINING_WAND, ItemUtil.Value(0,0,25)),
                new PurchasableItem(CustomEquipment.MISTWEAVE_ROBE, ItemUtil.Value(2,0,0)),
                new PurchasableItem(CustomArrows.SIMPLE_ARROW, ItemUtil.Value(0,0,5), 5)
                ));
    }
}
