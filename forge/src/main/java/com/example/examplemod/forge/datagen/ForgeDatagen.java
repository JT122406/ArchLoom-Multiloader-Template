package com.example.examplemod.forge.datagen;

import com.example.examplemod.ExampleMod;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ExampleMod.MOD_ID)
class ForgeDatagen {

	@SubscribeEvent
	protected static void gatherData(final GatherDataEvent event) {

	}
}
