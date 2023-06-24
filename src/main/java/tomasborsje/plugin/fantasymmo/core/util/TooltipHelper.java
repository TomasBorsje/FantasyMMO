package tomasborsje.plugin.fantasymmo.core.util;

import org.bukkit.ChatColor;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.StatBoost;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.*;

import java.util.ArrayList;
import java.util.List;

public class TooltipHelper {
    private static final String empty = "";
    public static final String strengthIcon = "\uD83D\uDDE1";
    public static final String intelligenceIcon = "☆";
    public static final String healthIcon = "❤";
    public static final String defenseIcon = "\uD83D\uDEE1";
    public static List<String> getTooltip(ICustomItem item) {
        List<String> tooltip = new ArrayList<String>(2);

        // Add item rating in yellow text if item has a rating
        if(item instanceof IHasItemScore itemLevelProvider) {
            // Note we put this right under the item's name
            tooltip.add(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Item Score " + itemLevelProvider.getItemScore());
        }

        // Add stats if item gives stats
        if(item instanceof IStatsProvider statsProvider) {
            tooltip.add(empty);

            StatBoost stats = statsProvider.getStats();
            if(stats.defense != 0) {
                tooltip.add(ChatColor.WHITE + defenseIcon + " Defense: " + getSign(stats.defense) + stats.defense);
            }
            if(stats.health != 0) {
                tooltip.add(ChatColor.GREEN + healthIcon + " Health: " + getSign(stats.health) + stats.health);
            }
            if(stats.strength != 0) {
                tooltip.add(ChatColor.RED + strengthIcon + " Strength: " + getSign(stats.strength) + stats.strength);
            }
            if(stats.intelligence != 0) {
                tooltip.add(ChatColor.BLUE + intelligenceIcon + " Intelligence: " + getSign(stats.intelligence) + stats.intelligence);
            }
        }

        // Add use description tooltips if they exist
        if(item instanceof IUsable usable) {
            String leftClick = usable.getLeftClickDescription();
            String rightClick = usable.getRightClickDescription();

            if(!leftClick.isEmpty() || !rightClick.isEmpty()) {
                tooltip.add(empty);
            }

            if(!leftClick.isEmpty()) {
                tooltip.add(ChatColor.YELLOW + empty + ChatColor.BOLD + "LEFT CLICK: " + ChatColor.RESET + empty + ChatColor.WHITE + leftClick);
            }
            if(!rightClick.isEmpty()) {
                tooltip.add(ChatColor.YELLOW + empty + ChatColor.BOLD + "RIGHT CLICK: " + ChatColor.RESET + empty + ChatColor.WHITE + rightClick);
            }
        }

        // Add lore tooltip if it exists
        if(item instanceof IHasDescription description) {
            tooltip.add(empty);

            String[] lines = description.getDescription().split("\n");
            for(String line : lines) {
                tooltip.add(ChatColor.GRAY +""+ ChatColor.ITALIC + line);
            }
        }

        // Add value indicator
        int value = item.getValue();
        if(value > 0) {
            tooltip.add(empty);
            tooltip.add(ChatColor.WHITE + "Value: " + GetValueString(item.getValue()));
        }

        // Add rarity indicator and item type label
        Rarity rarity = item.getRarity();
        ItemType type = item.getType();
        tooltip.add("");
        tooltip.add(rarity.getColor()+empty+ChatColor.BOLD+"- "+rarity.name()+ (type == ItemType.MISC ? "" : " " + type.name()) + " -");

        return tooltip;
    }

    public static String getSign(int value) {
        // No negative sign for negative values as they already add the plus in ToString
        return value >= 0 ? "+" : "";
    }

    public static String getDisplayPlate(CustomEntity customEntity) {
        // TODO: Colors per level range
        return ChatColor.WHITE+"["+ChatColor.GOLD+customEntity.level+ChatColor.WHITE+"] "
                + ChatColor.GREEN + customEntity.name + ChatColor.WHITE + " "
                + customEntity.currentHealth + "/" + customEntity.maxHealth;
    }

    public static String getItemDisplayName(ICustomItem item) {
        return item.getRarity().getColor() + item.getName();
    }

    public static String GetValueString(int value) {
        int gold = Math.floorDiv(value, 10000);
        int silver = Math.floorDiv(value - gold * 10000, 100);
        int copper = value - gold * 10000 - silver * 100;

        return (gold > 0 ? (ChatColor.YELLOW + "◎"+ gold+" ") : "")
                + (silver > 0 ? ChatColor.GRAY+ "◎" + silver+" " : "")
                + ChatColor.GOLD + "◎" + copper;
    }
}
