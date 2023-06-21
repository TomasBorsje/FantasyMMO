package tomasborsje.plugin.fantasymmo.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.bukkit.Color;
import tomasborsje.plugin.fantasymmo.core.AbstractArmourItem;
import tomasborsje.plugin.fantasymmo.core.enums.EquipType;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.StatBoost;
import tomasborsje.plugin.fantasymmo.core.interfaces.IDyeable;
import tomasborsje.plugin.fantasymmo.core.interfaces.IGlowingItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasDescription;

public class MistweaveRobe extends AbstractArmourItem implements IHasDescription, IDyeable, IGlowingItem {
    static StatBoost stats = new StatBoost(0, 25, 50, 13);
    static Color color = Color.fromRGB(70, 50, 168);
    public MistweaveRobe() {
        this.customId = "MISTWEAVE_ROBE";
        this.name = "Mistweave Robe";
        this.value = 789105;
        this.rarity = Rarity.RARE;
    }
    @Override
    public Item getBaseItem() {
        return Items.LEATHER_CHESTPLATE;
    }

    @Override
    public ItemType getType() {
        return ItemType.CHESTPLATE;
    }

    @Override
    public EquipType getEquipType() {
        return EquipType.ARMOUR;
    }

    @Override
    public StatBoost getStats() {
        return stats;
    }

    @Override
    public String getDescription() {
        return "A robe made from the finest mistweave cloth.\nGreatly bolsters a user's magic power.";
    }

    @Override
    public Color getColor() {
        return color;
    }
}
