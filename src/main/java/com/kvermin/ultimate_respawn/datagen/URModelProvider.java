package com.kvermin.ultimate_respawn.datagen;

import com.google.common.collect.Maps;
import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.block.ButtressRootBlock;
import com.kvermin.ultimate_respawn.block.URBlocks;
import com.kvermin.ultimate_respawn.item.URItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

import static net.minecraft.client.data.models.BlockModelGenerators.*;
import static net.minecraft.client.data.models.ItemModelGenerators.createFlatModelDispatch;

public class URModelProvider extends ModelProvider {

    public static final Map<Block, BlockFamily> BLOCK_FAMILIES = Maps.newHashMap(Map.of(
            URBlocks.ARCH_TREE_PLANKS.get(), new BlockFamily.Builder(URBlocks.ARCH_TREE_PLANKS.get())
                    .log(URBlocks.ARCH_TREE_LOG.get())
                    .strippedLog(URBlocks.STRIPPED_ARCH_TREE_LOG.get())
                    .stairs(URBlocks.ARCH_TREE_STAIRS.get())
                    .slab(URBlocks.ARCH_TREE_SLAB.get())
                    .fence(URBlocks.ARCH_TREE_FENCE.get())
                    .fenceGate(URBlocks.ARCH_TREE_FENCE_GATE.get())
                    .door(URBlocks.ARCH_TREE_DOOR.get())
                    .trapdoor(URBlocks.ARCH_TREE_TRAPDOOR.get())
                    .pressurePlate(URBlocks.ARCH_TREE_PRESSURE_PLATE.get())
                    .button(URBlocks.ARCH_TREE_BUTTON.get())
                    // .sign(URBlocks.ARCH_TREE_SIGN.get(), URBlocks.ARCH_TREE_WALL_SIGN.get())
                    // .hangingSign(URBlocks.ARCH_TREE_HANGING_SIGN.get(), URBlocks.ARCH_TREE_WALL_HANGING_SIGN.get())
                    .recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily(),
            URBlocks.BUTTRESS_PLANKS.get(), new BlockFamily.Builder(URBlocks.BUTTRESS_PLANKS.get())
                    .log(URBlocks.BUTTRESS_LOG.get())
                    .stairs(URBlocks.BUTTRESS_STAIRS.get())
                    .slab(URBlocks.BUTTRESS_SLAB.get())
                    .fence(URBlocks.BUTTRESS_FENCE.get())
                    .fenceGate(URBlocks.BUTTRESS_FENCE_GATE.get())
                    .door(URBlocks.BUTTRESS_DOOR.get())
                    .trapdoor(URBlocks.BUTTRESS_TRAPDOOR.get())
                    .pressurePlate(URBlocks.BUTTRESS_PRESSURE_PLATE.get())
                    .button(URBlocks.BUTTRESS_BUTTON.get())
                    // .sign(URBlocks.BUTTRESS_SIGN.get(), URBlocks.BUTTRESS_WALL_SIGN.get())
                    // .hangingSign(URBlocks.BUTTRESS_HANGING_SIGN.get(), URBlocks.BUTTRESS_WALL_HANGING_SIGN.get())
                    .recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily()
    ));

    public URModelProvider(PackOutput output) {
        super(output, UltimateRespawn.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(URItems.CHITIN.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(URItems.CRUOR_BALL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(URItems.ORICHALCUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(URItems.ORICHALCUM_SCRAP.get(), ModelTemplates.FLAT_ITEM);
        generateGreataxe(itemModels, URItems.ORICHALCUM_DREDGE.get());
        generateGreatsword(itemModels, URItems.ORICHALCUM_GREATSWORD.get());

        itemModels.generateFlatItem(URItems.BEETLE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(URItems.BOREHOLE_ANT_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(URItems.CHTHONIAN_KNIGHT_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(URItems.ORCHAUROCH_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(URItems.WORMS_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);

        BLOCK_FAMILIES.values().stream()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(blockFamily -> {
                    blockModels.family(blockFamily.getBaseBlock()).generateFor(blockFamily);
                });

        blockModels.woodProvider(URBlocks.ARCH_TREE_LOG.get()).logWithHorizontal(URBlocks.ARCH_TREE_LOG.get()).wood(URBlocks.ARCH_TREE_WOOD.get());
        blockModels.woodProvider(URBlocks.STRIPPED_ARCH_TREE_LOG.get()).logWithHorizontal(URBlocks.STRIPPED_ARCH_TREE_LOG.get()).wood(URBlocks.STRIPPED_ARCH_TREE_WOOD.get());
        blockModels.createShelf(URBlocks.ARCH_TREE_SHELF.get(), URBlocks.STRIPPED_ARCH_TREE_LOG.get());

        blockModels.woodProvider(URBlocks.BUTTRESS_LOG.get()).logWithHorizontal(URBlocks.BUTTRESS_LOG.get()).wood(URBlocks.BUTTRESS_WOOD.get());
        blockModels.createShelf(URBlocks.BUTTRESS_SHELF.get(), URBlocks.BUTTRESS_LOG.get());
        createButtressRoot(blockModels, URBlocks.BUTTRESS_ROOT.get());

        blockModels.createNonTemplateHorizontalBlock(URBlocks.EPIPHYTE.get());
        blockModels.registerSimpleFlatItemModel(URBlocks.EPIPHYTE.get());
        blockModels.createNonTemplateModelBlock(URBlocks.FAIRY_TABLECLOTH.get());
        blockModels.registerSimpleFlatItemModel(URBlocks.FAIRY_TABLECLOTH.get());
        blockModels.createFlowerBed(URBlocks.GROUND_ARUM.get());
    }

    public void generateGreatsword(ItemModelGenerators itemModels, Item item) {
        ItemModel.Unbaked flatModel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked inHandModel = ItemModelUtils.plainModel(ModelTemplates.createItem(UltimateRespawn.MOD_ID + ":greatsword_in_hand", "_in_hand", TextureSlot.LAYER0).create(item, TextureMapping.layer0(TextureMapping.getItemTexture(item, "_in_hand")), itemModels.modelOutput));
        itemModels.itemModelOutput.accept(item, createFlatModelDispatch(flatModel, inHandModel), new ClientItem.Properties(true, false, 1.95F));
    }
    public void generateGreataxe(ItemModelGenerators itemModels, Item item) {
        ItemModel.Unbaked flatModel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked inHandModel = ItemModelUtils.plainModel(ModelTemplates.createItem(UltimateRespawn.MOD_ID + ":greataxe_in_hand", "_in_hand", TextureSlot.LAYER0).create(item, TextureMapping.layer0(TextureMapping.getItemTexture(item, "_in_hand")), itemModels.modelOutput));
        itemModels.itemModelOutput.accept(item, createFlatModelDispatch(flatModel, inHandModel), new ClientItem.Properties(true, false, 1.95F));
    }

    public void createButtressRoot(BlockModelGenerators blockModels, Block block) {
        MultiVariant base = variants(
                plainModel(ModelLocationUtils.getModelLocation(block, "_base")),
                plainModel(ModelLocationUtils.getModelLocation(block, "_1")),
                plainModel(ModelLocationUtils.getModelLocation(block, "_2"))
        );
        MultiVariant top = plainVariant(ModelLocationUtils.getModelLocation(block));
        PropertyDispatch<MultiVariant> TOP_VARIANT = PropertyDispatch.initial(ButtressRootBlock.TOP).select(true, top).select(false, base);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(TOP_VARIANT).with(ROTATION_HORIZONTAL_FACING));
    }
}
