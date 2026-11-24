package com.kvermin.ultimate_respawn.block;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.item.URItems;
import net.minecraft.core.Direction;
import net.minecraft.references.BlockItemIds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class URBlocks {
    private static final BlockBehaviour.StateArgumentPredicate<AABB> NEAR_PLANE_INTERSECTS_OUTLINE = (state, level, blockPos, nearPlaneBox) -> { for (AABB outlineBox : state.getOcclusionShape().toAabbs()) { if (outlineBox.move(blockPos).intersects(nearPlaneBox)) { return true; } } return false; };

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(UltimateRespawn.MOD_ID);

    public static final DeferredBlock<LogBlock> ARCH_TREE_LOG = registerBlock("arch_tree_log", properties -> new LogBlock(logProperties(properties, MapColor.TERRACOTTA_GREEN, MapColor.PODZOL, SoundType.WOOD)));
    public static final DeferredBlock<LogBlock> ARCH_TREE_WOOD = registerBlock("arch_tree_wood", properties -> new LogBlock(logProperties(properties, MapColor.PODZOL, MapColor.PODZOL, SoundType.WOOD)));
    public static final DeferredBlock<LogBlock> STRIPPED_ARCH_TREE_LOG = registerBlock("stripped_arch_tree_log", properties -> new LogBlock(logProperties(properties, MapColor.TERRACOTTA_GREEN, MapColor.TERRACOTTA_GREEN, SoundType.WOOD)));
    public static final DeferredBlock<LogBlock> STRIPPED_ARCH_TREE_WOOD = registerBlock("stripped_arch_tree_wood", properties -> new LogBlock(logProperties(properties, MapColor.TERRACOTTA_GREEN, MapColor.TERRACOTTA_GREEN, SoundType.WOOD)));
    public static final DeferredBlock<PlanksBlock> ARCH_TREE_PLANKS = registerBlock("arch_tree_planks", properties -> new PlanksBlock(archTreePlanks(properties)));
    public static final DeferredBlock<StairBlock> ARCH_TREE_STAIRS = registerBlock("arch_tree_stairs", properties -> new StairBlock(ARCH_TREE_PLANKS.get().defaultBlockState(), archTreePlanks(properties).isViewBlocking(NEAR_PLANE_INTERSECTS_OUTLINE)));
    public static final DeferredBlock<SlabBlock> ARCH_TREE_SLAB = registerBlock("arch_tree_slab", properties -> new SlabBlock(archTreePlanks(properties).isViewBlocking(NEAR_PLANE_INTERSECTS_OUTLINE)));
    public static final DeferredBlock<FenceBlock> ARCH_TREE_FENCE = registerBlock("arch_tree_fence", properties -> new FenceBlock(archTreePlanks(properties).forceSolidOn()));
    public static final DeferredBlock<FenceGateBlock> ARCH_TREE_FENCE_GATE = registerBlock("arch_tree_fence_gate", properties -> new FenceGateBlock(WoodType.JUNGLE, archTreePlanks(properties).forceSolidOn()));
    public static final DeferredBlock<PressurePlateBlock> ARCH_TREE_PRESSURE_PLATE = registerBlock("arch_tree_pressure_plate", properties -> new PressurePlateBlock(BlockSetType.JUNGLE, archTreePlanks(properties).forceSolidOn().noCollision().strength(0.5F).pushReaction(PushReaction.POPPED)));
    public static final DeferredBlock<DoorBlock> ARCH_TREE_DOOR = registerBlock("arch_tree_door", properties -> new DoorBlock(BlockSetType.JUNGLE, archTreePlanks(properties).noOcclusion().strength(3.0F).pushReaction(PushReaction.POPPED)));
    public static final DeferredBlock<TrapDoorBlock> ARCH_TREE_TRAPDOOR = registerBlock("arch_tree_trapdoor", properties -> new TrapDoorBlock(BlockSetType.JUNGLE, archTreePlanks(properties).strength(3.0F).noOcclusion().isValidSpawn(Blocks::never)));
    public static final DeferredBlock<ButtonBlock> ARCH_TREE_BUTTON = registerBlock("arch_tree_button", properties -> new ButtonBlock(BlockSetType.JUNGLE, 30, archTreePlanks(properties).noCollision().strength(0.5F).pushReaction(PushReaction.POPPED)));
    public static final DeferredBlock<ShelfBlock> ARCH_TREE_SHELF = registerBlock("arch_tree_shelf", properties -> new ShelfBlock(archTreePlanks(properties).sound(SoundType.SHELF)));
    // sign, hanging sign, boats, shelf block entity

    public static final DeferredBlock<LogBlock> BUTTRESS_LOG = registerBlock("buttress_log", properties -> new LogBlock(logProperties(properties, MapColor.RAW_IRON, MapColor.RAW_IRON, SoundType.BAMBOO_WOOD)));
    public static final DeferredBlock<LogBlock> BUTTRESS_WOOD = registerBlock("buttress_wood", properties -> new LogBlock(logProperties(properties, MapColor.RAW_IRON, MapColor.RAW_IRON, SoundType.BAMBOO_WOOD)));
    public static final DeferredBlock<PlanksBlock> BUTTRESS_PLANKS = registerBlock("buttress_planks", properties -> new PlanksBlock(buttressPlanks(properties)));
    public static final DeferredBlock<StairBlock> BUTTRESS_STAIRS = registerBlock("buttress_stairs", properties -> new StairBlock(BUTTRESS_PLANKS.get().defaultBlockState(), buttressPlanks(properties).isViewBlocking(NEAR_PLANE_INTERSECTS_OUTLINE)));
    public static final DeferredBlock<SlabBlock> BUTTRESS_SLAB = registerBlock("buttress_slab", properties -> new SlabBlock(buttressPlanks(properties).isViewBlocking(NEAR_PLANE_INTERSECTS_OUTLINE)));
    public static final DeferredBlock<FenceBlock> BUTTRESS_FENCE = registerBlock("buttress_fence", properties -> new FenceBlock(buttressPlanks(properties).forceSolidOn()));
    public static final DeferredBlock<FenceGateBlock> BUTTRESS_FENCE_GATE = registerBlock("buttress_fence_gate", properties -> new FenceGateBlock(buttressPlanks(properties).forceSolidOn(), SoundEvents.BAMBOO_WOOD_FENCE_GATE_OPEN, SoundEvents.BAMBOO_WOOD_FENCE_GATE_CLOSE));
    public static final DeferredBlock<DoorBlock> BUTTRESS_DOOR = registerBlock("buttress_door", properties -> new DoorBlock(BlockSetType.BAMBOO, buttressPlanks(properties).noOcclusion().strength(3.0F).pushReaction(PushReaction.POPPED)));
    public static final DeferredBlock<TrapDoorBlock> BUTTRESS_TRAPDOOR = registerBlock("buttress_trapdoor", properties -> new TrapDoorBlock(BlockSetType.BAMBOO, buttressPlanks(properties).strength(3.0F).noOcclusion().isValidSpawn(Blocks::never)));
    public static final DeferredBlock<PressurePlateBlock> BUTTRESS_PRESSURE_PLATE = registerBlock("buttress_pressure_plate", properties -> new PressurePlateBlock(BlockSetType.BAMBOO, buttressPlanks(properties).forceSolidOn().noCollision().strength(0.5F).pushReaction(PushReaction.POPPED)));
    public static final DeferredBlock<ButtonBlock> BUTTRESS_BUTTON = registerBlock("buttress_button", properties -> new ButtonBlock(BlockSetType.BAMBOO, 30, buttressPlanks(properties).noCollision().strength(0.5F).pushReaction(PushReaction.POPPED)));
    public static final DeferredBlock<ShelfBlock> BUTTRESS_SHELF = registerBlock("buttress_shelf", properties -> new ShelfBlock(buttressPlanks(properties).sound(SoundType.SHELF)));
    public static final DeferredBlock<ButtressRootBlock> BUTTRESS_ROOT = registerBlock("buttress_root", properties -> new ButtressRootBlock(properties.sound(SoundType.BAMBOO_WOOD).strength(2.0F).noOcclusion().ignitedByLava()));

    public static final DeferredBlock<WallPlantBlock> EPIPHYTE = registerBlock("epiphyte", properties -> new WallPlantBlock(properties.mapColor(MapColor.PLANT).strength(0.1F).sound(SoundType.SMALL_DRIPLEAF).pushReaction(PushReaction.POPPED).noCollision().noOcclusion().replaceable()));
    public static final DeferredBlock<HangingCarpetBlock> FAIRY_TABLECLOTH = registerBlock("fairy_tablecloth", properties -> new HangingCarpetBlock(properties.mapColor(MapColor.PLANT).strength(0.1F).sound(SoundType.AZALEA_LEAVES).pushReaction(PushReaction.POPPED).noCollision().noOcclusion().replaceable()));
    public static final DeferredBlock<FlowerBedBlock> GROUND_ARUM = registerBlock("ground_arum", properties -> new FlowerBedBlock(properties.mapColor(MapColor.PLANT).strength(0.1F).sound(SoundType.SMALL_DRIPLEAF).pushReaction(PushReaction.POPPED).noCollision().noOcclusion().replaceable(), 5));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block) {
        DeferredBlock<T> returnBlock = BLOCKS.registerBlock(name, block);
        registerBlockItem(name, returnBlock);
        return returnBlock;
    }
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        URItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    private static BlockBehaviour.Properties logProperties(BlockBehaviour.Properties properties, MapColor topColor, MapColor sideColor, SoundType soundType) {
        return properties.mapColor((state) -> state.getValue(LogBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(soundType).ignitedByLava();
    }
    private static BlockBehaviour.Properties planksProperties(BlockBehaviour.Properties properties) {
        return properties.instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava();
    }
    private static BlockBehaviour.Properties archTreePlanks(BlockBehaviour.Properties properties) {
        return planksProperties(properties).mapColor(MapColor.TERRACOTTA_GREEN);
    }
    private static BlockBehaviour.Properties buttressPlanks(BlockBehaviour.Properties properties) {
        return planksProperties(properties).mapColor(MapColor.RAW_IRON).sound(SoundType.BAMBOO_WOOD);
    }
}
