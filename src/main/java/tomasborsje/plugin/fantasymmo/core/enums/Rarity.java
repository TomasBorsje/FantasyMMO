package tomasborsje.plugin.fantasymmo.core.enums;

import org.bukkit.ChatColor;

public enum Rarity {
    TRASH(ChatColor.GRAY),
    COMMON(ChatColor.WHITE),
    UNCOMMON(ChatColor.GREEN),
    RARE(ChatColor.BLUE),
    EPIC(ChatColor.DARK_PURPLE),
    LEGENDARY(ChatColor.GOLD);

    private final ChatColor color;
    Rarity(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }
}
