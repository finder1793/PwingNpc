package net.pwing.npc.visibility;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

public class PacketVisibilityHandler extends PacketListenerAbstract {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() == PacketType.Play.Server.SPAWN_ENTITY) {
            WrapperPlayServerSpawnEntity packet = new WrapperPlayServerSpawnEntity(event);
            
            if (packet.getEntityType() != EntityTypes.PLAYER) {
                return;
            }

            Player player = (Player) event.getPlayer();
            int entityId = packet.getEntityId();

            for (NPC npc : CitizensAPI.getNPCRegistry()) {
                if (!npc.isSpawned() || npc.getEntity().getEntityId() != entityId) {
                    continue;
                }

                if (!npc.hasTrait(VisibilityTrait.class)) {
                    continue;
                }

                VisibilityTrait trait = npc.getTrait(VisibilityTrait.class);
                if (!trait.canSee(player)) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
