package net.pwing.npc.config;

import net.pwing.npc.PwingNpc;
import net.pwing.npc.api.NPC;
import net.pwing.npc.npc.NPCData;
import net.pwing.npc.trait.Trait;
import net.pwing.npc.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private final PwingNpc plugin;
    private final File configFile;
    private final File npcFile;
    private FileConfiguration config;
    private FileConfiguration npcs;
    
    public ConfigManager(PwingNpc plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        this.npcFile = new File(plugin.getDataFolder(), "npcs.yml");
        loadConfigs();
    }
    
    public void loadNPC(NPC npc) {
        ConfigurationSection npcSection = npcs.getConfigurationSection("npcs." + npc.getId());
        if (npcSection == null) return;

        ConfigurationSection traitsSection = npcSection.getConfigurationSection("traits");
        if (traitsSection == null) return;

        for (String traitName : traitsSection.getKeys(false)) {
            Trait trait = plugin.getTraitRegistry().createTrait(traitName);
            if (trait != null) {
                ConfigurationSection traitSection = traitsSection.getConfigurationSection(traitName);
                trait.onLoad(npc, traitSection);
                npc.addTrait(trait);
            }
        }
    }
    
    public Map<String, NPCData> loadAllNPCs() {
        Map<String, NPCData> npcDataMap = new HashMap<>();
        ConfigurationSection npcsSection = npcs.getConfigurationSection("npcs");
        
        if (npcsSection != null) {
            for (String id : npcsSection.getKeys(false)) {
                ConfigurationSection npcSection = npcsSection.getConfigurationSection(id);
                if (npcSection != null) {
                    npcDataMap.put(id, new NPCData(npcSection));
                }
            }
        }
        
        return npcDataMap;
    }
    
    public void saveNPC(NPC npc) {
        String path = "npcs." + npc.getId();
        npcs.set(path + ".name", npc.getName());
        npcs.set(path + ".location", LocationUtil.serializeLocation(npc.getLocation()));
        npcs.set(path + ".spawn-on-load", true);

        ConfigurationSection traitsSection = npcs.createSection(path + ".traits");
        for (Trait trait : npc.getTraits()) {
            ConfigurationSection traitSection = traitsSection.createSection(trait.getName());
            trait.onSave(traitSection);
        }
        
        saveNPCs();
    }
    
    public Map<String, Object> loadTraits(String npcId) {
        Map<String, Object> traits = new HashMap<>();
        ConfigurationSection traitSection = npcs.getConfigurationSection("npcs." + npcId + ".traits");
        
        if (traitSection != null) {
            for (String traitName : traitSection.getKeys(false)) {
                // Dynamic trait loading
                ConfigurationSection traitData = traitSection.getConfigurationSection(traitName);
                if (traitData != null) {
                    traits.put(traitName, traitData);
                }
            }
        }
        
        return traits;
    }
    
    public void removeNPC(String id) {
        npcs.set("npcs." + id, null);
        saveNPCs();
    }
    
    private void loadConfigs() {
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        if (!npcFile.exists()) {
            try {
                npcFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create npcs.yml!");
            }
        }
        
        config = YamlConfiguration.loadConfiguration(configFile);
        npcs = YamlConfiguration.loadConfiguration(npcFile);
    }
    
    private void saveNPCs() {
        try {
            npcs.save(npcFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save npcs.yml!");
        }
    }
}