package net.pwing.npc.visibility;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VisibilityCommand implements CommandExecutor, TabCompleter {

    private final PwingVisibilityPlugin plugin;
    private final List<String> subcommands = Arrays.asList("add", "remove", "permission", "default");

    public VisibilityCommand(PwingVisibilityPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /npcvisibility <add|remove|permission|default> <id> [args]");
            return true;
        }

        NPC npc = CitizensAPI.getNPCRegistry().getById(Integer.parseInt(args[1]));
        if (npc == null) {
            sender.sendMessage(ChatColor.RED + "NPC not found!");
            return true;
        }

        VisibilityTrait trait = npc.getOrAddTrait(VisibilityTrait.class);

        switch (args[0].toLowerCase()) {
            case "add":
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Only players can use this command!");
                    return true;
                }
                trait.addPlayer((Player) sender);
                sender.sendMessage(ChatColor.GREEN + "Added you to NPC's visible players list!");
                break;

            case "remove":
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Only players can use this command!");
                    return true;
                }
                trait.removePlayer((Player) sender);
                sender.sendMessage(ChatColor.GREEN + "Removed you from NPC's visible players list!");
                break;

            case "permission":
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /npcvisibility permission <id> <permission>");
                    return true;
                }
                trait.setPermission(args[2]);
                sender.sendMessage(ChatColor.GREEN + "Set NPC's required permission to: " + args[2]);
                break;

            case "default":
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /npcvisibility default <id> <true|false>");
                    return true;
                }
                trait.setDefaultVisible(Boolean.parseBoolean(args[2]));
                sender.sendMessage(ChatColor.GREEN + "Set NPC's default visibility to: " + args[2]);
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return subcommands.stream()
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
