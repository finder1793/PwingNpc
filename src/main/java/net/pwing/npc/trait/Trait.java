package net.pwing.npc.trait;

import net.pwing.npc.api.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public interface Trait {
    String getName();
    void onLoad(NPC npc, ConfigurationSection config);
    void onSave(ConfigurationSection config);
    
    // New methods for dynamic command handling
    default List<String> getActions() {
        return List.of();
    }
    
    default void handleAction(CommandSender sender, String action, String[] args) {
        sender.sendMessage("§cThis trait doesn't support any actions!");
    }
}