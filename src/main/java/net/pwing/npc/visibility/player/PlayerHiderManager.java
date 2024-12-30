package net.pwing.npc.visibility.player;

import net.pwing.npc.visibility.PwingVisibilityPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PlayerHiderManager {
    private final PwingVisibilityPlugin plugin;
    private final Map<String, Set<UUID>> hiddenGroups = new HashMap<>();
    
    public PlayerHiderManager(PwingVisibilityPlugin plugin) {
        this.plugin = plugin;
    }
    
    public void createHiddenGroup(String name) {
        hiddenGroups.putIfAbsent(name, new HashSet<>());
    }
    
    public void addPlayerToGroup(Player player, String group) {
        hiddenGroups.computeIfAbsent(group, k -> new HashSet<>()).add(player.getUniqueId());
        updateVisibility(player);
    }
    
    public void removePlayerFromGroup(Player player, String group) {
        if (hiddenGroups.containsKey(group)) {
            hiddenGroups.get(group).remove(player.getUniqueId());
            updateVisibility(player);
        }
    }
    
    private final Set<UUID> globallyHidden = new HashSet<>();

    public void hidePlayer(Player player) {
        globallyHidden.add(player.getUniqueId());
        updateVisibility(player);
    }

    public void showPlayer(Player player) {
        globallyHidden.remove(player.getUniqueId());
        updateVisibility(player);
    }


    public boolean canSee(Player viewer, Player target) {
        if (globallyHidden.contains(target.getUniqueId()) && 
            !viewer.hasPermission("pwingnpc.see.hidden")) {
            return false;
        }
        for (Map.Entry<String, Set<UUID>> entry : hiddenGroups.entrySet()) {
            if (entry.getValue().contains(target.getUniqueId()) && 
                !viewer.hasPermission("pwingnpc.see." + entry.getKey())) {
                return false;
            }
        }
        return true;
    }
    
    private void updateVisibility(Player player) {
        for (Player online : plugin.getServer().getOnlinePlayers()) {
            if (!canSee(online, player)) {
                online.hidePlayer(plugin, player);
            } else {
                online.showPlayer(plugin, player);
            }
        }
    }
    public Map<String, Set<UUID>> getGroups() {
        return hiddenGroups;
    }

    public void addPlayerToGroup(UUID uuid, String group) {
        hiddenGroups.computeIfAbsent(group, k -> new HashSet<>()).add(uuid);
    }


    public Set<UUID> getGroupPlayers(String group) {
        return hiddenGroups.get(group);
    }
}
