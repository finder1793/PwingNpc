package net.pwing.npc.npc;

import net.pwing.npc.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class NPCData {
    private final String id;
    private final String name;
    private final Location location;
    private final Map<String, ConfigurationSection> traitData;
    private final boolean spawnOnLoad;
    
    public NPCData(String id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.traitData = new HashMap<>();
        this.spawnOnLoad = true;
    }
    
    public NPCData(ConfigurationSection section) {
        this.id = section.getName();
        this.name = section.getString("name");
        this.location = LocationUtil.deserializeLocation(section.getConfigurationSection("location"));
        this.traitData = loadTraitData(section.getConfigurationSection("traits"));
        this.spawnOnLoad = section.getBoolean("spawn-on-load", true);
    }
    
    private Map<String, ConfigurationSection> loadTraitData(ConfigurationSection traits) {
        Map<String, ConfigurationSection> data = new HashMap<>();
        if (traits != null) {
            for (String key : traits.getKeys(false)) {
                data.put(key, traits.getConfigurationSection(key));
            }
        }
        return data;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public Map<String, ConfigurationSection> getTraitData() {
        return traitData;
    }
    
    public boolean isSpawnOnLoad() {
        return spawnOnLoad;
    }
}