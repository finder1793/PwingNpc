package net.pwing.npc.npc;

import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfo;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import net.pwing.npc.api.NPC;
import net.pwing.npc.trait.Trait;
import org.bukkit.Location;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PacketNPC implements NPC {
    private final String id;
    private final String name;
    private final UUID uuid;
    private final int entityId;
    private Location location;
    private boolean spawned;
    private final Map<String, Trait> traits;

    public PacketNPC(NPCData data) {
        this.id = data.getId();
        this.name = data.getName();
        this.uuid = UUID.randomUUID();
        this.entityId = EntityIDManager.getNextEntityId();
        this.location = data.getLocation();
        this.traits = new HashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Location getLocation() {
        return location.clone();
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public boolean isSpawned() {
        return spawned;
    }

    @Override
    public void addTrait(Trait trait) {
        traits.put(trait.getName(), trait);
    }

    @Override
    public Trait getTrait(String name) {
        return traits.get(name);
    }

    @Override
    public Collection<Trait> getTraits() {
        return traits.values();
    }

    @Override
    public boolean hasTrait(String name) {
        return traits.containsKey(name);
    }

    @Override
    public void removeTrait(String name) {
        traits.remove(name);
    }

    @Override
    public void spawn() {
        if (spawned) return;

        UserProfile profile = new UserProfile(uuid, name);
        WrapperPlayServerPlayerInfo.PlayerData playerData = new WrapperPlayServerPlayerInfo.PlayerData(
                profile,
                GameMode.CREATIVE,
                0,
                null
        );

        WrapperPlayServerPlayerInfo addPlayerInfo = new WrapperPlayServerPlayerInfo(
                WrapperPlayServerPlayerInfo.Action.ADD_PLAYER,
                Collections.singletonList(playerData)
        );

        WrapperPlayServerSpawnEntity spawnEntity = new WrapperPlayServerSpawnEntity(
                entityId,
                uuid,
                EntityTypes.PLAYER,
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch(),
                0
        );

        location.getWorld().getPlayers().forEach(player -> {
            if (player.getLocation().distance(location) <= 48.0) {
                sendPackets(player, addPlayerInfo, spawnEntity);
            }
        });

        spawned = true;
    }

    @Override
    public void despawn() {
        if (!spawned) return;

        UserProfile profile = new UserProfile(uuid, name);
        WrapperPlayServerPlayerInfo.PlayerData playerData = new WrapperPlayServerPlayerInfo.PlayerData(
                profile,
                GameMode.CREATIVE,
                0,
                null
        );

        WrapperPlayServerPlayerInfo removePlayerInfo = new WrapperPlayServerPlayerInfo(
                WrapperPlayServerPlayerInfo.Action.REMOVE_PLAYER,
                Collections.singletonList(playerData)
        );

        WrapperPlayServerDestroyEntities destroyEntities = new WrapperPlayServerDestroyEntities(
                entityId
        );

        location.getWorld().getPlayers().forEach(player -> {
            sendPackets(player, destroyEntities, removePlayerInfo);
        });

        spawned = false;
    }

    @Override
    public void remove() {
        despawn();
    }

    private void sendPackets(Player player, Object... packets) {
        for (Object packet : packets) {
            PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
        }
    }
}