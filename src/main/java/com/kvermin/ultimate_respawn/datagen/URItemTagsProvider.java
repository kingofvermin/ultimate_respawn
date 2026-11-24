package com.kvermin.ultimate_respawn.datagen;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.item.URItemTags;
import com.kvermin.ultimate_respawn.item.URItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class URItemTagsProvider extends ItemTagsProvider {
    public URItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, UltimateRespawn.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.PICKAXES)
                .add(URItems.ORICHALCUM_DREDGE.getKey());
        tag(ItemTags.SWORDS)
                .add(URItems.ORICHALCUM_GREATSWORD.getKey());

        tag(URItemTags.ORICHALCUM_TOOL_MATERIALS)
                .add(URItems.ORICHALCUM_INGOT.getKey());
    }
}
