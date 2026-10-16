package com.kvermin.ultimate_respawn.datagen;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.item.URItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

import static net.minecraft.client.data.models.ItemModelGenerators.createFlatModelDispatch;

public class URModelProvider extends ModelProvider {

    public URModelProvider(PackOutput output) {
        super(output, UltimateRespawn.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(URItems.CRUOR_BALL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(URItems.ORICHALCUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(URItems.ORICHALCUM_SCRAP.get(), ModelTemplates.FLAT_ITEM);
        generateGreatsword(itemModels, URItems.ORICHALCUM_GREATSWORD.get());

        itemModels.generateFlatItem(URItems.BEETLE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(URItems.BOREHOLE_ANT_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(URItems.CHTHONIAN_KNIGHT_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(URItems.ORCHAUROCH_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(URItems.WORMS_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
    }

    public void generateGreatsword(ItemModelGenerators itemModels, Item item) {
        ItemModel.Unbaked flatModel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked inHandModel = ItemModelUtils.plainModel(ModelTemplates.createItem(UltimateRespawn.MODID + ":greatsword_in_hand", "_in_hand", TextureSlot.LAYER0).create(item, TextureMapping.layer0(TextureMapping.getItemTexture(item, "_in_hand")), itemModels.modelOutput));
        itemModels.itemModelOutput.accept(item, createFlatModelDispatch(flatModel, inHandModel), new ClientItem.Properties(true, false, 1.95F));
    }
}
