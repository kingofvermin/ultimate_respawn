package com.kvermin.ultimate_respawn;

import com.kvermin.ultimate_respawn.datagen.*;
import com.kvermin.ultimate_respawn.worldgen.URTreeFeatures;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

@EventBusSubscriber(modid = UltimateRespawn.MOD_ID)
public class UltimateRespawnDatagen {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        var lookupProvider = event.getWorldLookupProvider();

        generator.addProvider(true, new URModelProvider(packOutput));
        generator.addProvider(true, new URItemTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new URBlockTagsProvider(packOutput, lookupProvider));

        event.createWorldRegistryObjects(new RegistrySetBuilder()
                .add(Registries.FEATURE, URTreeFeatures::bootstrap),
                Set.of(UltimateRespawn.MOD_ID));
        event.createReloadableRegistryObjects(new RegistrySetBuilder()
                .add(Registries.LOOT_TABLE, URLootTableProvider.create()),
                Set.of(UltimateRespawn.MOD_ID));
    }
}
