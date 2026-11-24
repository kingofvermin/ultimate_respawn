package com.kvermin.ultimate_respawn.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface VerticalBonemealableBlock extends BonemealableBlock {
    static boolean hasSpreadableNeighbourPos(LevelReader level, BlockPos pos, BlockState blockToPlace) {
        return getSpreadableNeighbourPos(Stream.concat(Direction.Plane.HORIZONTAL.stream(), Direction.Plane.VERTICAL.stream()).toList(), level, pos, blockToPlace).isPresent();
    }
    static Optional<BlockPos> findSpreadableNeighbourPos(Level level, BlockPos pos, BlockState blockToPlace) {
        List<Direction> directions = Stream.concat(Direction.Plane.HORIZONTAL.shuffledCopy(level.getRandom()).stream(), Direction.Plane.VERTICAL.shuffledCopy(level.getRandom()).stream()).toList();
        return getSpreadableNeighbourPos(directions, level, pos, blockToPlace);
    }
    private static Optional<BlockPos> getSpreadableNeighbourPos(List<Direction> directions, LevelReader level, BlockPos pos, BlockState blockToPlace) {
        for(Direction direction : directions) {
            BlockPos neighbourPos = pos.relative(direction);
            if (level.isEmptyBlock(neighbourPos) && blockToPlace.canSurvive(level, neighbourPos)) {
                return Optional.of(neighbourPos);
            }
        }

        return Optional.empty();
    }
}
