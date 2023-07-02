package tomasborsje.plugin.fantasymmo.items;

import net.minecraft.world.item.Item;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.buffs.FoodHealthRegenBuff;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomItem;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasDescription;
import tomasborsje.plugin.fantasymmo.core.interfaces.IUsable;
import tomasborsje.plugin.fantasymmo.core.util.SoundUtil;

public class BasicHealthFoodItem extends AbstractCustomItem implements IUsable, IHasDescription {
    private final int durationSec;
    private final int healthPerSec;
    private final String description;

    public BasicHealthFoodItem(String id, String name, String description, Rarity rarity, Item baseItem, int value, int durationSec, int healthPerSec) {
        this.description = description;
        this.customId = id;
        this.name = name;
        this.rarity = rarity;
        this.baseItem = baseItem;
        this.value = value;
        this.durationSec = durationSec;
        this.healthPerSec = healthPerSec;
    }

    @Override
    public boolean rightClick(PlayerData pd, ItemStack item) {
        pd.addBuff(new FoodHealthRegenBuff(name, durationSec*20, healthPerSec));
        SoundUtil.PlayEatSound(pd.player);
        pd.player.sendMessage(ChatColor.GREEN+"You ate "+name+". (+"+healthPerSec+ChatColor.GREEN+" hp/s)");
        return true;
    }

    @Override
    public String getRightClickDescription() {
        return ChatColor.WHITE+"Restores "+ChatColor.RED+healthPerSec+ChatColor.WHITE+" health per second for "+ChatColor.YELLOW+durationSec+ChatColor.WHITE+" seconds.\nRestoration stops upon entering combat.";
    }

    @Override
    public boolean canStack() {
        return true;
    }

    @Override
    public boolean isConsumable() {
        return true;
    }

    @Override
    public ItemType getType() {
        return ItemType.FOOD;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
