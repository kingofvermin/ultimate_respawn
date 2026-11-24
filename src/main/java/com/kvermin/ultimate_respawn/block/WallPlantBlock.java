package com.kvermin.ultimate_respawn.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.logging.Logger;

public class WallPlantBlock extends LadderBlock implements VerticalBonemealableBlock {
    public WallPlantBlock(Properties properties) {
        super(properties);
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return (VoxelShape) SHAPES.get(state.getValue(FACING));
    }

    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, BonemealSource source) {
        return VerticalBonemealableBlock.hasSpreadableNeighbourPos(level, pos, state);
    }

    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        return true;
    }

    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        VerticalBonemealableBlock.findSpreadableNeighbourPos(level, pos, state).ifPresent((blockPos) -> level.setBlockAndUpdate(blockPos, this.defaultBlockState().setValue(FACING, state.getValue(FACING))));
    }
}
