package com.kvermin.ultimate_respawn.worldgen.tree;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class URTreePartTypes<P extends RootPlacer> {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE, UltimateRespawn.MOD_ID);
    public static final DeferredRegister<RootPlacerType<?>> ROOT_PLACER_TYPES = DeferredRegister.create(BuiltInRegistries.ROOT_PLACER_TYPE, UltimateRespawn.MOD_ID);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(BuiltInRegistries.TREE_DECORATOR_TYPE, UltimateRespawn.MOD_ID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<ArchTreeTrunkPlacer>> ARCH_TREE_TRUNK_PLACER = TRUNK_PLACER_TYPES.register("arch_tree_trunk_placer", () -> new TrunkPlacerType<>(ArchTreeTrunkPlacer.CODEC));
    public static final DeferredHolder<RootPlacerType<?>, RootPlacerType<ArchTreeRootPlacer>> ARCH_TREE_ROOT_PLACER = ROOT_PLACER_TYPES.register("arch_tree_root_placer", () -> new RootPlacerType<>(ArchTreeRootPlacer.CODEC));
    public static final DeferredHolder<RootPlacerType<?>, RootPlacerType<ButtressRootPlacer>> BUTTRESS_ROOT_PLACER = ROOT_PLACER_TYPES.register("buttress_root_placer", () -> new RootPlacerType<>(ButtressRootPlacer.CODEC));

    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<EpiphyteDecorator>> EPIPHYTE = TREE_DECORATOR_TYPES.register("epiphyte", () -> new TreeDecoratorType<>(EpiphyteDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<TallShelfMushroomDecorator>> TALL_SHELF_MUSHROOM = TREE_DECORATOR_TYPES.register("tall_shelf_mushroom", () -> new TreeDecoratorType<>(TallShelfMushroomDecorator.CODEC));

    private final MapCodec<P> codec;

    public URTreePartTypes(MapCodec<P> codec) {
        this.codec = codec;
    }

    public MapCodec<P> codec() {
        return this.codec;
    }
}
