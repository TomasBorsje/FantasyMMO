package tomasborsje.plugin.fantasymmo.content.entities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.util.CustomLootTable;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.content.entities.nms.BasicNMSWolf;

public class ForestWolf extends CustomEntity {
    private static final CustomLootTable lootTable = CustomLootTable.EMPTY;

    public ForestWolf(Location location) {
        super(location);
        this.name = "Forest Wolf";
        this.id = "FOREST_WOLF";
        this.level = 1;
        this.currentHealth = 40;
        this.maxHealth = 40;
        this.attackDamage = 5;
        this.killMoney = ItemUtil.Value(0, 0, 2);
    }

    @Override
    public LivingEntity getNMSEntity(Location location) {
        Level level = ((CraftWorld)location.getWorld()).getHandle();
        Wolf wolf = new BasicNMSWolf(level, location.getX(), location.getY(), location.getZ());
        wolf.setPos(location.getX(), location.getY(), location.getZ());

        return wolf;
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
