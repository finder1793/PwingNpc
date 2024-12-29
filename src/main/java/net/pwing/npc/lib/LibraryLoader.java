package net.pwing.npc.lib;

import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public class LibraryLoader {
    
    public static void loadPacketEvents(JavaPlugin plugin) {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(plugin));
        PacketEvents.getAPI().init();
    }
    
    public static void terminate() {
        PacketEvents.getAPI().terminate();
    }
}
