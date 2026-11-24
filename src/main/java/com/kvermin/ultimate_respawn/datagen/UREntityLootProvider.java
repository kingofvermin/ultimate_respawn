package com.kvermin.ultimate_respawn.datagen;

import com.kvermin.ultimate_respawn.entity.URMobs;
import com.kvermin.ultimate_respawn.entity.orchauroch.AppleCow;
import com.kvermin.ultimate_respawn.item.URItems;
import net.minecraft.advancements.predicates.NbtPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.core.Holder;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.stream.Stream;

public class UREntityLootProvider extends EntityLootSubProvider {
    public UREntityLootProvider(LootTableSubProvider.Context output) {
        super(FeatureFlags.REGISTRY.allFlags(), output);
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return URMobs.ENTITY_TYPES.getEntries().stream().map(Holder::value);
    }

    @Override
    public void generate() {
        this.add(URMobs.BEETLE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(URItems.CHITIN.get())
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0, 1)))));
        this.add(URMobs.BOREHOLE_ANT.get(), LootTable.lootTable());
        this.add(URMobs.CHTHONIAN_KNIGHT.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2))))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0, 1))))
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(URItems.CRUOR_BALL.get())
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 1))))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0, 1))))
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(URItems.ORICHALCUM_SCRAP.get())
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))))
        );
        CompoundTag growthStage = new CompoundTag();
        growthStage.putInt("GrowthStage", 3);
        this.add(URMobs.ORCHAUROCH.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(Items.LEATHER)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 3))))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(Items.BEEF)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0, 1)))
                .withPool(LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(
                                URGiftLootProvider.orchaurochShed
                        ).when(
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity()
                                                .nbt(new NbtPredicate(growthStage))
                                )))));
        this.add(URMobs.WORMS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(URItems.CRUOR_BALL.get())
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 3))))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0, 1))))
        );
    }
}
