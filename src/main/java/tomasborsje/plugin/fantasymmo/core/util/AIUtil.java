package tomasborsje.plugin.fantasymmo.core.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;

public class AIUtil {
    /**
     * Disables knockback and removes all default pathfinding goals from the entity.
     * @param entity The entity to reset.
     */
    public static void Reset(PathfinderMob entity) {
        // Disable knockback
        entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);

        // Remove all goals so the entity doesn't do anything
        entity.removeAllGoals((goal -> true));

        // Remove all target selection goals so the entity doesn't attack anything
        entity.targetSelector.removeAllGoals((goal -> true));

        // Make the entity float in water
        entity.goalSelector.addGoal(0, new FloatGoal(entity));

        // Make the entity look around randomly
        // We don't look at players as that is intensive
        entity.goalSelector.addGoal(15, new RandomLookAroundGoal(entity));
    }

    /**
     * Sets the attack pathfinding radius of the entity.
     * Should not be too high as it will cause lag.
     * Note this is also used as the evasion radius of a mob.
     * @param entity The entity to set the radius of.
     * @param radius The radius to set.
     */
    public static void SetFollowRadius(LivingEntity entity, double radius) {
        if(entity.getAttribute(Attributes.FOLLOW_RANGE) != null) {
            entity.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(radius);
        }
    }

    /**
     * Disables knockback for the entity.
     * @param entity The entity to disable knockback for.
     */
    public static void DisableKnockback(LivingEntity entity) {
        AttributeInstance knockbackResistance = entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if(knockbackResistance != null) {
            knockbackResistance.setBaseValue(1.0);
        }
    }
}
