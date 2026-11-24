package com.kvermin.ultimate_respawn.datagen;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class URGiftLootProvider implements LootTableSubProvider {
        private final LootTableSubProvider.Context output;
        public static Holder.Reference<LootTable> orchaurochShed;

    public URGiftLootProvider(LootTableSubProvider.Context output) {
            this.output = output;
        }

        @Override
        public void run() {
            orchaurochShed = this.output.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "orchauroch_shed")), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ContextIntProviders.exactly(1))
                            .add(LootItem.lootTableItem(Items.APPLE))
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
                    ));
        }
}
