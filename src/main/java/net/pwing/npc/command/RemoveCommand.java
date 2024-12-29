package net.pwing.npc.command;

import net.pwing.npc.PwingNpc;
import net.pwing.npc.api.NPC;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveCommand extends SubCommand {
    private final PwingNpc plugin;
    
    public RemoveCommand(PwingNpc plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public String getName() {
        return "remove";
    }
    
    @Override
    public String getDescription() {
        return "Remove an existing NPC";
    }
    
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /npc remove <id>");
            return true;
        }
        
        NPC npc = plugin.getNPCManager().getNPC(args[1]);
        if (npc == null) {
            sender.sendMessage("§cNPC not found!");
            return true;
        }
        
        npc.remove();
        sender.sendMessage("§aNPC removed successfully!");
        return true;
    }
    
    @Override
    public List<String> getTabCompletions(CommandSender sender, String[] args) {
        if (args.length == 2) {
            return plugin.getNPCManager().getNPCs().stream()
                    .map(NPC::getId)
                    .filter(id -> id.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
