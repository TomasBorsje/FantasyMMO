package tomasborsje.plugin.fantasymmo.entities.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;

public class ForestSlime extends CustomEntity {
    public ForestSlime(Location location) {
        super(location);
        this.name = "Forest Slime";
        this.level = 1;
        this.currentHealth = 20;
        this.maxHealth = 20;
        this.id = "FOREST_SLIME";
    }

    @Override
    public LivingEntity getNMSEntity(Location location) {
        Level level = ((CraftWorld)location.getWorld()).getHandle();
        Slime slime = new Slime(EntityType.SLIME, level);
        slime.setSize(2, true);
        slime.setPos(location.getX(), location.getY(), location.getZ());

        return slime;
    }
}
