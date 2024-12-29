package net.pwing.npc.packet;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;

public class NPCMetadataHandler {
    public static WrapperPlayServerEntityMetadata createMetadataPacket(int entityId, String displayName, boolean nameVisible) {
        EntityData[] metadata = {
            // Set display name
            new EntityData(2, EntityData.ValueType.OPTIONAL_CHAT, displayName),
            // Set name visibility
            new EntityData(3, EntityData.ValueType.BOOLEAN, nameVisible)
        };
        
        return new WrapperPlayServerEntityMetadata(entityId, metadata);
    }
}
