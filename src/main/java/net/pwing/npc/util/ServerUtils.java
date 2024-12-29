package net.pwing.npc.util;

public class ServerUtils {

    private static Boolean isPaper = null;

    public static boolean isPaper() {
        if (isPaper != null) {
            return isPaper;
        }
        
        try {
            Class.forName("io.papermc.paper.configuration.Configuration");
            isPaper = true;
        } catch (ClassNotFoundException e) {
            isPaper = false;
        }
        
        return isPaper;
    }
}
