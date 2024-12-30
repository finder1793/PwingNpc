package net.pwing.npc.visibility.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class PlayerHiderCommand implements CommandExecutor {
    private final PlayerHiderManager manager;
    
    public PlayerHiderCommand(PlayerHiderManager manager) {
        this.manager = manager;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /playerhider <group|hide|show> [player]");
            return true;
        }

        String action = args[0].toLowerCase();
        
        switch (action) {
            case "hide":
                handleHideCommand(sender, args);
                break;
            case "show":
                handleShowCommand(sender, args);
                break;
            case "group":
                handleGroupCommand(sender, args);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Invalid action! Use 'group', 'hide', or 'show'");
        }
        
        return true;
    }
    private void handleHideCommand(CommandSender sender, String[] args) {
        // Check base permission for hiding
        if (!sender.hasPermission("pwingnpc.hide.self")) {
            sender.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        Player target;
        if (args.length > 1) {
            // Check permission for hiding others
            if (!sender.hasPermission("pwingnpc.hide.others")) {
                sender.sendMessage(ChatColor.RED + "No permission to hide other players!");
                return;
            }
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found!");
                return;
            }
        } else if (sender instanceof Player) {
            target = (Player) sender;
        } else {
            sender.sendMessage(ChatColor.RED + "Console must specify a player!");
            return;
        }

        manager.hidePlayer(target);
        sender.sendMessage(ChatColor.GREEN + "Hidden " + target.getName() + " from all players!");
    }

    private void handleShowCommand(CommandSender sender, String[] args) {
        // Check base permission for showing
        if (!sender.hasPermission("pwingnpc.show.self")) {
            sender.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        Player target;
        if (args.length > 1) {
            // Check permission for showing others
            if (!sender.hasPermission("pwingnpc.show.others")) {
                sender.sendMessage(ChatColor.RED + "No permission to show other players!");
                return;
            }
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found!");
                return;
            }
        } else if (sender instanceof Player) {
            target = (Player) sender;
        } else {
            sender.sendMessage(ChatColor.RED + "Console must specify a player!");
            return;
        }

        manager.showPlayer(target);
        sender.sendMessage(ChatColor.GREEN + "Showing " + target.getName() + " to all players!");
    }
    private void handleGroupCommand(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /playerhider group <add|remove|create|list> <groupName> [player]");
            return;
        }

        String groupAction = args[1].toLowerCase();
        String groupName = args[2];

        switch (groupAction) {
            case "create":
                if (!sender.hasPermission("pwingnpc.group.create")) {
                    sender.sendMessage(ChatColor.RED + "No permission!");
                    return;
                }
                manager.createHiddenGroup(groupName);
                sender.sendMessage(ChatColor.GREEN + "Created new hidden group: " + groupName);
                break;

            case "list":
                if (!sender.hasPermission("pwingnpc.group.list")) {
                    sender.sendMessage(ChatColor.RED + "No permission!");
                    return;
                }
                Set<UUID> players = manager.getGroupPlayers(groupName);
                if (players == null || players.isEmpty()) {
                    sender.sendMessage(ChatColor.YELLOW + "No players in group: " + groupName);
                    return;
                }
                sender.sendMessage(ChatColor.GREEN + "Players in group " + groupName + ":");
                players.forEach(uuid -> {
                    String playerName = Bukkit.getOfflinePlayer(uuid).getName();
                    sender.sendMessage(ChatColor.GRAY + "- " + playerName);
                });
                break;

            case "add":
                if (args.length < 4) {
                    sender.sendMessage(ChatColor.RED + "Please specify a player!");
                    return;
                }
                if (!sender.hasPermission("pwingnpc.group.add")) {
                    sender.sendMessage(ChatColor.RED + "No permission!");
                    return;
                }
                Player targetAdd = Bukkit.getPlayer(args[3]);
                if (targetAdd == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found!");
                    return;
                }
                manager.addPlayerToGroup(targetAdd, groupName);
                sender.sendMessage(ChatColor.GREEN + "Added " + targetAdd.getName() + " to group: " + groupName);
                break;

            case "remove":
                if (args.length < 4) {
                    sender.sendMessage(ChatColor.RED + "Please specify a player!");
                    return;
                }
                if (!sender.hasPermission("pwingnpc.group.remove")) {
                    sender.sendMessage(ChatColor.RED + "No permission!");
                    return;
                }
                Player targetRemove = Bukkit.getPlayer(args[3]);
                if (targetRemove == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found!");
                    return;
                }
                manager.removePlayerFromGroup(targetRemove, groupName);
                sender.sendMessage(ChatColor.GREEN + "Removed " + targetRemove.getName() + " from group: " + groupName);
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Invalid group action! Use create, list, add, or remove");
        }
    }
}

