package com.kvermin.ultimate_respawn.item;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.entity.URMobs;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class URItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateRespawn.MODID);

    public static final DeferredItem<Item> ORICHALCUM_SCRAP = ITEMS.registerSimpleItem("orichalcum_scrap");
    public static final DeferredItem<Item> ORICHALCUM_INGOT = ITEMS.registerSimpleItem("orichalcum_ingot");
    public static final DeferredItem<Item> ORICHALCUM_GREATSWORD = ITEMS.registerSimpleItem(
            "orichalcum_greatsword", () -> (new Item.Properties()).sword(ToolMaterial.NETHERITE, 4F, -3F).attributes(ItemAttributeModifiers.builder().add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "base_attack_range"), 0.8D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build())
    );
}