package net.pwing.npc.trait;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TraitRegistry {
    private final Map<String, Function<ConfigurationSection, Trait>> traitFactories = new HashMap<>();
    
    public void registerTrait(String name, Function<ConfigurationSection, Trait> factory) {
        traitFactories.put(name.toLowerCase(), factory);
    }
    
    public Trait createTrait(String name, ConfigurationSection config) {
        Function<ConfigurationSection, Trait> factory = traitFactories.get(name.toLowerCase());
        return factory != null ? factory.apply(config) : null;
    }
}
