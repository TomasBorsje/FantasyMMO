package tomasborsje.plugin.fantasymmo.core.util;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import tomasborsje.plugin.fantasymmo.FantasyMMO;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IDyeable;
import tomasborsje.plugin.fantasymmo.core.interfaces.IGlowingItem;
import tomasborsje.plugin.fantasymmo.core.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.enchantments.GlowEnchantment;

import java.util.List;

public class ItemUtil {
    public static boolean IsCustomItem(ItemStack stack) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        return IsCustomItem(nmsStack);
    }

    public static boolean IsCustomItem(net.minecraft.world.item.ItemStack stack) {
        // Get NBT tag and check if our tag exists
        net.minecraft.nbt.CompoundTag nbt = stack.getTag();
        if (nbt == null) {
            return false;
        }
        return nbt.contains("ITEM_ID") && ItemRegistry.ITEMS.exists(nbt.getString("ITEM_ID"));
    }

    public static ICustomItem GetAsCustomItem(ItemStack stack) {
        if(!IsCustomItem(stack)) {
            throw new IllegalArgumentException("ItemStack is not a custom item!");
        }
        return ItemRegistry.ITEMS.get(GetCustomId(stack));
    }

    public static String GetCustomId(ItemStack stack) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        net.minecraft.nbt.CompoundTag nbt = nmsStack.getTag();
        return nbt.getString("ITEM_ID");
    }

    public static int Value(int gold, int silver, int copper) {
        return (gold * 10000) + (silver * 100) + copper;
    }

    public static ItemStack createDefaultStack(ICustomItem item) {
        net.minecraft.world.item.ItemStack nmsStack = new net.minecraft.world.item.ItemStack(item.getBaseItem());

        // Create new CompoundTag
        CompoundTag nbt = nmsStack.getOrCreateTag();
        // Add ITEM_ID tag
        nbt.putString("ITEM_ID", item.getCustomId());

        // Add UUID tag if not stackable
        if(!item.canStack()) {
            nbt.putUUID("UUID", java.util.UUID.randomUUID());
        }

        nmsStack.setTag(nbt);

        // Convert back to CraftItemStack
        ItemStack newStack = CraftItemStack.asBukkitCopy(nmsStack);

        // Get lore and display name
        List<String> lore = TooltipUtil.getTooltip(item);
        String displayName = TooltipUtil.getItemDisplayName(item);

        // Set lore tooltip
        ItemMeta meta = newStack.getItemMeta();
        meta.setLore(lore); // Set tooltip lore
        meta.setDisplayName(displayName); // Set colored name
        meta.setUnbreakable(true); // Unbreakable, we don't want durability bars (or do we?)
        // TODO: Durability?
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        // Add glow enchantment if marked
        if(item instanceof IGlowingItem) {
            meta.addEnchant(new GlowEnchantment(new NamespacedKey(FantasyMMO.Plugin, "glow")), 1, true);
        }
        // Add leather armour dye if dyeable
        if(item instanceof IDyeable dyeable) {
            ((LeatherArmorMeta)meta).setColor(dyeable.getColor());
        }

        newStack.setItemMeta(meta);

        return newStack;
    }
}
