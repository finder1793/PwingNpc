package net.pwing.npc.api;

import org.bukkit.Location;

import java.util.Collection;

public interface NPCManager {
    NPC createNPC(String name, Location location);
    
    NPC getNPC(String id);
    
    Collection<NPC> getNPCs();
    
    void removeNPC(String id);
}
