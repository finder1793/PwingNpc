package net.pwing.npc.skin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class SkinFetcher {
    
    public static CompletableFuture<NPCSkin> fetchSkinFromName(String username) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
                InputStreamReader reader = new InputStreamReader(url.openStream());
                JsonObject object = JsonParser.parseReader(reader).getAsJsonObject();
                String uuid = object.get("id").getAsString();
                
                URL profileUrl = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
                reader = new InputStreamReader(profileUrl.openStream());
                object = JsonParser.parseReader(reader).getAsJsonObject();
                
                JsonObject properties = object.getAsJsonArray("properties").get(0).getAsJsonObject();
                String value = properties.get("value").getAsString();
                String signature = properties.get("signature").getAsString();
                
                return new NPCSkin(value, signature);
            } catch (IOException e) {
                throw new RuntimeException("Failed to fetch skin", e);
            }
        });
    }
}
