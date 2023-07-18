package tomasborsje.plugin.fantasymmo.content.items.equipment;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import tomasborsje.plugin.fantasymmo.core.AbstractArmourItem;
import tomasborsje.plugin.fantasymmo.core.StatBoost;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.IGlowingItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasArmourTrim;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasDescription;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;

public class SolarPhoenixHood extends AbstractArmourItem implements IGlowingItem, IHasDescription, IHasArmourTrim {
    private static final ArmorTrim SOLAR_PHOENIX_TRIM = new ArmorTrim(TrimMaterial.COPPER, TrimPattern.RIB);
    private static final StatBoost stats = new StatBoost().withIntelligence(173).withHealth(251).withDefense(57);

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
        return SOLAR_PHOENIX_TRIM;
    }

    @Override
    public String getDescription() {
        return "Delicately crafted from the feathers of the Solar Phoenix.\nImbued with the power of the sun.";
    }

    @Override
    public StatBoost getStats() {
        return stats;
    }
}
