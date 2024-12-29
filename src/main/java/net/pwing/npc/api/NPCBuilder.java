package net.pwing.npc.api;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class NPCBuilder {
    private String name;
    private Location location;
    private EntityType entityType = EntityType.PLAYER;
    
    public NPCBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    public NPCBuilder location(Location location) {
        this.location = location;
        return this;
    }
    
    public NPCBuilder entityType(EntityType type) {
        this.entityType = type;
        return this;
    }
    
    public NPC build(NPCManager manager) {
        // Implementation will come later
        return null;
    }
}
