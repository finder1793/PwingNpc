package net.pwing.npc;

import net.pwing.npc.lib.LibraryLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class PwingNpc extends JavaPlugin {
    
    @Override
    public void onEnable() {
        LibraryLoader.loadPacketEvents(this);
        getLogger().info("PwingNpc has been enabled!");
    }

    @Override
    public void onDisable() {
        LibraryLoader.terminate();
        getLogger().info("PwingNpc has been disabled!");
    }
}
