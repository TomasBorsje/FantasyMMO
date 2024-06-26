package tomasborsje.plugin.fantasymmo.content.items.equipment;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import tomasborsje.plugin.fantasymmo.core.AbstractArmourItem;
import tomasborsje.plugin.fantasymmo.core.StatBoost;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.MMOClass;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.IGlowingItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasArmourTrim;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasDescription;
import tomasborsje.plugin.fantasymmo.core.util.ArmorTrims;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;

public class SolarPhoenixHood extends AbstractArmourItem implements IGlowingItem, IHasDescription, IHasArmourTrim {
    private final static int itemScore = 500;
    private static final StatBoost stats = new StatBoost(itemScore, ItemType.HELMET, MMOClass.MAGE).withIntelligence().withHealth().withDefense();
    public SolarPhoenixHood() {
        this.customId = "SOLAR_PHOENIX_HOOD";
        this.name = "Solar Phoenix Hood";
        this.value = ItemUtil.Value(23, 1, 89);
        this.rarity = Rarity.LEGENDARY;
    }

    @Override
    public Item getBaseItem() {
        return Items.GOLDEN_HELMET;
    }

    @Override
    public ItemType getType() {
        return ItemType.HELMET;
    }

    @Override
    public ArmorTrim getArmourTrim() {
        return ArmorTrims.SOLAR_PHOENIX_TRIM;
    }

    @Override
    public String getDescription() {
        return "Delicately crafted from the feathers of the Solar Phoenix.\nImbued with the power of the sun.";
    }

    @Override
    public StatBoost getStats() {
        return stats;
    }

    @Override
    public int getItemScore() {
        return itemScore;
    }
}
