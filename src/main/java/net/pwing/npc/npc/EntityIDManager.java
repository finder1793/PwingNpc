package net.pwing.npc.npc;

import java.util.concurrent.atomic.AtomicInteger;

public class EntityIDManager {
    private static final AtomicInteger nextEntityId = new AtomicInteger(Integer.MAX_VALUE);
    
    public static int getNextEntityId() {
        return nextEntityId.decrementAndGet();
    }
}
