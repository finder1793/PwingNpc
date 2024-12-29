package net.pwing.npc.trait;

import net.pwing.npc.api.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class LookCloseTrait implements Trait {
    private boolean enabled;
    private double range;
    private NPC npc;
    
    @Override
    public String getName() {
        return "lookcloser";
    }
    
    @Override
    public void onLoad(NPC npc, ConfigurationSection config) {
        this.npc = npc;
        this.enabled = config.getBoolean("enabled", true);
        this.range = config.getDouble("range", 5.0);
    }
    
    @Override
    public void onSave(ConfigurationSection config) {
        config.set("enabled", enabled);
        config.set("range", range);
    }

    @Override
    public List<String> getActions() {
        return List.of("toggle", "setrange");
    }

    @Override
    public void handleAction(CommandSender sender, String action, String[] args) {
        switch (action.toLowerCase()) {
            case "toggle" -> {
                enabled = !enabled;
                sender.sendMessage("§aLook closer " + (enabled ? "enabled" : "disabled"));
            }
            case "setrange" -> {
                if (args.length < 5) {
                    sender.sendMessage("§cUsage: /npc trait <id> lookcloser setrange <range>");
                    return;
                }
                try {
                    range = Double.parseDouble(args[4]);
                    sender.sendMessage("§aSet look range to " + range);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§cInvalid range value!");
                }
            }
        }
    }
}