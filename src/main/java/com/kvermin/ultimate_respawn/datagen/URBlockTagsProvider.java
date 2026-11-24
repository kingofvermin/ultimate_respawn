package com.kvermin.ultimate_respawn.datagen;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.block.URBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.internal.NeoForgeBlockTagsProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class URBlockTagsProvider extends BlockTagsProvider {
    public static final List<ResourceKey<Block>> NATURAL_LOGS = List.of(
            URBlocks.ARCH_TREE_LOG.getKey(),
            URBlocks.BUTTRESS_LOG.getKey()
    );
    public static final List<ResourceKey<Block>> NATURAL_WOODS = List.of(
            URBlocks.ARCH_TREE_WOOD.getKey(),
            URBlocks.BUTTRESS_WOOD.getKey()
    );
    public static final List<ResourceKey<Block>> STRIPPED_LOGS = List.of(
            URBlocks.STRIPPED_ARCH_TREE_LOG.getKey()
    );
    public static final List<ResourceKey<Block>> STRIPPED_WOODS = List.of(
            URBlocks.STRIPPED_ARCH_TREE_WOOD.getKey()
    );
    public static final List<ResourceKey<Block>> LOGS = Stream.of(
            NATURAL_LOGS.stream(),
            NATURAL_WOODS.stream(),
            STRIPPED_LOGS.stream(),
            STRIPPED_WOODS.stream()
            ).flatMap(key -> key).toList();

    public static final List<ResourceKey<Block>> PLANKS = List.of(
            URBlocks.ARCH_TREE_PLANKS.getKey(),
            URBlocks.BUTTRESS_PLANKS.getKey()
    );
    public static final List<ResourceKey<Block>> WOODEN_STAIRS = List.of(
            URBlocks.ARCH_TREE_STAIRS.getKey(),
            URBlocks.BUTTRESS_STAIRS.getKey()
    );
    public static final List<ResourceKey<Block>> WOODEN_SLABS = List.of(
            URBlocks.ARCH_TREE_SLAB.getKey(),
            URBlocks.BUTTRESS_SLAB.getKey()
    );
    public static final List<ResourceKey<Block>> WOODEN_FENCES = List.of(
            URBlocks.ARCH_TREE_FENCE.getKey(),
            URBlocks.BUTTRESS_FENCE.getKey()
    );
    public static final List<ResourceKey<Block>> WOODEN_FENCE_GATES = List.of(
            URBlocks.ARCH_TREE_FENCE_GATE.getKey(),
            URBlocks.BUTTRESS_FENCE_GATE.getKey()
    );
    public static final List<ResourceKey<Block>> WOODEN_DOORS = List.of(
            URBlocks.ARCH_TREE_DOOR.getKey(),
            URBlocks.BUTTRESS_DOOR.getKey()
    );
    public static final List<ResourceKey<Block>> WOODEN_TRAPDOORS = List.of(
            URBlocks.ARCH_TREE_TRAPDOOR.getKey(),
            URBlocks.BUTTRESS_TRAPDOOR.getKey()
    );
    public static final List<ResourceKey<Block>> WOODEN_PRESSURE_PLATES = List.of(
            URBlocks.ARCH_TREE_PRESSURE_PLATE.getKey(),
            URBlocks.BUTTRESS_PRESSURE_PLATE.getKey()
    );
    public static final List<ResourceKey<Block>> WOODEN_BUTTONS = List.of(
            URBlocks.ARCH_TREE_BUTTON.getKey(),
            URBlocks.BUTTRESS_BUTTON.getKey()
    );
    public static final List<ResourceKey<Block>> WOODEN_SHELVES = List.of(
            URBlocks.ARCH_TREE_SHELF.getKey(),
            URBlocks.BUTTRESS_SHELF.getKey()
    );
    public static final List<ResourceKey<Block>> WOOD_SETS = Stream.of(
            PLANKS.stream(),
            WOODEN_STAIRS.stream(),
            WOODEN_SLABS.stream(),
            WOODEN_FENCES.stream(),
            WOODEN_FENCE_GATES.stream(),
            WOODEN_DOORS.stream(),
            WOODEN_TRAPDOORS.stream(),
            WOODEN_PRESSURE_PLATES.stream(),
            WOODEN_BUTTONS.stream(),
            WOODEN_SHELVES.stream()
    ).flatMap(key -> key).toList();

    public URBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, UltimateRespawn.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        Stream.of(
                        LOGS.stream(),
                        WOOD_SETS.stream()
                ).flatMap(key -> key)
                .forEach(key -> tag(BlockTags.MINEABLE_WITH_AXE).add(key));

        LOGS.forEach(key -> tag(BlockTags.LOGS).add(key));

        NATURAL_LOGS.forEach(key -> tag(Tags.Blocks.NATURAL_LOGS).add(key));
        NATURAL_WOODS.forEach(key -> tag(Tags.Blocks.NATURAL_WOODS).add(key));
        STRIPPED_LOGS.forEach(key -> tag(Tags.Blocks.STRIPPED_LOGS).add(key));
        STRIPPED_WOODS.forEach(key -> tag(Tags.Blocks.STRIPPED_WOODS).add(key));

        tag(BlockTags.JUNGLE_LOGS)
                .add(URBlocks.ARCH_TREE_LOG.getKey())
                .add(URBlocks.ARCH_TREE_WOOD.getKey())
                .add(URBlocks.STRIPPED_ARCH_TREE_LOG.getKey())
                .add(URBlocks.STRIPPED_ARCH_TREE_WOOD.getKey());

        PLANKS.forEach(key -> tag(BlockTags.PLANKS).add(key));
        WOODEN_STAIRS.forEach(key -> tag(BlockTags.WOODEN_STAIRS).add(key));
        WOODEN_SLABS.forEach(key -> tag(BlockTags.WOODEN_SLABS).add(key));
        WOODEN_FENCES.forEach(key -> tag(BlockTags.WOODEN_FENCES).add(key));
        WOODEN_FENCE_GATES.forEach(key -> tag(BlockTags.FENCE_GATES).add(key));
        WOODEN_FENCE_GATES.forEach(key -> tag(Tags.Blocks.FENCE_GATES_WOODEN).add(key));
        WOODEN_DOORS.forEach(key -> tag(BlockTags.WOODEN_DOORS).add(key));
        WOODEN_TRAPDOORS.forEach(key -> tag(BlockTags.WOODEN_TRAPDOORS).add(key));
        WOODEN_PRESSURE_PLATES.forEach(key -> tag(BlockTags.WOODEN_PRESSURE_PLATES).add(key));
        WOODEN_BUTTONS.forEach(key -> tag(BlockTags.WOODEN_BUTTONS).add(key));
        WOODEN_SHELVES.forEach(key -> tag(BlockTags.WOODEN_SHELVES).add(key));
    }
}
