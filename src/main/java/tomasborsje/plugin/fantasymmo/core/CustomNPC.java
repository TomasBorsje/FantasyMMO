package tomasborsje.plugin.fantasymmo.core;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.core.enums.FakePlayerSkin;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasId;
import tomasborsje.plugin.fantasymmo.core.util.SoundUtil;

public abstract class CustomNPC implements IHasId {
    public String id;
    public String name;
    public Location location;
    protected ItemStack heldItem;
    protected float voicePitch = 0.7f;
    public Entity nmsEntity;

    /**
     * Create a new instance of this entity at the given location.
     * Make sure you set id, name, location, etc. in any derived constructors.
     * @param loc The location to spawn the entity at
     */
    public CustomNPC(Location loc) {
        this.location = loc;
    }

    public void tick() {
        // CustomTick our NPC entity if they're a fake player
        if(nmsEntity instanceof FakePlayerEntity fakePlayer) {
            fakePlayer.tick();
        }
        // Otherwise the game itself will call normal Entity#tick
    }

    public void spawnInWorld() {
        nmsEntity = getNmsEntity();
        ((CraftWorld)location.getWorld()).getHandle().addFreshEntity(nmsEntity);
    }

    /**
     * Returns an NMS entity to use as this NPC. Override this to change the entity type.
     * @return The NMS entity
     */
    protected Entity getNmsEntity() {
        return new FakePlayerEntity(location, name, FakePlayerSkin.MECHANIC, heldItem);
    }

    /**
     * If this is a fake player, shows it to the given player.
     * @param player The player to show the fake player to
     */
    public void spawnForPlayer(Player player) {
        if(nmsEntity instanceof FakePlayerEntity fakePlayer) {
            fakePlayer.spawnForPlayer(player);
        }
    }

    /**
     * If this is a fake player, hides it from the given player.
     * @param player The player to hide the fake player from
     */
    public void hideForPlayer(Player player) {
        if(nmsEntity instanceof FakePlayerEntity fakePlayer) {
            fakePlayer.hideForPlayer(player);
        }
    }

    /**
     * Fully removes the entity from existence.
     * You will not be able to show it to players, etc.
     */
    public void remove() {
        nmsEntity.remove(Entity.RemovalReason.DISCARDED);
    }

    public void interact(PlayerData playerData) {
        SoundUtil.PlayNPCInteractSound(playerData.player, voicePitch);
    }

    @Override
    public String getCustomId() {
        return id;
    }
}
