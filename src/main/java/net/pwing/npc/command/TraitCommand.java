package net.pwing.npc.command;

import net.pwing.npc.PwingNpc;
import net.pwing.npc.api.NPC;
import net.pwing.npc.trait.Trait;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TraitCommand extends SubCommand {
    private final PwingNpc plugin;
    
    public TraitCommand(PwingNpc plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public String getName() {
        return "trait";
    }
    
    @Override
    public String getDescription() {
        return "Manage NPC traits";
    }
    
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sender.sendMessage("§cUsage: /npc trait <id> <traitName> <action> [args]");
            return true;
        }
        
        NPC npc = plugin.getNPCManager().getNPC(args[1]);
        if (npc == null) {
            sender.sendMessage("§cNPC not found!");
            return true;
        }
        
        String traitName = args[2];
        Trait trait = plugin.getTraitRegistry().getTrait(traitName);
        
        if (trait == null) {
            sender.sendMessage("§cTrait not found!");
            return true;
        }
        
        // Dynamic trait action handling
        String action = args[3].toLowerCase();
        handleTraitAction(sender, npc, trait, action, args);
        
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
        
        if (args.length == 3) {
            return plugin.getTraitRegistry().getTraitNames().stream()
                    .filter(name -> name.toLowerCase().startsWith(args[2].toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        if (args.length == 4) {
            Trait trait = plugin.getTraitRegistry().getTrait(args[2]);
            if (trait != null) {
                return trait.getActions().stream()
                        .filter(action -> action.toLowerCase().startsWith(args[3].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        
        return new ArrayList<>();
    }
}
