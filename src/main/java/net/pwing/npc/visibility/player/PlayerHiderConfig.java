package net.pwing.npc.visibility.player;

import net.pwing.npc.visibility.PwingVisibilityPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerHiderConfig {
    private final PwingVisibilityPlugin plugin;
    private final PlayerHiderManager manager;

    public PlayerHiderConfig(PwingVisibilityPlugin plugin, PlayerHiderManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    public void saveGroups() {
        ConfigurationSection section = plugin.getConfig().createSection("player-groups");
        manager.getGroups().forEach((group, players) -> {
            List<String> uuids = new ArrayList<>();
            players.forEach(uuid -> uuids.add(uuid.toString()));
            section.set(group, uuids);
        });
        plugin.saveConfig();
    }

    public void loadGroups() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("player-groups");
        if (section == null) return;

        section.getKeys(false).forEach(group -> {
            List<String> uuids = section.getStringList(group);
            uuids.forEach(uuid -> manager.addPlayerToGroup(UUID.fromString(uuid), group));
        });
    }
}
