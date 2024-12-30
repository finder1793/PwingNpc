package net.pwing.npc.visibility;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class VisibilityConfig {
    private final PwingVisibilityPlugin plugin;

    public VisibilityConfig(PwingVisibilityPlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    public void saveNPCSettings(int npcId, VisibilityTrait trait) {
        String path = "npcs." + npcId;
        FileConfiguration config = plugin.getConfig();
        
        config.set(path + ".permission", trait.getPermission());
        config.set(path + ".visible-by-default", trait.isDefaultVisible());
        config.set(path + ".visible-to", trait.getVisiblePlayers());
        
        plugin.saveConfig();
    }

    public void loadNPCSettings(int npcId, VisibilityTrait trait) {
        String path = "npcs." + npcId;
        FileConfiguration config = plugin.getConfig();
        
        if (config.contains(path)) {
            trait.setPermission(config.getString(path + ".permission", ""));
            trait.setDefaultVisible(config.getBoolean(path + ".visible-by-default", true));
            
            List<String> players = config.getStringList(path + ".visible-to");
            players.forEach(uuid -> trait.addPlayer(UUID.fromString(uuid)));
        }
    }

    public boolean getDefaultVisible() {
        return plugin.getConfig().getBoolean("settings.default-visible", true);
    }

    public String getDefaultPermission() {
        return plugin.getConfig().getString("settings.default-permission", "");
    }
}
