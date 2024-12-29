package net.pwing.npc.movement;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityHeadRotation;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityPosition;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityPositionAndRotation;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class NPCMovement {
    private final int entityId;
    
    public NPCMovement(int entityId) {
        this.entityId = entityId;
    }
    
    public void moveTo(Location from, Location to, boolean onGround) {
        double deltaX = to.getX() - from.getX();
        double deltaY = to.getY() - from.getY();
        double deltaZ = to.getZ() - from.getZ();
        
        WrapperPlayServerEntityPositionAndRotation movePacket = new WrapperPlayServerEntityPositionAndRotation(
                entityId,
                deltaX,
                deltaY,
                deltaZ,
                to.getYaw(),
                to.getPitch(),
                onGround
        );
        
        WrapperPlayServerEntityHeadRotation headPacket = new WrapperPlayServerEntityHeadRotation(
                entityId,
                to.getYaw()
        );
        
        to.getWorld().getPlayers().forEach(player -> {
            if (player.getLocation().distance(to) <= 48.0) {
                PacketEvents.getAPI().getPlayerManager().sendPacket(player, movePacket);
                PacketEvents.getAPI().getPlayerManager().sendPacket(player, headPacket);
            }
        });
    }
}
