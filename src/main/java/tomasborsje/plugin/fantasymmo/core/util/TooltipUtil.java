package tomasborsje.plugin.fantasymmo.core.util;

import org.bukkit.ChatColor;
import tomasborsje.plugin.fantasymmo.core.AbstractMeleeWeapon;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.StatBoost;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.*;
import tomasborsje.plugin.fantasymmo.recipes.RecipeScrollItem;

import java.util.ArrayList;
import java.util.List;

public class TooltipUtil {
    private static final String empty = "";
    public static final String strengthIcon = "\uD83D\uDDE1";
    public static final String intelligenceIcon = "☆";
    public static final String healthIcon = "❤";
    public static final String defenseIcon = "\uD83D\uDEE1";

    public static final String intelligenceLabel = ChatColor.BLUE + intelligenceIcon + " Intelligence";
    public static final String strengthLabel = ChatColor.RED + strengthIcon + " Strength";
    public static final String healthLabel = ChatColor.GREEN + healthIcon + " Health";
    public static final String defenseLabel = ChatColor.WHITE + defenseIcon + " Defense";

    public static List<String> getTooltip(ICustomItem item) {
        List<String> tooltip = new ArrayList<>(2);

        // Add item rating in yellow text if item has a rating
        if(item instanceof IHasItemScore itemLevelProvider) {
            // Note we put this right under the item's name
            tooltip.add(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Item Score " + itemLevelProvider.getItemScore());
        }

        // Add damage indicator if item is melee weapon
        if(item instanceof AbstractMeleeWeapon meleeWeapon) {
            tooltip.add(empty);
            tooltip.add(ChatColor.GOLD + "Damage: " + ChatColor.WHITE + meleeWeapon.getDamage());
        }

        // Add stats if item gives stats
        if(item instanceof IStatProvider statsProvider) {
            tooltip.add(empty);

            StatBoost stats = statsProvider.getStats();
            if(stats.defense != 0) {
                tooltip.add(ChatColor.WHITE + defenseIcon + " Defense: " + ChatColor.WHITE + getSign(stats.defense) + stats.defense);
            }
            if(stats.health != 0) {
                tooltip.add(ChatColor.GREEN + healthIcon + " Health: " + ChatColor.WHITE + getSign(stats.health) + stats.health);
            }
            if(stats.strength != 0) {
                tooltip.add(ChatColor.RED + strengthIcon + " Strength: " + ChatColor.WHITE + getSign(stats.strength) + stats.strength);
            }
            if(stats.intelligence != 0) {
                tooltip.add(ChatColor.BLUE + intelligenceIcon + " Intelligence: " + ChatColor.WHITE + getSign(stats.intelligence) + stats.intelligence);
            }
        }

        // Add on attack effect if item has one
        if(item instanceof AbstractMeleeWeapon meleeWeapon) {
            if(meleeWeapon.getAttackDescription() != null) {
                tooltip.add(empty);
                String[] attackDesc = meleeWeapon.getAttackDescription().split("\n");
                for(int i = 0; i < attackDesc.length; i++) {
                    if(i == 0) {
                        tooltip.add(ChatColor.YELLOW + empty + ChatColor.BOLD + "ON ATTACK: " + ChatColor.RESET + empty + ChatColor.WHITE + attackDesc[i]);
                    }
                    else {
                        tooltip.add(ChatColor.WHITE + attackDesc[i]);
                    }
                }
            }
        }

        // Add use description tooltips if they exist
        if(item instanceof IUsable usable) {
            String[] leftClick = usable.getLeftClickDescription().split("\n");
            String[] rightClick = usable.getRightClickDescription().split("\n");

            if(!leftClick[0].isEmpty() || !rightClick[0].isBlank()) {
                tooltip.add(empty);
            }

            // If the left click description exists, add it
            if(!leftClick[0].isBlank()) {
                for(int i = 0; i < leftClick.length; i++) {
                    if(i == 0) {
                        tooltip.add(ChatColor.YELLOW + empty + ChatColor.BOLD + "LEFT CLICK: " + ChatColor.RESET + empty + ChatColor.WHITE + leftClick[i]);
                    }
                    else {
                        tooltip.add(ChatColor.WHITE + leftClick[i]);
                    }
                }
            }
            // If the right click description exists, add it
            if(!rightClick[0].isBlank()) {
                for(int i = 0; i < rightClick.length; i++) {
                    if(i == 0) {
                        tooltip.add(ChatColor.YELLOW + empty + ChatColor.BOLD + "RIGHT CLICK: " + ChatColor.RESET + empty + ChatColor.WHITE + rightClick[i]);
                    }
                    else {
                        tooltip.add(ChatColor.WHITE + rightClick[i]);
                    }
                }
            }
        }

        // Add lore tooltip if it exists
        if(item instanceof IHasDescription description) {
            if(item instanceof RecipeScrollItem recipe) {
                // Add recipe type line
                tooltip.add(ChatColor.GRAY+recipe.getProfessionType().getTitleCase() + " Recipe");
            }
            else {
                // Add empty line for non-recipe scroll items
                tooltip.add(empty);
            }

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
        tooltip.add(rarity.getColor()+empty+ChatColor.BOLD+"- "+rarity.name()+ (type == ItemType.MISC ? "" : " " + type.name().replace("_", " ")) + " -");

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

    public static String getNPCDisplayPlate(String name) {
        return ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + name;
    }

    public static String getItemDisplayName(ICustomItem item) {
        // Legendary items have a bold name
        if(item.getRarity() == Rarity.LEGENDARY) {
            return item.getRarity().getColor() + "" + ChatColor.BOLD + item.getName();
        }
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
    public static String GetPlayerMoneyString(int value) {
        int gold = Math.floorDiv(value, 10000);
        int silver = Math.floorDiv(value - gold * 10000, 100);
        int copper = value - gold * 10000 - silver * 100;

        return (ChatColor.YELLOW + "◎" + gold + " "
                + (silver > 0 ? ChatColor.GRAY+ "◎" + silver+" " : "")
                + ChatColor.GOLD + "◎" + copper);
    }

    public static String GetValueStringItalic(int value) {
        int gold = Math.floorDiv(value, 10000);
        int silver = Math.floorDiv(value - gold * 10000, 100);
        int copper = value - gold * 10000 - silver * 100;

        return (gold > 0 ? (ChatColor.YELLOW + "" + ChatColor.ITALIC + "◎" + gold+" ") : "")
                + (silver > 0 ? ChatColor.GRAY + "" + ChatColor.ITALIC + "◎" + silver+" " : "")
                + ChatColor.GOLD + "" + ChatColor.ITALIC + "◎" + copper;
    }
}
