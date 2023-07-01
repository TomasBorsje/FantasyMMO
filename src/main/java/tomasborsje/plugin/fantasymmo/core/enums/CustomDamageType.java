package tomasborsje.plugin.fantasymmo.core.enums;

import org.bukkit.ChatColor;

public enum CustomDamageType {
    PHYSICAL(ChatColor.YELLOW),
    FALL(ChatColor.WHITE),
    MAGIC(ChatColor.LIGHT_PURPLE),
    TRUE(ChatColor.WHITE);

    private final ChatColor damageColor;
    CustomDamageType(ChatColor color) {
        this.damageColor = color;
    }
    public ChatColor getDamageColor() {
        return damageColor;
    }
}
