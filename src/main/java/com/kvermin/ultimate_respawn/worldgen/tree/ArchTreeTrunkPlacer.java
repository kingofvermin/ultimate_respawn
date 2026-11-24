package com.kvermin.ultimate_respawn.worldgen.tree;

import com.google.common.collect.Lists;
import com.kvermin.ultimate_respawn.block.URBlocks;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ArchTreeTrunkPlacer extends GiantTrunkPlacer {
    public static final MapCodec<ArchTreeTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((i) -> trunkPlacerParts(i).apply(i, ArchTreeTrunkPlacer::new));

    public ArchTreeTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }
    @Override
    protected TrunkPlacerType<?> type() { return URTreePartTypes.ARCH_TREE_TRUNK_PLACER.get(); }

    public List<FoliagePlacer.FoliageAttachment> placeTrunk(WorldGenLevel level, BiConsumer<BlockPos, BlockState> trunkSetter, RandomSource random, int treeHeight, BlockPos origin, TreeFeature tree) {
        List<FoliagePlacer.FoliageAttachment> attachments = Lists.newArrayList();
        attachments.addAll(super.placeTrunk(level, trunkSetter, random, treeHeight, origin, tree));
        placeLog(level, trunkSetter, random, origin, tree);

        for (int i = 0; i < 5; ++i) {
            placeBranch(level, trunkSetter, random, treeHeight - random.nextInt(3), 5 + random.nextInt(3), i * Mth.PI * 2/5, origin, tree, attachments);
        }
        for (int branchHeight = treeHeight - 2 - random.nextInt(4); branchHeight > treeHeight * 3/4; branchHeight -= 1 + random.nextInt(2)) {
            placeBranch(level, trunkSetter, random, branchHeight, origin, tree, attachments);
        }

        return attachments;
    }

    private void placeBranch(WorldGenLevel level, BiConsumer<BlockPos, BlockState> trunkSetter, RandomSource random, int branchHeight, BlockPos origin, TreeFeature tree, List<FoliagePlacer.FoliageAttachment> attachments) {
        placeBranch(level, trunkSetter, random, branchHeight, 5, random.nextFloat() * ((float)Math.PI * 2F), origin, tree, attachments);
    }
    private void placeBranch(WorldGenLevel level, BiConsumer<BlockPos, BlockState> trunkSetter, RandomSource random, int branchHeight, int branchLength, float angle, BlockPos origin, TreeFeature tree, List<FoliagePlacer.FoliageAttachment> attachments) {
        int bx = 0;
        int bz = 0;

        for(int b = 0; b < branchLength; ++b) {
            bx = (int)(1.5F + Mth.cos((double)angle) * (float)b);
            bz = (int)(1.5F + Mth.sin((double)angle) * (float)b);
            BlockPos pos = origin.offset(bx, branchHeight - 3 + b / 2, bz);
            this.placeLog(level, trunkSetter, random, pos, tree);
        }

        attachments.add(new FoliagePlacer.FoliageAttachment(origin.offset(bx, branchHeight, bz), -2, false));
    }
}
