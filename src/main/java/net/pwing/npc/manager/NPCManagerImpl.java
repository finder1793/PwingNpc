package net.pwing.npc.manager;

import net.pwing.npc.PwingNpc;
import net.pwing.npc.api.NPC;
import net.pwing.npc.api.NPCManager;
import net.pwing.npc.npc.NPCData;
import net.pwing.npc.npc.PacketNPC;
import org.bukkit.Location;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NPCManagerImpl implements NPCManager {
    private final PwingNpc plugin;
    private final Map<String, NPC> npcs;
    
    public NPCManagerImpl(PwingNpc plugin) {
        this.plugin = plugin;
        this.npcs = new HashMap<>();
    }
    
    @Override
    public NPC createNPC(String name, Location location) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        NPCData data = new NPCData(id, name, location);
        NPC npc = new PacketNPC(data);
        npcs.put(id, npc);
        plugin.getConfigManager().saveNPC(npc);
        return npc;
    }
    
    @Override
    public NPC getNPC(String id) {
        return npcs.get(id);
    }
    
    @Override
    public Collection<NPC> getNPCs() {
        return npcs.values();
    }
    
    @Override
    public void removeNPC(String id) {
        NPC npc = npcs.remove(id);
        if (npc != null) {
            npc.remove();
            plugin.getConfigManager().removeNPC(id);
        }
    }
    
    public void loadNPCs() {
        plugin.getConfigManager().loadAllNPCs().forEach((id, npcData) -> {
            NPC npc = new PacketNPC(npcData);
            npcs.put(id, npc);
            plugin.getConfigManager().loadNPC(npc);
            if (npcData.isSpawnOnLoad()) {
                npc.spawn();
            }
        });
    }
}