package com.kvermin.ultimate_respawn.worldgen;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.block.URBlocks;
import com.kvermin.ultimate_respawn.worldgen.tree.*;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BlockStateProviders;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PoplarFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.*;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class URTreeFeatures {
    public static void bootstrap(BootstrapContext<Feature> context) {
        Holder<BlockStateProvider> belowTrunkProvider = context.lookup(Registries.BLOCK_STATE_PROVIDER).getOrThrow(BlockStateProviders.SOIL_BENEATH_TREE);
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

        register(context, createKey("arch_tree"), archTree(belowTrunkProvider, blocks).build());
        register(context, createKey("buttress_tree"), buttressTree(belowTrunkProvider, blocks).build());
    }

    private static void register(BootstrapContext<Feature> context, ResourceKey<Feature> key, Feature feature)
    {
        context.register(key, feature);
    }

    private static ResourceKey<Feature> createKey(String name) {
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, name));
    }

    private static TreeFeature.Builder archTree(final Holder<BlockStateProvider> belowTrunkProvider, HolderGetter<Block> blocks) {
        PlaceOnGroundDecorator sparseLeafLitter = new PlaceOnGroundDecorator(
                96, 4, 2, Holder.direct(new WeightedStateProvider(VegetationFeatures.leafLitterPatchBuilder(1, 3)))
        );
        PlaceOnGroundDecorator thickLeafLitter = new PlaceOnGroundDecorator(
                150, 2, 2, Holder.direct(new WeightedStateProvider(VegetationFeatures.leafLitterPatchBuilder(1, 4)))
        );

        return new TreeFeature.Builder(
                BlockStateProvider.of(URBlocks.ARCH_TREE_LOG.get()),
                new ArchTreeTrunkPlacer(
                        18, 2, 10
                ),
                BlockStateProvider.of(Blocks.AZALEA_LEAVES),
                new DarkOakFoliagePlacer(UniformInt.of(0, 1), ConstantInt.of(0)),
                Optional.of(
                        new ArchTreeRootPlacer(
                                UniformInt.of(3, 5),
                                BlockStateProvider.holderOf(URBlocks.ARCH_TREE_WOOD.get()),
                                Optional.of(
                                        new AboveRootPlacement(BlockStateProvider.holderOf(Blocks.MOSS_CARPET), 0.25F)
                                )
                        )
                ),
                new TwoLayersFeatureSize(2, 0, 2),
                Holder.direct(BlockStateProvider.of(Blocks.AIR))
        )
                .decorators(List.of(
                        sparseLeafLitter,
                        thickLeafLitter,
                        new TallShelfMushroomDecorator(0.5F),
                        new EpiphyteDecorator(1)
                        ))
                .ignoreVines();
    }
    private static TreeFeature.Builder buttressTree(final Holder<BlockStateProvider> belowTrunkProvider, HolderGetter<Block> blocks) {
        return new TreeFeature.Builder(
                BlockStateProvider.of(URBlocks.BUTTRESS_LOG.get()),
                new StraightTrunkPlacer(16, 0, 4),
                BlockStateProvider.of(Blocks.ACACIA_LEAVES),
                new DarkOakFoliagePlacer(UniformInt.of(0, 1), ConstantInt.of(0)),
                Optional.of(
                        new ButtressRootPlacer(ConstantInt.of(0), BlockStateProvider.holderOf(URBlocks.BUTTRESS_ROOT.get()), Optional.empty())
                ),
                new TwoLayersFeatureSize(1, 0, 2),
                belowTrunkProvider)
        .decorators(List.of(
                new EpiphyteDecorator(0.5F)
        ))
        .ignoreVines();
    }
}
