package com.kvermin.ultimate_respawn.item;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class URItemTags {
    public static final TagKey<Item> ORICHALCUM_TOOL_MATERIALS = createTag("orichalcum_tool_materials");

    private static TagKey<Item> createTag(String name) {
        return ItemTags.create(Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, name));
    }
}
