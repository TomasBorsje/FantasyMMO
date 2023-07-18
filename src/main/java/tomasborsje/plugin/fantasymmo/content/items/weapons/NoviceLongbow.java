package tomasborsje.plugin.fantasymmo.content.items.weapons;

import tomasborsje.plugin.fantasymmo.core.AbstractBowWeapon;
import tomasborsje.plugin.fantasymmo.core.StatBoost;
import tomasborsje.plugin.fantasymmo.core.enums.EquipType;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.MMOClass;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasDescription;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasItemScore;
import tomasborsje.plugin.fantasymmo.core.interfaces.IStatProvider;

public class NoviceLongbow extends AbstractBowWeapon implements IHasDescription, IHasItemScore, IStatProvider {
    private final int itemScore = 10;
    private final StatBoost stats = new StatBoost(itemScore, ItemType.LONGBOW, MMOClass.ARCHER).withFocus();
    public NoviceLongbow() {
        super();
        this.rarity = Rarity.COMMON;
        this.name = "Novice Longbow";
        this.customId = "NOVICE_LONGBOW";
        this.value = 50;
        this.damage = 13;
    }

    @Override
    public boolean canStack() {
        return false;
    }

    @Override
    public ItemType getType() {
        return ItemType.LONGBOW;
    }

    @Override
    public String getDescription() {
        return "A simple longbow, used by novice archers to practice their aim.";
    }

    @Override
    public StatBoost getStats() {
        return stats;
    }

    @Override
    public EquipType getEquipType() {
        return EquipType.HELD;
    }

    @Override
    public int getItemScore() {
        return 0;
    }
}
