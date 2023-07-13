package tomasborsje.plugin.fantasymmo.core;

import net.minecraft.world.item.Items;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.buffs.FoodManaRegenBuff;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomItem;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.IGlowingItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasDescription;
import tomasborsje.plugin.fantasymmo.core.interfaces.IUsable;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.core.util.SoundUtil;

public class BasicManaPotionItem extends AbstractCustomItem implements IUsable, IGlowingItem, IHasDescription {
    private final int durationSec;
    private final int manaPerSec;
    public final Color potionColor;
    private final String description;

    public BasicManaPotionItem(String id, String name, String description, Color potionColor, Rarity rarity, int value, int durationSec, int manaPerSec) {
        this.customId = id;
        this.name = name;
        this.description = description;
        this.potionColor = potionColor;
        this.rarity = rarity;
        this.baseItem = Items.POTION;
        this.value = value;
        this.durationSec = durationSec;
        this.manaPerSec = manaPerSec;
    }

    @Override
    public boolean rightClick(PlayerData pd, ItemStack item) {
        pd.addBuff(new FoodManaRegenBuff(name, durationSec*20, manaPerSec));
        SoundUtil.PlayDrinkSound(pd.player);
        pd.player.sendMessage(ChatColor.GREEN+"You drank "+name+". (+"+manaPerSec+ChatColor.GREEN+" Mana Regeneration)");
        return true;
    }

    @Override
    public String getRightClickDescription() {
        return ChatColor.WHITE+"Restores "+ChatColor.BLUE+ manaPerSec*durationSec +" Mana"+ChatColor.WHITE+" over "+ChatColor.YELLOW+durationSec+ChatColor.WHITE+" seconds.\nRestoration stops upon entering combat.";
    }

    @Override
    public ItemStack createStack() {
        ItemStack stack = super.createStack();
        ItemUtil.AddPotionColourMeta(stack, potionColor);
        return stack;
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
