package net.pwing.npc.command;

import net.pwing.npc.PwingNpc;
import net.pwing.npc.api.NPC;
import net.pwing.npc.skin.SkinFetcher;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SkinCommand extends SubCommand {
    private final PwingNpc plugin;
    
    public SkinCommand(PwingNpc plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public String getName() {
        return "skin";
    }
    
    @Override
    public String getDescription() {
        return "Set an NPC's skin";
    }
    
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§cUsage: /npc skin <id> <skinName>");
            return true;
        }
        
        NPC npc = plugin.getNPCManager().getNPC(args[1]);
        if (npc == null) {
            sender.sendMessage("§cNPC not found!");
            return true;
        }
        
        String skinName = args[2];
        sender.sendMessage("§eFetching skin...");
        
        SkinFetcher.fetchSkinFromName(skinName).thenAccept(skin -> {
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                npc.setSkin(skin);
                sender.sendMessage("§aSkin updated successfully!");
            });
        }).exceptionally(throwable -> {
            sender.sendMessage("§cFailed to fetch skin: " + throwable.getMessage());
            return null;
        });
        
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
