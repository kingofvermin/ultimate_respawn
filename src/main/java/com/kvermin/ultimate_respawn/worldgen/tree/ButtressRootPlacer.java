package com.kvermin.ultimate_respawn.worldgen.tree;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class ButtressRootPlacer extends RootPlacer {
    public static final MapCodec<ButtressRootPlacer> CODEC = RecordCodecBuilder.mapCodec((i) -> rootPlacerParts(i).apply(i, ButtressRootPlacer::new));

    public ButtressRootPlacer(IntProvider trunkOffsetY, Holder<BlockStateProvider> rootProvider, Optional<AboveRootPlacement> aboveRootPlacement) {
        super(trunkOffsetY, rootProvider, aboveRootPlacement);
    }

    @Override
    protected RootPlacerType<?> type() { return URTreePartTypes.BUTTRESS_ROOT_PLACER.get(); }

    @Override
    public boolean placeRoots(WorldGenLevel level, BiConsumer<BlockPos, BlockState> rootSetter, RandomSource random, BlockPos origin, BlockPos trunkOrigin, TreeFeature tree) {
        List<BlockPos> rootPositions = Lists.newArrayList();

        for(Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos pos = trunkOrigin.relative(dir);
            this.placeRoot(level, rootSetter, random, pos, dir, tree);
        }

        return true;
    }

    protected void placeRoot(WorldGenLevel level, BiConsumer<BlockPos, BlockState> rootSetter, RandomSource random, BlockPos pos, Direction dir, TreeFeature tree) {
        int height = random.nextInt(5) + 4;
        int stepHeight = random.nextInt(2) + 1;

        if (canPlaceRoot(level, pos.relative(dir, 2))) { rootSetter.accept(pos.relative(dir, 2), this.rootProvider.value().getState(level, random, pos.relative(dir, 2)).trySetValue(HorizontalDirectionalBlock.FACING, dir)); }
        for (int i = 0; i <= height; ++i) {
            if (canPlaceRoot(level, pos.above(i))) { rootSetter.accept(pos.above(i), this.rootProvider.value().getState(level, random, pos.above(i)).trySetValue(HorizontalDirectionalBlock.FACING, dir)); }
            if (i <= height - stepHeight && canPlaceRoot(level, pos.above(i).relative(dir))) {
                rootSetter.accept(pos.above(i).relative(dir), this.rootProvider.value().getState(level, random, pos.above(i).relative(dir)).trySetValue(HorizontalDirectionalBlock.FACING, dir));
            }
        }

        for (int i = 0; i < 3; ++i) {
            if (canPlaceRoot(level, pos.below().relative(dir, i))) { rootSetter.accept(pos.below().relative(dir, i), tree.belowTrunkProvider().value().getState(level, random, pos.below().relative(dir, i))); }
        }
    }

}
