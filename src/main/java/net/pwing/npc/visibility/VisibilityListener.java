package net.pwing.npc.visibility;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class VisibilityListener implements Listener {
    
    private final PwingVisibilityPlugin plugin;
    
    public VisibilityListener(PwingVisibilityPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        for (NPC npc : CitizensAPI.getNPCRegistry()) {
            if (!npc.hasTrait(VisibilityTrait.class))
                continue;

            VisibilityTrait trait = npc.getTrait(VisibilityTrait.class);
            if (!trait.canSee(event.getPlayer())) {
                event.getPlayer().hideEntity(plugin, npc.getEntity());
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (NPC npc : CitizensAPI.getNPCRegistry()) {
            if (!npc.hasTrait(VisibilityTrait.class))
                continue;

            VisibilityTrait trait = npc.getTrait(VisibilityTrait.class);
            if (!trait.canSee(event.getPlayer())) {
                event.getPlayer().hideEntity(plugin, npc.getEntity());
            }
        }
    }
}