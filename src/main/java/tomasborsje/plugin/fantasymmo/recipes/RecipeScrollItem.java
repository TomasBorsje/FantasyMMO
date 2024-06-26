package tomasborsje.plugin.fantasymmo.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomItem;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasDescription;
import tomasborsje.plugin.fantasymmo.core.interfaces.IUsable;
import tomasborsje.plugin.fantasymmo.core.enums.ProfessionType;
import tomasborsje.plugin.fantasymmo.core.util.TooltipUtil;

import java.util.List;

public class RecipeScrollItem extends AbstractCustomItem implements IUsable, IHasDescription {
    private final ProfessionType type;
    private final ICustomRecipe recipe;
    public RecipeScrollItem(ICustomRecipe recipe, Rarity rarity) {
        this.customId = "RECIPE_SCROLL_"+recipe.getCustomId();
        this.name = "Recipe Scroll ("+recipe.getOutputItem().getName()+")";
        this.recipe = recipe;
        this.type = recipe.getProfessionType();
        this.rarity = rarity;
        this.value = 1;
    }

    @Override
    public boolean rightClick(PlayerData playerData, ItemStack item) {
        // Get player data
        Player player = playerData.player;
        // Check if player already has recipe
        if (playerData.knownRecipeIds.contains(this.recipe.getCustomId())) {
            player.sendMessage(ChatColor.LIGHT_PURPLE+"You already know this recipe!");
            return false;
        }
        // Grant player recipe
        playerData.knownRecipeIds.add(this.recipe.getCustomId());
        player.sendMessage(ChatColor.LIGHT_PURPLE+"You learned a new recipe!");
        return true;
    }

    @Override
    public boolean canStack() {
        return false;
    }

    @Override
    public Item getBaseItem() {
        return Items.PAPER;
    }

    @Override
    public ItemType getType() {
        return ItemType.RECIPE_SCROLL;
    }

    public ProfessionType getProfessionType() {
        return type;
    }

    @Override
    public boolean isConsumable() {
        return true;
    }

    @Override
    public String getDescription() {
        String description = ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+ChatColor.UNDERLINE+"Unlocks the following recipe:\n";
        // Add item display name and lore
        description += TooltipUtil.getItemDisplayName(recipe.getOutputItem())+"\n";
        List<String> itemLore = TooltipUtil.getTooltip(recipe.getOutputItem());
        for (String line : itemLore) {
            description += line+"\n";
        }
        // Add material list
        description += "\n" + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Required materials:\n";
        for (IIngredient ingredient : recipe.getIngredients()) {
            description += ChatColor.WHITE+""+ingredient.getRequiredCount() +"x "+ingredient.getIngredientName();
        }
        return description;
    }
}
