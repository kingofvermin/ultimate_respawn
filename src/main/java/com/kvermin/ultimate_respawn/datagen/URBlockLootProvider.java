package com.kvermin.ultimate_respawn.datagen;

import com.kvermin.ultimate_respawn.block.URBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class URBlockLootProvider extends BlockLootSubProvider {
    private static final Set<Item> EXPLOSION_RESISTANT = Set.of();

    protected URBlockLootProvider(Context context) {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), context);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return URBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

    @Override
    protected void generate() {
        dropSelf(URBlocks.ARCH_TREE_LOG.get());
        dropSelf(URBlocks.ARCH_TREE_WOOD.get());
        dropSelf(URBlocks.STRIPPED_ARCH_TREE_LOG.get());
        dropSelf(URBlocks.STRIPPED_ARCH_TREE_WOOD.get());
        dropSelf(URBlocks.ARCH_TREE_PLANKS.get());
        dropSelf(URBlocks.ARCH_TREE_STAIRS.get());
        add(URBlocks.ARCH_TREE_SLAB.get(), this::createSlabItemTable);
        dropSelf(URBlocks.ARCH_TREE_FENCE.get());
        dropSelf(URBlocks.ARCH_TREE_FENCE_GATE.get());
        add(URBlocks.ARCH_TREE_DOOR.get(), this::createDoorTable);
        dropSelf(URBlocks.ARCH_TREE_TRAPDOOR.get());
        dropSelf(URBlocks.ARCH_TREE_PRESSURE_PLATE.get());
        dropSelf(URBlocks.ARCH_TREE_BUTTON.get());
        dropSelf(URBlocks.ARCH_TREE_SHELF.get());

        dropSelf(URBlocks.BUTTRESS_LOG.get());
        dropSelf(URBlocks.BUTTRESS_WOOD.get());
        dropSelf(URBlocks.BUTTRESS_PLANKS.get());
        dropSelf(URBlocks.BUTTRESS_STAIRS.get());
        add(URBlocks.BUTTRESS_SLAB.get(), this::createSlabItemTable);
        dropSelf(URBlocks.BUTTRESS_FENCE.get());
        dropSelf(URBlocks.BUTTRESS_FENCE_GATE.get());
        add(URBlocks.BUTTRESS_DOOR.get(), this::createDoorTable);
        dropSelf(URBlocks.BUTTRESS_TRAPDOOR.get());
        dropSelf(URBlocks.BUTTRESS_PRESSURE_PLATE.get());
        dropSelf(URBlocks.BUTTRESS_BUTTON.get());
        dropSelf(URBlocks.BUTTRESS_SHELF.get());
        dropSelf(URBlocks.BUTTRESS_ROOT.get());

        add(URBlocks.EPIPHYTE.get(), this::createShearsOrSilkTouchOnlyDrop);
        add(URBlocks.FAIRY_TABLECLOTH.get(), this::createShearsOrSilkTouchOnlyDrop);
        add(URBlocks.GROUND_ARUM.get(), this::createShearsOrSilkTouchOnlyDrop);
    }
}
