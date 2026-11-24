package com.kvermin.ultimate_respawn.worldgen.tree;

import com.google.common.collect.Lists;
import com.kvermin.ultimate_respawn.block.URBlocks;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.rootplacers.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

public class ArchTreeRootPlacer extends RootPlacer {
    public static final MapCodec<ArchTreeRootPlacer> CODEC = RecordCodecBuilder.mapCodec((i) -> rootPlacerParts(i).apply(i, ArchTreeRootPlacer::new));

    public ArchTreeRootPlacer(IntProvider trunkOffsetY, Holder<BlockStateProvider> rootProvider, Optional<AboveRootPlacement> aboveRootPlacement) {
        super(trunkOffsetY, rootProvider, aboveRootPlacement);
    }

    @Override
    protected RootPlacerType<?> type() {
        return URTreePartTypes.ARCH_TREE_ROOT_PLACER.get();
    }

    @Override
    public boolean placeRoots(WorldGenLevel level, BiConsumer<BlockPos, BlockState> rootSetter, RandomSource random, BlockPos origin, BlockPos trunkOrigin, TreeFeature tree) {
        List<BlockPos> rootPositions = Lists.newArrayList();
        BlockPos.MutableBlockPos columnPos = origin.mutable();

        while(columnPos.getY() < trunkOrigin.getY()) {
            if (!this.canPlaceRoot(level, columnPos)) {
                return false;
            }

            columnPos.move(Direction.UP);
        }

        rootPositions.add(trunkOrigin.below());

        int offsetRoots = 0;
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            int yOffset;
            BlockPos pos;

            if (offsetRoots == 3) {
                yOffset = 0;
            } else {
                yOffset = random.nextInt(2);
                if (yOffset > 0) { offsetRoots++; }
            }

            if (dir == Direction.NORTH) {
                pos = trunkOrigin.offset(1, yOffset, 1).relative(dir);
            } else if (dir == Direction.EAST) {
                pos = trunkOrigin.offset(0, yOffset, 1).relative(dir);
            } else if (dir == Direction.WEST) {
                pos = trunkOrigin.offset(1, yOffset, 0).relative(dir);
            } else {
                pos = trunkOrigin.offset(0, yOffset, 0).relative(dir);
            }

            List<BlockPos> positionsInDirection = Lists.newArrayList();
            if (!this.simulateRoots(level, random, pos, dir, pos, positionsInDirection)) {
                return false;
            }

            rootPositions.addAll(positionsInDirection);
        }

        for(BlockPos rootPos : rootPositions) {
            this.placeRoot(level, rootSetter, random, rootPos, trunkOrigin, !rootPositions.contains(rootPos.above()), tree);
        }

