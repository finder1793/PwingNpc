package net.pwing.npc.command;

import net.pwing.npc.PwingNpc;
import net.pwing.npc.api.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoveCommand extends SubCommand {
    private final PwingNpc plugin;
    
    public MoveCommand(PwingNpc plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public String getName() {
        return "move";
    }
    
    @Override
    public String getDescription() {
        return "Move an NPC to your location";
    }
    
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can move NPCs!");
            return true;
        }
        
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /npc move <id>");
            return true;
        }
        
        NPC npc = plugin.getNPCManager().getNPC(args[1]);
        if (npc == null) {
            sender.sendMessage("§cNPC not found!");
            return true;
        }
        
        npc.teleport(player.getLocation());
        sender.sendMessage("§aNPC moved successfully!");
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
