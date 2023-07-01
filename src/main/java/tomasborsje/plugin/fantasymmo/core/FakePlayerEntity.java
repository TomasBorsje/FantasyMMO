package tomasborsje.plugin.fantasymmo.core;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import tomasborsje.plugin.fantasymmo.core.enums.FakePlayerSkin;
import tomasborsje.plugin.fantasymmo.core.util.TooltipUtil;

import java.util.List;
import java.util.UUID;

/**
 * NMS Entity for a fake player entity.
 */
public class FakePlayerEntity extends ServerPlayer {
    private final ItemStack heldItem;
    long ticksAlive = 0;
    private static final int LOOK_DISTANCE = 4;
    private static final int LOOK_TIMER = 4;

    /**
     * Creates a new fake player entity.
     * @param loc The location to spawn the entity at
     * @param name The name of the fake player
     * @param skin The skin of the fake player
     * @param heldItem The item the fake player is holding. Can be null or empty.
     */
    public FakePlayerEntity(Location loc, String name, FakePlayerSkin skin, ItemStack heldItem) {
        // Pass world and game profile to vanilla constructor
        super(((CraftWorld)loc.getWorld()).getHandle().getServer(),((CraftWorld)loc.getWorld()).getHandle(),
                new GameProfile(UUID.randomUUID(), TooltipUtil.getNPCDisplayPlate(name)));
        // Set player position
        this.setPos(loc.getX(), loc.getY(), loc.getZ());
        // Add our skin
        this.getGameProfile().getProperties().put("textures", skin.texture);
        // Set held item
        this.heldItem = heldItem;
        // Create dummy connection, needed for interaction
        this.connection = new ServerGamePacketListenerImpl(this.getServer(), new Connection(PacketFlow.CLIENTBOUND), this);
        // Set creative mode so this npc doesn't take damage
        this.setGameMode(GameType.CREATIVE);
        // Spawn for every logged in player
        for (Player player : this.level.players()) {
            spawnForPlayer((ServerPlayer) player);
        }
    }

    /**
     * Spawns the fake player in world for the given real player.
     * @param p The player to spawn the fake player for
     */
    public void spawnForPlayer(org.bukkit.entity.Player p) {
        spawnForPlayer(((CraftPlayer)p).getHandle());
    }

    /**
     * Spawns the fake player in world for the given real player.
     * @param p The NMS player to spawn the fake player for
     */
    public void spawnForPlayer(ServerPlayer p) {
        var connection = p.connection;

        // Spawn player in world
        connection.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, this));
        connection.send(new ClientboundAddPlayerPacket(this));
        connection.send(new ClientboundAddEntityPacket(this));
        if(this.heldItem != null && !this.heldItem.isEmpty()) {
            connection.send(new ClientboundSetEquipmentPacket(this.getBukkitEntity().getEntityId(), List.of(Pair.of(EquipmentSlot.MAINHAND, heldItem))));
        }
    }

    public void hideForPlayer(org.bukkit.entity.Player p) {
        hideForPlayer(((CraftPlayer)p).getHandle());
    }

    private void hideForPlayer(ServerPlayer player) {
        player.connection.send(new ClientboundRemoveEntitiesPacket(this.getId()));
    }

    /**
     * Makes the NPC look at the given player.
     * Note: This will only be visible to the given player!
     * Each player will see the NPC looking at them.
     * @param p The player to look at
     */
    public void lookAtPlayer(ServerPlayer p) {
        // Don't look at ourselves.
        if(p.getId() == this.getId()) { return; }

        var connection = p.connection;

        // Calc our and player locations, so we can use their yaw/pitch code
        Location thisLoc = getBukkitEntity().getEyeLocation();
        Location playerLoc = p.getBukkitEntity().getEyeLocation();
        // Calculate direction
        Location direction = thisLoc.setDirection(playerLoc.subtract(thisLoc).toVector());

        // Calc yaw and pitch
        // TODO: Optimise this, don't need to make locations when we can just do math
        byte yaw = (byte) (((direction.getYaw())%360.0)*256/360);
        byte pitch = (byte) ((direction.getPitch()%360.0)*256/360);

        // Horizontal head movement
        connection.send(new ClientboundRotateHeadPacket(this, yaw));
        // Body movement and vertical head movement
        connection.send(new ClientboundMoveEntityPacket.Rot(this.getBukkitEntity().getEntityId(), yaw, pitch, false));
        // Set held item
        if(this.heldItem != null && !this.heldItem.isEmpty()) {
            connection.send(new ClientboundSetEquipmentPacket(this.getBukkitEntity().getEntityId(), List.of(Pair.of(EquipmentSlot.MAINHAND, heldItem))));
        }
    }

    /**
     * Ticks our custom player. Completely overrides the vanilla tick method,
     * as this is a fake player.
     */
    @Override
    public void tick() {
        ticksAlive++;
        // Look at nearby players every 5 ticks
        if(ticksAlive % LOOK_TIMER == 0) {
            this.level.getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(LOOK_DISTANCE)).forEach(this::lookAtPlayer);
        }
    }
}
