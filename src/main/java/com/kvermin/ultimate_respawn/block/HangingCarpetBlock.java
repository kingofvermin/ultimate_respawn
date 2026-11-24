package com.kvermin.ultimate_respawn.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;

public class HangingCarpetBlock extends CarpetBlock implements BonemealableBlock {
    public HangingCarpetBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockBelow = level.getBlockState(pos.below());
        return super.canSurvive(state, level, pos) && blockBelow.isFaceSturdy(level, pos.below(), Direction.UP, SupportType.CENTER);
    }

    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, BonemealSource source) {
        return BonemealableBlock.hasSpreadableNeighbourPos(level, pos, state);
    }

    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        return true;
    }

    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        BonemealableBlock.findSpreadableNeighbourPos(level, pos, state).ifPresent((blockPos) -> level.setBlockAndUpdate(blockPos, this.defaultBlockState()));
    }
}
