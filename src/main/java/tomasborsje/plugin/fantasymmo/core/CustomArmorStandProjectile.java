package tomasborsje.plugin.fantasymmo.core;

import net.minecraft.core.Rotations;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

public abstract class CustomArmorStandProjectile {
    public Location pos;
    public Vector velocity;
    public int ticksAlive = 0;
    public int lifetime = 200;
    public boolean alive = true;
    protected static final double Y_OFFSET = -1.975; // Necessary to use the stand head as center
    protected final ArmorStand stand;
    protected final float projectileSize = 0.25f;

    protected final World world;
    protected final Entity owner;
    protected int maxPenetrate = 0;
    // Per-instance vars
    protected double damage = 5;
    protected double speed = 0.7f;
    protected ArrayList<Integer> hitIds = new ArrayList<>();

    public CustomArmorStandProjectile(@Nullable Entity owner, Location startPos, Vector direction) {
        this.owner = owner;
        this.pos = startPos;
        this.velocity = direction.normalize().multiply(speed);
        this.world = startPos.getWorld();
        // Spawn armor stand
        Level level = ((CraftWorld)this.world).getHandle();
        stand = new ArmorStand(level, pos.getX(), pos.getY()+Y_OFFSET, pos.getZ());
        stand.setItemSlot(EquipmentSlot.HEAD, getRepresentation());
        stand.setMarker(true);
        stand.setInvisible(true);
        level.addFreshEntity(stand);
    }

    protected ItemStack getRepresentation() {
        return new ItemStack(Items.MAGMA_BLOCK);
    }

    public void tick() {
        if(!alive) {return;}

        ticksAlive++;

        if(ticksAlive > lifetime) {
            kill();
            return;
        }

        // Move the projectile
        pos = pos.add(velocity);
        // Move the representation armour stand
        stand.setPos(pos.getX(), pos.getY()+Y_OFFSET, pos.getZ());
        stand.setHeadPose(new Rotations(ticksAlive*5, -ticksAlive*5, ticksAlive*3));

        // Check for collisions
        Vec3 eyeHeight = stand.getEyePosition();

        // If we hit a block, kill
        Block blockAtBullet = world.getBlockAt((int) eyeHeight.x, (int) eyeHeight.y, (int) eyeHeight.z);
        if(!blockAtBullet.isPassable()) {
            kill();
            return;
        }

        // Get hit entities
        Collection<Entity> hitEntities = world.getNearbyEntities(new Location(world, eyeHeight.x, eyeHeight.y, eyeHeight.z), projectileSize, projectileSize, projectileSize);

        // If we hit 0 living entities, continue
        if(hitEntities.stream().anyMatch((entity) ->
                !(entity instanceof Player) && !(entity instanceof org.bukkit.entity.ArmorStand) && entity instanceof LivingEntity && !entity.isDead()
        &&      !hitIds.contains(entity.getEntityId())))
        {
            // We've reached an entity
            LivingEntity target = (LivingEntity) hitEntities.iterator().next();

            hitEntity(target);

            // Only penetrate targets if we hit

            hitIds.add(target.getEntityId());
            if(hitIds.size()>maxPenetrate) {
                kill();
            }
        }
    }
    protected abstract void hitEntity(LivingEntity hitEntity);
    public void kill() {
        alive = false;
        if(stand != null) {
            stand.kill();
        }
    }
}
