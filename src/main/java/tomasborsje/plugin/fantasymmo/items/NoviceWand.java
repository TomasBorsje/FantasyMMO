package tomasborsje.plugin.fantasymmo.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomItem;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.StatBoost;
import tomasborsje.plugin.fantasymmo.core.enums.ItemType;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.*;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.entities.projectiles.FireballProjectile;
import tomasborsje.plugin.fantasymmo.handlers.ProjectileHandler;

public class NoviceWand extends AbstractCustomItem implements IUsable, IStatProvider, IHasDescription, IHasItemScore, IGlowingItem {
    private final StatBoost heldStats = new StatBoost().withIntelligence(3);
    public NoviceWand() {
        this.customId = "NOVICE_WAND";
        this.name = "Novice Wand";
        this.rarity = Rarity.UNCOMMON;
        this.value = ItemUtil.Value(0, 0, 50);
        this.baseItem = Items.STICK;
    }
    private final int manaCost = 10;
    private final int damage = 20;

    @Override
    public boolean rightClick(PlayerData playerData, ItemStack item) {
        // Get player data
        Player player = playerData.player;
        // Try and consume mana to cast a fireball
        if(playerData.tryConsumeMana(manaCost)) {
            int damage = (int) (playerData.spellDamageMultiplier * this.damage);
            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 0.6f, 1.3f);
            FireballProjectile proj = new FireballProjectile(player, damage, player.getEyeLocation(), player.getEyeLocation().getDirection());
            ProjectileHandler.instance.spawnProjectile(proj);
            return true;
        }
        return false;
    }

    @Override
    public boolean leftClick(PlayerData player, ItemStack item) {
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
        return "Cast a fireball dealing " + ChatColor.RED + damage + ChatColor.WHITE + " magic damage.";
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
}
