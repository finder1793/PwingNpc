package net.pwing.npc.trait;

import net.pwing.npc.api.NPC;
import net.pwing.npc.movement.NPCMovement;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class MovementTrait implements Trait {
    private NPC npc;
    private NPCMovement movement;
    private boolean pathfinding;
    private double speed;

    @Override
    public String getName() {
        return "movement";
    }

    @Override
    public void onLoad(NPC npc, ConfigurationSection config) {
        this.npc = npc;
        this.movement = new NPCMovement(npc.getEntityId());
        this.pathfinding = config.getBoolean("pathfinding", true);
        this.speed = config.getDouble("speed", 0.2);
    }

    @Override
    public void onSave(ConfigurationSection config) {
        config.set("pathfinding", pathfinding);
        config.set("speed", speed);
    }

    public void walkTo(Location target) {
        Location start = npc.getLocation();
        movement.moveTo(start, target, true);
    }

    public void teleport(Location location) {
        movement.moveTo(npc.getLocation(), location, false);
    }

    @Override
    public List<String> getActions() {
        return List.of("walkto", "teleport", "setspeed");
    }

    @Override
    public void handleAction(CommandSender sender, String action, String[] args) {
        switch (action.toLowerCase()) {
            case "walkto" -> {
                if (sender instanceof Player player) {
                    walkTo(player.getLocation());
                    sender.sendMessage("§aNPC is walking to your location!");
                }
            }
            case "setspeed" -> {
                if (args.length < 5) {
                    sender.sendMessage("§cUsage: /npc trait <id> movement setspeed <speed>");
                    return;
                }
                try {
                    speed = Double.parseDouble(args[4]);
                    sender.sendMessage("§aSet NPC movement speed to " + speed);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§cInvalid speed value!");
                }
            }
        }
    }
}