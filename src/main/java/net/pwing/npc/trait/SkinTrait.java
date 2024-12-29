package net.pwing.npc.trait;

import net.pwing.npc.api.NPC;
import net.pwing.npc.skin.NPCSkin;
import net.pwing.npc.skin.SkinFetcher;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class SkinTrait implements Trait {
    private NPC npc;
    private NPCSkin skin;
    private String skinName;

    @Override
    public String getName() {
        return "skin";
    }

    @Override
    public void onLoad(NPC npc, ConfigurationSection config) {
        this.npc = npc;
        this.skinName = config.getString("skin-name", "");
        String value = config.getString("value", "");
        String signature = config.getString("signature", "");
        
        if (!value.isEmpty() && !signature.isEmpty()) {
            this.skin = new NPCSkin(value, signature);
        }
    }

    @Override
    public void onSave(ConfigurationSection config) {
        config.set("skin-name", skinName);
        if (skin != null) {
            config.set("value", skin.getValue());
            config.set("signature", skin.getSignature());
        }
    }

    @Override
    public List<String> getActions() {
        return List.of("set", "clear");
    }

    @Override
    public void handleAction(CommandSender sender, String action, String[] args) {
        switch (action.toLowerCase()) {
            case "set" -> {
                if (args.length < 5) {
                    sender.sendMessage("§cUsage: /npc trait <id> skin set <skinName>");
                    return;
                }
                String newSkinName = args[4];
                sender.sendMessage("§eFetching skin...");
                
                SkinFetcher.fetchSkinFromName(newSkinName).thenAccept(newSkin -> {
                    setSkin(newSkin, newSkinName);
                    sender.sendMessage("§aSkin updated successfully!");
                }).exceptionally(throwable -> {
                    sender.sendMessage("§cFailed to fetch skin: " + throwable.getMessage());
                    return null;
                });
            }
            case "clear" -> {
                skin = null;
                skinName = "";
                sender.sendMessage("§aSkin cleared!");
            }
        }
    }

    public void setSkin(NPCSkin skin, String skinName) {
        this.skin = skin;
        this.skinName = skinName;
        // Update NPC skin
        if (npc instanceof PacketNPC packetNPC) {
            packetNPC.setSkin(skin);
        }
    }
}