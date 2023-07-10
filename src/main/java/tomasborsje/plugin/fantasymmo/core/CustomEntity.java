package tomasborsje.plugin.fantasymmo.core;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftChatMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.enums.CustomDamageType;
import tomasborsje.plugin.fantasymmo.core.interfaces.IBuffable;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasId;
import tomasborsje.plugin.fantasymmo.core.util.HologramUtil;
import tomasborsje.plugin.fantasymmo.core.util.TooltipUtil;
import tomasborsje.plugin.fantasymmo.handlers.EntityDeathHandler;

import java.util.ArrayList;

/**
 * This class holds the custom entity information of an NMS entity.
 * TODO: Big big todo.
 * TODO: Create some interface that can be shared
 * TODO: between players and CustomEntities so we can easily add buffs, health, etc.
 */
public abstract class CustomEntity implements IHasId, IBuffable {
    public LivingEntity nmsEntity;
    public int maxHealth = 40;
    public int currentHealth = 40;
    public String id;
    public int level;
    public String name;
    public int attackDamage = 1;
    public int killMoney = 0;
    public final ArrayList<Buff> buffs = new ArrayList<>();

    public CustomEntity(Location location) {
        this.nmsEntity = getNMSEntity(location);
        this.nmsEntity.setCustomNameVisible(true);
    }

    @Override
    public void addBuff(Buff buff) {
        buffs.add(buff);
        buff.onApply(this);
    }

    @Override
    public void removeAllBuffs() {
        for(Buff buff : buffs) {
            buff.onRemove(this);
        }
        buffs.clear();
    }

    /**
     * Returns the health to set the NMS entity's health to
     * as a 0-100% scale of the custom entity's health.
     * @return The health to set the NMS entity's health to
     */
    protected float getScaledEntityHealth() {
        return (float) (nmsEntity.getAttribute(Attributes.MAX_HEALTH).getValue() * currentHealth / maxHealth);
    }

    /**
     * Completely reset the entity, resetting its health, buffs, etc.
     * Called when an entity is too far from its spawn point and is leashed back.
     */
    public void reset() {
        this.currentHealth = this.maxHealth;
        this.removeAllBuffs();
    }

    public abstract LivingEntity getNMSEntity(Location location);

    /**
     * Reduce this custom entity's health by the given amount and damage type.
     * Applies armor, etc. if applicable.
     * @param source The entity that is dealing the damage
     * @param type The type of damage being dealt
     * @param damageAmount The amount of damage being dealt
     * @return The amount of damage that was dealt
     */
    public float hurt(Entity source, CustomDamageType type, int damageAmount) {
        // Don't take damage if we're already dead
        if(currentHealth <= 0) {
            return 0;
        }

        // TODO: Calculate armor and damage reduction etc

        // Spawn damage indicator
        HologramUtil.SpawnDamageIndicator(this.nmsEntity.getBukkitEntity().getLocation().add(0, nmsEntity.getEyeHeight()+0.8, 0), 1, type, damageAmount);

        // Reduce health and die if we have 0 or less
        currentHealth -= damageAmount;
        if(currentHealth <= 0) {
            currentHealth = 0;
            onDeath(source, type, damageAmount);
        }
        nmsEntity.setHealth(getScaledEntityHealth());
        return damageAmount;
    }

    /**
     * Called when this entity kills another entity.
     * @param victim The entity that was killed
     */
    public void onKill(CustomEntity victim) {
        // Do nothing by default
    }

    public void onDeath(Entity source, CustomDamageType type, int killingDamage) {
        EntityDeathHandler.instance.handleDeath(this, source);
    }

    public ItemStack[] getDroppedLoot(Player player) {
        return new ItemStack[0];
    }

    public int getKillXp() {
        return 0;
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
        nmsEntity.setCustomName(CraftChatMessage.fromStringOrNull(TooltipUtil.getDisplayPlate(this)));
    }
    public String getCustomId() {
        return id;
    }
}
