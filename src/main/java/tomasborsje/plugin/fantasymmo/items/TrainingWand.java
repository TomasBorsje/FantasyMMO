package tomasborsje.plugin.fantasymmo.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.AbstractUsableItem;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.StatBoost;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.IGlowingItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasDescription;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasItemScore;
import tomasborsje.plugin.fantasymmo.core.interfaces.IStatsProvider;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.entities.projectiles.TrainingWandProjectile;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;
import tomasborsje.plugin.fantasymmo.handlers.ProjectileHandler;

public class TrainingWand extends AbstractUsableItem implements IStatsProvider, IHasDescription, IHasItemScore, IGlowingItem {
    private final StatBoost heldStats = new StatBoost(0, 13, 0, 0);
    public TrainingWand() {
        this.customId = "TRAINING_WAND";
        this.name = "Training Wand";
        this.rarity = Rarity.UNCOMMON;
        this.value = ItemUtil.Value(0, 0, 50);
        this.baseItem = Items.STICK;
    }

    private final int manaCost = 10;
    private final int damage = 20;

    @Override
    public boolean rightClick(Player player, ItemStack item) {
        // Get player data
        PlayerData playerData = PlayerHandler.instance.loadPlayerData(player);

        // Try and consume mana to cast a fireball
        if(playerData.tryConsumeMana(manaCost)) {
            int damage = (int) (playerData.spellDamageMultiplier * this.damage);
            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 0.6f, 1.3f);
            TrainingWandProjectile proj = new TrainingWandProjectile(player, damage, player.getEyeLocation(), player.getEyeLocation().getDirection());
            ProjectileHandler.instance.spawnProjectile(proj);
            return true;
        }
        return false;
    }

    @Override
    public boolean leftClick(Player player, ItemStack item) {
        return false;
    }

    @Override
    public int getTickCooldown() {
        return 20;
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
        return "Cast a fireball dealing " + ChatColor.RED + damage + ChatColor.WHITE + " fire damage.";
    }

    @Override
    public boolean canStack() {
        return false;
    }

    @Override
    public Item getBaseItem() {
        return Items.STICK;
    }

    @Override
    public ItemType getType() {
        return ItemType.WAND;
    }

    @Override
    public int getItemScore() {
        return 5;
    }
}
