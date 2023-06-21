package tomasborsje.plugin.fantasymmo.entities.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class MobRestrictedWanderGoal extends RandomStrollGoal {
    private final Vec3 centerPos;
    private final double range;

    public MobRestrictedWanderGoal(PathfinderMob var0, Vec3 center, double range, double speed) {
        super(var0, speed);
        centerPos = center;
        this.range = range;
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        return centerPos.add(new Vec3((Math.random()-0.5) * range, 0, (Math.random()-0.5) * range));
    }
}