        return true;
    }

    @Override
    protected boolean canPlaceRoot(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, (state) -> state.isAir() || state.is(BlockTags.REPLACEABLE_BY_TREES) || state.is(Blocks.MOSS_CARPET) || state.is(Blocks.POPLAR_WOOD));
    }

    protected void placeRoot(WorldGenLevel level, BiConsumer<BlockPos, BlockState> rootSetter, RandomSource random, BlockPos pos, BlockPos trunkPos, boolean isRootTop, TreeFeature tree) {
        boolean isMossBlock = this.aboveRootPlacement.isPresent() && this.canPlaceRoot(level, pos) && isRootTop && random.nextFloat() <= 0.5;
        BlockPos origin = trunkPos.subtract(pos);

        if (isMossBlock) {
            rootSetter.accept(pos, BlockStateProvider.holderOf(Blocks.MOSS_BLOCK).value().getState(level, random, pos));
        } else if (this.canPlaceRoot(level, pos)) {
            rootSetter.accept(pos, this.rootProvider.value().getState(level, random, pos));
        }
        if (this.aboveRootPlacement.isPresent() && isRootTop) {
            AboveRootPlacement abovePlacement = this.aboveRootPlacement.get();
            BlockPos above = pos.above();
            if (random.nextFloat() < abovePlacement.aboveRootPlacementChance() && level.isStateAtPosition(above, BlockBehaviour.BlockStateBase::isAir)) {
                rootSetter.accept(above, abovePlacement.aboveRootProvider().value().getState(level, random, above));
            } else if (random.nextFloat() <= 0.5 && isMossBlock) {
                Holder<BlockStateProvider> rootDecoration = random.nextBoolean() ? BlockStateProvider.holderOf(Blocks.FERN) : BlockStateProvider.holderOf(Blocks.BUSH);
                rootSetter.accept(above, rootDecoration.value().getState(level, random, above));
            } else if (!(isMossBlock && random.nextFloat() < 0.5)) {
                rootSetter.accept(above, BlockStateProvider.holderOf(URBlocks.FAIRY_TABLECLOTH.get()).value().getState(level, random, above));
            }
        }
    }

    private boolean simulateRoots(LevelSimulatedReader level, RandomSource random, BlockPos rootPos, Direction dir, BlockPos rootOrigin, List<BlockPos> rootPositions) {
        return simulateRoots(level, random, rootPos, dir, rootOrigin, rootPositions, 0, Direction.Plane.HORIZONTAL, 0);
    }

    private boolean simulateRoots(LevelSimulatedReader level, RandomSource random, BlockPos rootPos, Direction dir, BlockPos rootOrigin, List<BlockPos> rootPositions, int shelves, Direction.Plane prevDir, int dirSteps) {
        for(BlockPos pos : this.potentialRootPositions(rootPos, dir, prevDir, dirSteps, shelves, random, rootOrigin)) {
            if (this.canPlaceRoot(level, pos)) {
                Direction.Plane newDir = pos.equals(rootPos.relative(dir)) ? Direction.Plane.HORIZONTAL : Direction.Plane.VERTICAL;

                if (pos.distManhattan(rootOrigin) <= 1) {
                    for (int h = 0; h < random.nextInt(3) + 2; ++h) {
                        rootPositions.add(pos.offset(0, h, 0));
                        rootPositions.add(pos.offset(0, h, 0).relative(dir.getCounterClockWise(Direction.Axis.Y)));
                    }
                } else {
                    rootPositions.add(pos);
                    rootPositions.add(pos.relative(dir.getCounterClockWise(Direction.Axis.Y)));
                    if (newDir.equals(Direction.Plane.HORIZONTAL)) {
                        rootPositions.add(pos.above());
                        rootPositions.add(pos.above().relative(dir.getCounterClockWise(Direction.Axis.Y)));
                    } else {
                        BlockPos origin = rootOrigin.subtract(pos);
                        Direction inside = Direction.getApproximateNearest(origin.getX(), 0, origin.getZ());
                        rootPositions.add(pos.relative(inside));
                        rootPositions.add(pos.relative(inside).relative(dir.getCounterClockWise(Direction.Axis.Y)));
                    }
                }

                Logger.getAnonymousLogger().info(String.valueOf(shelves));
                if (!newDir.equals(prevDir) && newDir.equals(Direction.Plane.HORIZONTAL)) { shelves++; }
                if (!this.simulateRoots(level, random, pos, dir, rootOrigin, rootPositions, shelves, newDir, newDir.equals(prevDir) ? ++dirSteps : 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected List<BlockPos> potentialRootPositions(BlockPos pos, Direction dir, Direction.Plane prevDir, int dirSteps, int shelves, RandomSource random, BlockPos rootOrigin) {
        BlockPos below = pos.below();
        BlockPos nextTo = pos.relative(dir);
        int width = pos.distManhattan(rootOrigin);
        if (width < 4 || prevDir.equals(Direction.Plane.HORIZONTAL) && dirSteps < 2) {
            return List.of(nextTo);
        } else if ((prevDir.equals(Direction.Plane.HORIZONTAL) && dirSteps >= 6) || (prevDir.equals(Direction.Plane.VERTICAL) && (dirSteps < 2 || shelves >= 1))) {
            return List.of(below);
        } else {
            return random.nextBoolean() ? List.of(nextTo) : List.of(below);
        }
    }
}
