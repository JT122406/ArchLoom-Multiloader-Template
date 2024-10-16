package com.example.examplemod.neoforge;

import com.example.examplemod.ExampleMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/**
 * Main class for the mod on the NeoForge platform.
 */
@Mod(ExampleMod.MOD_ID)
public class ExampleModNeoForge {
    public ExampleModNeoForge(IEventBus eventBus) {
        ExampleMod.init();
    }
}
