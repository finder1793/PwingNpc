package net.pwing.npc.skin;

import com.github.retrooper.packetevents.protocol.player.TextureProperty;

public class NPCSkin {
    private final String value;
    private final String signature;

    public NPCSkin(String value, String signature) {
        this.value = value;
        this.signature = signature;
    }

    public TextureProperty toTextureProperty() {
        return new TextureProperty("textures", value, signature);
    }
}
