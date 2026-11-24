package com.kvermin.ultimate_respawn.datagen;

import net.minecraft.core.registries.SingleRegistryBootstrap;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public class URLootTableProvider {
    public static SingleRegistryBootstrap<LootTable> create() {
        return new LootTableProvider(
                BuiltInLootTables.all(),
                List.of(
                        new LootTableProvider.SubProviderEntry(URBlockLootProvider::new, LootContextParamSets.BLOCK),

                        new LootTableProvider.SubProviderEntry(URGiftLootProvider::new, LootContextParamSets.GIFT),
                        new LootTableProvider.SubProviderEntry(UREntityLootProvider::new, LootContextParamSets.ENTITY)
                )
        );
    }
}
