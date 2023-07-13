package tomasborsje.plugin.fantasymmo.entities.nms;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import tomasborsje.plugin.fantasymmo.core.util.AIUtil;
import tomasborsje.plugin.fantasymmo.entities.goals.LeashedWanderGoal;

/**
 * A custom wolf entity. It will roam around its spawn location and attack
 * nearby players that attack it.
 */
public class BasicNMSWolf extends Wolf {
    private final static double CHASE_SPEED = 1.35;
    private final static float LEAP_HEIGHT = 0.4f;
    public BasicNMSWolf(Level level, double centerX, double centerY, double centerZ) {
        super(EntityType.WOLF, level);

        AIUtil.Reset(this);
        AIUtil.SetFollowRadius(this, 20);

        // Leap at our target
        this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, LEAP_HEIGHT));
        // Melee attack our target
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, CHASE_SPEED, true));
        // Otherwise wander around our spawn location
        this.goalSelector.addGoal(3, new LeashedWanderGoal(this, centerX, centerY, centerZ));

        // Add a target goal to make the wolf target nearby players that attack it
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity entityLiving) {
        return 2*2;
    }
}
