package net.pwing.npc.api;

import net.pwing.npc.trait.Trait;
import org.bukkit.Location;

import java.util.Collection;

public interface NPC {
    String getId();
    
    String getName();
    
    Location getLocation();
    
    int getEntityId();
    
    boolean isSpawned();
    
    void spawn();
    
    void despawn();
    
    void remove();
    
    void addTrait(Trait trait);
    
    Trait getTrait(String name);
    
    Collection<Trait> getTraits();
    
    boolean hasTrait(String name);
    
    void removeTrait(String name);
}
