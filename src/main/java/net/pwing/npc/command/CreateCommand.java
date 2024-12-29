package net.pwing.npc.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CreateCommand extends SubCommand {
    @Override
    public String getName() {
        return "create";
    }
    
    @Override
    public String getDescription() {
        return "Create a new NPC";
    }
    
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can create NPCs!");
            return true;
        }
        
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /npc create <name>");
            return true;
        }
        
        String name = args[1];
        // Create NPC logic here
        
        return true;
    }
    
    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
