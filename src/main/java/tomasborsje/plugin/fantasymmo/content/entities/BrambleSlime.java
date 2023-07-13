package tomasborsje.plugin.fantasymmo.content.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.content.items.holders.CustomItems;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.util.CustomLootTable;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;

public class BrambleSlime extends CustomEntity {
    private static final CustomLootTable lootTable =
            new CustomLootTable(CustomItems.SLIMEBALL, 0.8);
    public BrambleSlime(Location location) {
        super(location);
        this.name = "Bramble Slime";
        this.id = "BRAMBLE_SLIME";
        this.level = 1;
        this.currentHealth = 40;
        this.maxHealth = 40;
        this.attackDamage = 5;
        this.killMoney = ItemUtil.Value(0, 0, 2);
    }

    @Override
    public LivingEntity getNMSEntity(Location location) {
        Level level = ((CraftWorld)location.getWorld()).getHandle();
        Slime slime = new Slime(EntityType.SLIME, level);
        slime.setSize(2, true);
        slime.setPos(location.getX(), location.getY(), location.getZ());

        return slime;
    }

    @Override
    public ItemStack[] getDroppedLoot(Player player) {
        return lootTable.roll(player);
    }

    @Override
    public int getKillXp() {
        return 33;
    }
}
