package tomasborsje.plugin.fantasymmo.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.*;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.IGlowingItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasDescription;
import tomasborsje.plugin.fantasymmo.core.interfaces.IStatsProvider;
import tomasborsje.plugin.fantasymmo.handlers.ProjectileHandler;
import tomasborsje.plugin.fantasymmo.entities.projectiles.TrainingWandProjectile;

public class TrainingWand extends AbstractUsableItem implements IStatsProvider, IHasDescription, IGlowingItem {
    private final StatBoost heldStats = new StatBoost(0, 13, 0, 0);
    public TrainingWand() {
        this.customId = "TRAINING_WAND";
        this.name = "Training Wand";
        this.rarity = Rarity.UNCOMMON;
        this.value = 10724;
        this.baseItem = Items.STICK;
    }

    @Override
    public boolean rightClick(Player player, ItemStack item) {
        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 0.6f, 1.3f);
        TrainingWandProjectile proj = new TrainingWandProjectile(player, player.getEyeLocation(), player.getEyeLocation().getDirection());
        ProjectileHandler.instance.spawnProjectile(proj);
        return true;
    }

    @Override
    public boolean leftClick(Player player, ItemStack item) {
        return false;
    }

    @Override
    public StatBoost getStats() {
        return heldStats;
    }

    @Override
    public String getDescription() {
        return "A basic wand provided to mages in training.";
    }

    @Override
    public String getRightClickDescription() {
        return "Cast a fireball dealing " + ChatColor.RED + "500" + ChatColor.WHITE + " fire damage.";
    }

    @Override
    public Item getBaseItem() {
        return Items.STICK;
    }

    @Override
    public ItemType getType() {
        return ItemType.WAND;
    }
}
