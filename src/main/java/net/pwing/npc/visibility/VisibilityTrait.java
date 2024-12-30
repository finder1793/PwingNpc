package net.pwing.npc.visibility;

import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@TraitName("visibility")
public class VisibilityTrait extends Trait {
    
    private Set<UUID> visibleTo = new HashSet<>();
    private String requiredPermission;
    private boolean defaultVisible = true;

    public VisibilityTrait() {
        super("visibility");
    }
    @Override
    public void onAttach() {
        PwingVisibilityPlugin plugin = (PwingVisibilityPlugin) CitizensAPI.getPlugin();
    }


    public boolean canSee(Player player) {
        if (requiredPermission != null && !player.hasPermission(requiredPermission))
            return false;
            
        if (visibleTo.contains(player.getUniqueId()))
            return true;
            
        return defaultVisible;
    }

    public void setPermission(String permission) {
        this.requiredPermission = permission;
    }

    public String getPermission() {
        return requiredPermission;
    }

    public boolean isDefaultVisible() {
        return defaultVisible;
    }

    public List<String> getVisiblePlayers() {
        List<String> players = new ArrayList<>();
        visibleTo.forEach(uuid -> players.add(uuid.toString()));
        return players;
    }

    public void addPlayer(Player player) {
        visibleTo.add(player.getUniqueId());
    }

    public void addPlayer(UUID uuid) {
        visibleTo.add(uuid);
    }

    public void removePlayer(Player player) {
        visibleTo.remove(player.getUniqueId());
    }

    public void setDefaultVisible(boolean visible) {
        this.defaultVisible = visible;
    }
}


