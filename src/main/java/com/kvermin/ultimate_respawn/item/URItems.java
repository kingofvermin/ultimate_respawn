package com.kvermin.ultimate_respawn.item;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.entity.URMobs;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class URItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UltimateRespawn.MODID);

    public static final DeferredItem<Item> CRUOR_BALL = ITEMS.registerSimpleItem("cruor_ball");
    public static final DeferredItem<Item> ORICHALCUM_SCRAP = ITEMS.registerSimpleItem("orichalcum_scrap");
    public static final DeferredItem<Item> ORICHALCUM_INGOT = ITEMS.registerSimpleItem("orichalcum_ingot");
    public static final DeferredItem<Item> ORICHALCUM_GREATSWORD = ITEMS.registerSimpleItem(
            "orichalcum_greatsword", () -> new Item.Properties().sword(ToolMaterial.NETHERITE, 0, 0).attributes(ItemAttributeModifiers.builder()
                    .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 8, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, -2.6, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                    .build())
                    .component(DataComponents.ATTACK_RANGE, new AttackRange(0.0F, 4.0F, 0.0F, 6.0F, 0.125F, 0.5F))
    );

    public static final DeferredItem<SpawnEggItem> BEETLE_SPAWN_EGG = ITEMS.registerItem("beetle_spawn_egg", SpawnEggItem::new, () -> new Item.Properties().spawnEgg(URMobs.BEETLE.get()));
    public static final DeferredItem<SpawnEggItem> BOREHOLE_ANT_SPAWN_EGG = ITEMS.registerItem("borehole_ant_spawn_egg", SpawnEggItem::new, () -> new Item.Properties().spawnEgg(URMobs.BOREHOLE_ANT.get()));
    public static final DeferredItem<SpawnEggItem> CHTHONIAN_KNIGHT_SPAWN_EGG = ITEMS.registerItem("chthonian_knight_spawn_egg", SpawnEggItem::new, () -> new Item.Properties().spawnEgg(URMobs.CHTHONIAN_KNIGHT.get()));
    public static final DeferredItem<SpawnEggItem> ORCHAUROCH_SPAWN_EGG = ITEMS.registerItem("orchauroch_spawn_egg", SpawnEggItem::new, () -> new Item.Properties().spawnEgg(URMobs.ORCHAUROCH.get()));
    public static final DeferredItem<SpawnEggItem> WORMS_SPAWN_EGG = ITEMS.registerItem("worms_spawn_egg", SpawnEggItem::new, () -> new Item.Properties().spawnEgg(URMobs.WORMS.get()));
}