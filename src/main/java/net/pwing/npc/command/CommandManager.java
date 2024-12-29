package net.pwing.npc.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final List<SubCommand> subCommands = new ArrayList<>();
    
    public CommandManager() {
        subCommands.add(new CreateCommand());
        subCommands.add(new RemoveCommand());
        subCommands.add(new SkinCommand());
        subCommands.add(new MoveCommand());
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§6PwingNPC Commands:");
            subCommands.forEach(sub -> sender.sendMessage("§e/" + label + " " + sub.getName() + " §7- " + sub.getDescription()));
            return true;
        }
        
        for (SubCommand sub : subCommands) {
            if (sub.getName().equalsIgnoreCase(args[0])) {
                return sub.execute(sender, args);
            }
        }
        
        return false;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            for (SubCommand sub : subCommands) {
                if (sub.getName().startsWith(args[0].toLowerCase())) {
                    completions.add(sub.getName());
                }
            }
            return completions;
        }
        
        for (SubCommand sub : subCommands) {
            if (sub.getName().equalsIgnoreCase(args[0])) {
                return sub.getTabCompletions(sender, args);
            }
        }
        
        return new ArrayList<>();
    }
}
