package net.pwing.npc.visibility;

import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.plugin.java.JavaPlugin;
import net.pwing.npc.visibility.player.PlayerHiderCommand;
import net.pwing.npc.visibility.player.PlayerHiderManager;
import net.pwing.npc.visibility.VisibilityListener;
import net.pwing.npc.visibility.VisibilityTrait;
import net.pwing.npc.visibility.VisibilityCommand;
import net.pwing.npc.visibility.player.PlayerHiderConfig;

public class PwingVisibilityPlugin extends JavaPlugin {
    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().getSettings()
                .bStats(true)
                .checkForUpdates(false)
                .debug(false);
        PacketEvents.getAPI().load();
    }
    private VisibilityConfig visibilityConfig;
    private PlayerHiderManager playerHiderManager;
    private PlayerHiderConfig playerHiderConfig;

    @Override
    public void onEnable() {
        PacketEvents.getAPI().init();
        
        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(VisibilityTrait.class));
        getServer().getPluginManager().registerEvents(new VisibilityListener(this), this);
        getCommand("npcvisibility").setExecutor(new VisibilityCommand(this));
        
        PacketEvents.getAPI().getEventManager().registerListener(new PacketVisibilityHandler());

        this.visibilityConfig = new VisibilityConfig(this);
        this.playerHiderManager = new PlayerHiderManager(this);
        this.playerHiderConfig = new PlayerHiderConfig(this, playerHiderManager);
        playerHiderConfig.loadGroups();
        getCommand("playerhider").setExecutor(new PlayerHiderCommand(playerHiderManager));
    }
    
    public VisibilityConfig getVisibilityConfig() {
        return visibilityConfig;
    }

    @Override
    public void onDisable() {
        playerHiderConfig.saveGroups();
        PacketEvents.getAPI().terminate();
    }
}