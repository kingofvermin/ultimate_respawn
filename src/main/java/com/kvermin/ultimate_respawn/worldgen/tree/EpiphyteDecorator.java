package com.kvermin.ultimate_respawn.worldgen.tree;

import com.kvermin.ultimate_respawn.block.URBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.ShelfMushroomBlock;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class EpiphyteDecorator extends TreeDecorator {
    public static final MapCodec<EpiphyteDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(EpiphyteDecorator::new, d -> d.placementProbability);

    private final float placementProbability;

    public EpiphyteDecorator(float probability) {
        this.placementProbability = probability;
    }

    protected TreeDecoratorType<?> type() {
        return URTreePartTypes.EPIPHYTE.get();
    }

    public void place(TreeDecorator.Context context) {
        RandomSource random = context.random();
        if (!(random.nextFloat() >= this.placementProbability)) {
            List<BlockPos> logs = context.logs();
            if (!logs.isEmpty()) {
                placeOnStandingTree(context, logs, random);
            }
        }

    }

    private static void placeOnStandingTree(TreeDecorator.Context context, List<BlockPos> logs, RandomSource random) {
        Direction[] directions = pickTwoPerpendicularDirections(random);
        int treeBaseY = ((BlockPos)logs.getFirst()).getY();

        for(BlockPos logPos : logs) {
            if (isWithinDecoratableHeight(logPos, treeBaseY)) {
                for(Direction facing : directions) {
                    if (!(random.nextFloat() > 0.25F) && tryPlaceMushroomOnStandingTree(context, logPos, facing, random)) {
                        break;
                    }
                }
            }
        }

    }

    private static boolean tryPlaceMushroomOnStandingTree(TreeDecorator.Context context, BlockPos logPos, Direction facing, RandomSource random) {
        BlockPos mushroomPos = mushroomPosFor(logPos, facing);
        if (!isBlockReplaceableWithShelfMushroom(context, mushroomPos)) {
            return false;
        } else if (hasShelfMushroomAt(context, mushroomPos.below())) {
            return false;
        } else {
            placeMushroom(context, mushroomPos, facing, random);
            return true;
        }
    }

    private static Direction[] pickTwoPerpendicularDirections(RandomSource random) {
        Direction first = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        return new Direction[]{first, first.getClockWise()};
    }

    private static boolean isWithinDecoratableHeight(BlockPos pos, int treeBaseY) {
        int dy = pos.getY() - treeBaseY;
        return dy >= 1 && dy <= 12;
    }

    private static BlockPos mushroomPosFor(BlockPos logPos, Direction facing) {
        return logPos.offset(facing.getStepX(), 0, facing.getStepZ());
    }

    private static void placeMushroom(TreeDecorator.Context context, BlockPos pos, Direction facing, RandomSource random) {
        context.setBlock(pos, URBlocks.EPIPHYTE.get().defaultBlockState().setValue(ShelfMushroomBlock.FACING, facing));
    }

    private static boolean hasShelfMushroomAt(TreeDecorator.Context context, BlockPos pos) {
        return context.checkBlock(pos, (state) -> state.is(URBlocks.EPIPHYTE.get()));
    }

    public static boolean isBlockReplaceableWithShelfMushroom(TreeDecorator.Context context, BlockPos pos) {
        return context.isReplaceable(pos) && !context.isWaterOrWaterNearby(pos);
    }
}
