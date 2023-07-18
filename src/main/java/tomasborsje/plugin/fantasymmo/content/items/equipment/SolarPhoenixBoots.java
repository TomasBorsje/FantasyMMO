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

public class SolarPhoenixBoots extends AbstractArmourItem implements IGlowingItem, IHasDescription, IHasArmourTrim {
    private final static int itemScore = 500;
    private static final StatBoost stats = new StatBoost(itemScore, ItemType.BOOTS, MMOClass.MAGE).withIntelligence().withHealth().withDefense();
    public SolarPhoenixBoots() {
        this.customId = "SOLAR_PHOENIX_BOOTS";
        this.name = "Solar Phoenix Boots";
        this.value = ItemUtil.Value(23, 1, 89);
        this.rarity = Rarity.LEGENDARY;
    }

    @Override
    public Item getBaseItem() {
        return Items.GOLDEN_BOOTS;
    }

    @Override
    public ItemType getType() {
        return ItemType.BOOTS;
    }

    @Override
    public ArmorTrim getArmourTrim() {
        return ArmorTrims.SOLAR_PHOENIX_TRIM;
    }

    @Override
    public String getDescription() {
        return "Fiery boots, charred with phoenix ashes.\nImbued with the power of the sun.";
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
