package net.pwing.npc.packet;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import net.pwing.npc.api.NPCManager;

public class NPCPacketListener extends PacketListenerAbstract {
    private final NPCManager npcManager;

    public NPCPacketListener(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            WrapperPlayClientInteractEntity packet = new WrapperPlayClientInteractEntity(event);
            handleInteraction(packet);
        }
    }

    private void handleInteraction(WrapperPlayClientInteractEntity packet) {
        int entityId = packet.getEntityId();
        // Handle NPC interaction based on entity ID
    }
}
