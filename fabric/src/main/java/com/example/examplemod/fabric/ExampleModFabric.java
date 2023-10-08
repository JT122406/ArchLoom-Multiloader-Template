package com.example.examplemod.fabric;

import com.example.examplemod.ExampleMod;
import net.fabricmc.api.ModInitializer;

/**
 * This class is the entrypoint for the mod on the Fabric platform.
 */
public class ExampleModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ExampleMod.init();
    }
}
