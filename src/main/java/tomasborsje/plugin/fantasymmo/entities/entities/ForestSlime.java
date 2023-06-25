package tomasborsje.plugin.fantasymmo.entities.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.core.util.CustomLootTable;

public class ForestSlime extends CustomEntity {
    private static final CustomLootTable lootTable =
            new CustomLootTable(ItemRegistry.SLIMEBALL, 0.5)
                    .addEntry(ItemRegistry.MISTWEAVE_ROBE, 0.6)
                    .addEntry(ItemRegistry.TRAINING_WAND, 0.8);
    public ForestSlime(Location location) {
        super(location);
        this.name = "Forest Slime";
        this.id = "FOREST_SLIME";
        this.level = 1;
        this.currentHealth = 40;
        this.maxHealth = 40;
        this.attackDamage = 5;
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
