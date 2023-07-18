package tomasborsje.plugin.fantasymmo.core;

import net.minecraft.core.Rotations;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import tomasborsje.plugin.fantasymmo.core.util.CustomHeads;

import javax.annotation.Nullable;

public abstract class CustomPlayerHeadProjectile extends CustomProjectile {
    protected static final double Y_OFFSET = -1.975 + 0.25; // Necessary to use the stand head as center, -0.25 to center the head itself
    protected ArmorStand stand;

    public CustomPlayerHeadProjectile(@Nullable Entity owner, Location startPos, Vector direction) {
        super(owner, startPos, direction);
        spawnArmorStand();
    }

    protected ItemStack getHeadItem() {
        return CraftItemStack.asNMSCopy(CustomHeads.GetFireHead());
    }

    /**
     * Spawns and assigns the armor stand with the player head in the world.
     * Uses getHeadItem() to get the itemstack to use as head.
     */
    protected void spawnArmorStand() {
        Level level = ((CraftWorld)this.world).getHandle();
        stand = new ArmorStand(level, pos.getX(), pos.getY()+Y_OFFSET, pos.getZ());
        stand.setItemSlot(EquipmentSlot.HEAD, getHeadItem());
        stand.setMarker(true);
        stand.setInvisible(true);
        level.addFreshEntity(stand);
    }

    @Override
    public void tick() {
        super.tick();
        // Move the representation armour stand
        stand.setPos(pos.getX(), pos.getY()+Y_OFFSET, pos.getZ());

        // TODO: Offset the position so the head doesn't move up and down as it rotates
        // Would allow for rotation around more than just Y axis
        stand.setHeadPose(new Rotations(0, -ticksAlive*5, 0));
    }

    @Override
    public void kill() {
        super.kill();
        if(stand != null) {
            stand.kill();
        }
    }
}
