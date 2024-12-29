package net.pwing.npc.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {
    public abstract String getName();
    public abstract String getDescription();
    public abstract boolean execute(CommandSender sender, String[] args);
    public abstract List<String> getTabCompletions(CommandSender sender, String[] args);
}
