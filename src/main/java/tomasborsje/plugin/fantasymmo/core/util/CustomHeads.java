package tomasborsje.plugin.fantasymmo.core.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class CustomHeads {
    static UUID fireHead = UUID.randomUUID();
    static String fireTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjkwMWYwNzg1ZDIyMmU5YWZhNTA1NmNkZGFmZGI3YTMzYTRhYTljODllOTEyZWU3MWYzM2I2ZDc2MzEwNTRkIn19fQ==";

    public static ItemStack GetFireHead() {
        ItemStack head = new ItemStack(org.bukkit.Material.PLAYER_HEAD);

        SkullMeta meta = (SkullMeta) head.getItemMeta();
        // set skull owner
        GameProfile profile = new GameProfile(fireHead, null);
        profile.getProperties().put("textures", new Property("textures", fireTexture));

        try
        {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);

        }
        catch (IllegalArgumentException|NoSuchFieldException|SecurityException | IllegalAccessException error)
        {
            error.printStackTrace();
        }

        head.setItemMeta(meta);
        return head;
    }
}
