package tomasborsje.plugin.fantasymmo.entities.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * A goal that makes the entity wander around a specific location.
 * TODO: Avoid wandering into water.
 */
public class LeashedWanderGoal extends RandomStrollGoal {
    private final static int DEFAULT_WANDER_RANGE = 15;
    private final Vec3 centerPos;
    private final double range;

    /**
     * Creates a new leashed wander goal with the specified location, wander range, and move speed modifier.
     * @param entity The entity to apply the goal to.
     * @param x The x coordinate of the center of the wander area.
     * @param y The y coordinate of the center of the wander area.
     * @param z The z coordinate of the center of the wander area.
     * @param range The range of the wander area.
     * @param speed The speed modifier to apply to the entity's wandering movement.
     */
    public LeashedWanderGoal(PathfinderMob entity, double x, double y, double z, double range, double speed) {
        super(entity, speed);
        centerPos = new Vec3(x, y, z);
        this.range = range;
    }

    /**
     * Creates a new leashed wander goal with the specified location and wander range.
     * Uses a default move speed modifier of 1.
     * @param entity The entity to apply the goal to.
     * @param x The x coordinate of the center of the wander area.
     * @param y The y coordinate of the center of the wander area.
     * @param z The z coordinate of the center of the wander area.
     * @param range The range of the wander area.
     */
    public LeashedWanderGoal(PathfinderMob entity, double x, double y, double z, double range) {
        this(entity, x, y, z, range, 1);
    }

    /**
     * Creates a new leashed wander goal with the specified location and wander range.
     * Uses a default move speed modifier of 1 and a default wander range of 15.
     * @param entity The entity to apply the goal to.
     * @param x The x coordinate of the center of the wander area.
     * @param y The y coordinate of the center of the wander area.
     * @param z The z coordinate of the center of the wander area.
     */
    public LeashedWanderGoal(PathfinderMob entity, double x, double y, double z) {
        this(entity, x, y, z, DEFAULT_WANDER_RANGE, 1);
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        return centerPos.add((Math.random()-0.5) * range, 0, (Math.random()-0.5) * range);
    }
}
