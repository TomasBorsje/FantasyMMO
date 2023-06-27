package tomasborsje.plugin.fantasymmo.core;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import tomasborsje.plugin.fantasymmo.core.enums.CustomDamageType;
import tomasborsje.plugin.fantasymmo.handlers.EntityHandler;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

public abstract class CustomProjectile {
    public Location pos;
    public Vector velocity;
    public int ticksAlive = 0;
    public int lifetime = 200;
    public boolean alive = true;
    protected final float projectileSize = 0.25f;

    protected final World world;
    protected final Entity owner;
    protected int maxPenetrate = 0;
    protected int damage = 5;
    protected double speed = 0.7f;
    protected boolean showDamage = true;
    protected CustomDamageType damageType = CustomDamageType.MAGIC;
    protected ArrayList<Integer> hitIds = new ArrayList<>();

    public CustomProjectile(@Nullable Entity owner, Location startPos, Vector direction) {
        this.owner = owner;
        this.pos = startPos;
        this.velocity = direction.normalize().multiply(speed);
        this.world = startPos.getWorld();
    }

    public void tick() {
        if(!alive) { return; }

        ticksAlive++;

        if(ticksAlive > lifetime) {
            kill();
            return;
        }

        // Move the projectile
        pos = pos.add(velocity);

        // Check for collisions
        // If we hit a block, kill
        Block blockAtBullet = world.getBlockAt(pos);
        if(!blockAtBullet.isPassable()) {
            kill();
            return;
        }

        // Get hit entities
        Collection<Entity> hitEntities = world.getNearbyEntities(pos, projectileSize, projectileSize, projectileSize);

        // Get all custom entities we haven't hit yet
        var targets = (hitEntities.stream().filter((entity) ->
                EntityHandler.instance.hasEntity(entity.getEntityId()) && !hitIds.contains(entity.getEntityId())));

        targets.forEach((target) -> {
            // Pass custom entity to projectile
            CustomEntity customTarget = EntityHandler.instance.getEntity(target.getEntityId());

            if(showDamage) {
                ((LivingEntity)target).damage(0);
            }
            hitEntity(customTarget);

            // store hit entities
            hitIds.add(target.getEntityId());
            if(hitIds.size()>maxPenetrate) {
                kill();
            }
        });

    }
    protected void hitEntity(CustomEntity hitEntity) {
        if(owner instanceof Player p) {
            // Mark combat as we hit an enemy
            PlayerHandler.instance.getPlayerData(p).markCombat();
        }
    };
    public void kill() {
        alive = false;
    }
}
