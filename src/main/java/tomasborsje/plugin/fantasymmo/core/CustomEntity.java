package tomasborsje.plugin.fantasymmo.core;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.entity.Entity;
import tomasborsje.plugin.fantasymmo.core.enums.CustomDamageType;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasId;
import tomasborsje.plugin.fantasymmo.core.util.TooltipHelper;

/**
 * This class holds the custom entity information of an NMS entity.
 */
public abstract class CustomEntity implements IHasId {

    public LivingEntity nmsEntity;
    public int maxHealth = 40;
    public int currentHealth = 40;
    public String id;
    public int level;
    public String name;

    public CustomEntity(Location location) {
        this.nmsEntity = getNMSEntity(location);
    }

    /**
     * Returns the health to set the NMS entity's health to
     * as a 0-100% scale of the custom entity's health.
     * @return The health to set the NMS entity's health to
     */
    protected float getScaledEntityHealth() {
        return (float) (nmsEntity.getAttribute(Attributes.MAX_HEALTH).getValue() * currentHealth / maxHealth);
    }

    public abstract LivingEntity getNMSEntity(Location location);

    /**
     * Reduce this custom entity's health by the given amount and damage type.
     * Applies armor, etc. if applicable.
     * @param source The entity that is dealing the damage
     * @param type The type of damage being dealt
     * @param amount The amount of damage being dealt
     * @return The amount of damage that was dealt
     */
    public float hurt(Entity source, CustomDamageType type, int amount) {
        currentHealth -= amount;
        if(currentHealth <= 0) {
            currentHealth = 0;
        }
        nmsEntity.setHealth(getScaledEntityHealth());
        return amount;
    }

    /**
     * Increase this custom entity's health by an amount.
     * @param amount The amount to increase the health by
     * @return The amount the health was increased by
     */
    public float heal(int amount) {
        currentHealth += amount;
        if(currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
        nmsEntity.setHealth(getScaledEntityHealth());
        return amount;
    }

    /**
     * Returns the level of this custom entity.
     */
    public void tick() {
        // Update name plate to reflect health, etc.
        nmsEntity.setCustomName(CraftChatMessage.fromStringOrNull(TooltipHelper.getDisplayPlate(this)));
    }
    public String getCustomId() {
        return id;
    }
}
