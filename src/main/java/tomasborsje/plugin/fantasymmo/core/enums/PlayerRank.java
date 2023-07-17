package tomasborsje.plugin.fantasymmo.core.enums;

import org.bukkit.ChatColor;

public enum PlayerRank {
    NORMAL(ChatColor.GREEN, ""),
    DONATOR(ChatColor.YELLOW, ChatColor.GOLD+""+ChatColor.BOLD+"⭐"),
    GAME_MASTER(ChatColor.BLUE, ChatColor.BLUE+""+ChatColor.BOLD+"GM ");

    private final ChatColor color;
    private final String prefix;

    PlayerRank(ChatColor color, String prefix) {
        this.color = color;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public ChatColor getColor() {
        return color;
    }
}
