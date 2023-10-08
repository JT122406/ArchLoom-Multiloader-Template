package com.example.examplemod.forge;

import com.example.examplemod.ExampleMod;
import net.minecraftforge.fml.common.Mod;

/**
 * Main class for the mod on the Forge platform.
 */
@Mod(ExampleMod.MOD_ID)
public class ExampleModForge {
    public ExampleModForge() {
        ExampleMod.init();
    }
}
