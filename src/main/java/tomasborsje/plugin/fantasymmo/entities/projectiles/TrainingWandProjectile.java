package tomasborsje.plugin.fantasymmo.entities.projectiles;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.core.CustomProjectile;

public class TrainingWandProjectile extends CustomProjectile {

    public TrainingWandProjectile(Player player, int damage, Location startPos, Vector velocity) {
        super(player, startPos, velocity);
        this.damage = damage;
        this.lifetime = 50;
        this.maxPenetrate = 0;
    }

    @Override
    protected void hitEntity(CustomEntity hitEntity) {
        // Damage the entity
        hitEntity.hurt(owner, damageType, this.damage);
        // Play a sound
        world.playSound(pos, Sound.ENTITY_BLAZE_SHOOT, 0.6f, 1.7f);
    }

    @Override
    public void kill() {
        // Explode into fire particles on death
        this.world.spawnParticle(Particle.FLAME, pos, 20);
        super.kill();
    }

    @Override
    public void tick() {
        // Spawn flame particles
        for(int i = 0; i < 5; i++) {
            double xOffset = (Math.random()-0.5f)*0.5f;
            double yOffset = (Math.random()-0.5f)*0.5f;
            double zOffset = (Math.random()-0.5f)*0.5f;
            // Spawn either flame or smoke particle at 80-20 ratio
            this.world.spawnParticle(Math.random() < 0.2 ? Particle.SMOKE_NORMAL : Particle.FLAME, new Location(world, pos.getX()+xOffset, pos.getY()+yOffset, pos.getZ()+zOffset), 1,
                    0, 0, 0, 0);
        }
        // Tick
        super.tick();
    }
}
