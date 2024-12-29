package net.pwing.npc;

import net.pwing.npc.command.CommandManager;
import net.pwing.npc.config.ConfigManager;
import net.pwing.npc.manager.NPCManagerImpl;
import net.pwing.npc.trait.TraitRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public class PwingNpc extends JavaPlugin {
    private ConfigManager configManager;
    private NPCManagerImpl npcManager;
    private TraitRegistry traitRegistry;
    
    @Override
    public void onEnable() {
        // Initialize managers
        this.configManager = new ConfigManager(this);
        this.npcManager = new NPCManagerImpl(this);
        this.traitRegistry = new TraitRegistry();
        
        // Register default traits
        registerDefaultTraits();
        
        // Load NPCs
        npcManager.loadNPCs();
        
        // Register commands
        getCommand("npc").setExecutor(new CommandManager(this));
        
        getLogger().info("PwingNpc has been enabled!");
    }
    
    @Override
    public void onDisable() {
        // Save all NPCs before shutdown
        npcManager.getNPCs().forEach(npc -> configManager.saveNPC(npc));
        getLogger().info("PwingNpc has been disabled!");
    }
    
    private void registerDefaultTraits() {
        traitRegistry.registerTrait("movement", MovementTrait::new);
        traitRegistry.registerTrait("skin", SkinTrait::new);
        traitRegistry.registerTrait("lookcloser", LookCloseTrait::new);
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public NPCManagerImpl getNPCManager() {
        return npcManager;
    }
    
    public TraitRegistry getTraitRegistry() {
        return traitRegistry;
    }
}