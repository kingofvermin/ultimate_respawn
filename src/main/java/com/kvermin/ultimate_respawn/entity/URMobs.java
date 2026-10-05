package com.kvermin.ultimate_respawn.entity;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.entity.beetle.Beetle;
import com.kvermin.ultimate_respawn.entity.borehole_ant.BoreholeWorker;
import com.kvermin.ultimate_respawn.entity.orchauroch.AppleCow;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class URMobs {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.createEntities(UltimateRespawn.MODID);

    public static final Supplier<EntityType<Beetle>> BEETLE = ENTITY_TYPES.register(
            "beetle", () -> EntityType.Builder.<Beetle>of(Beetle::new, MobCategory.CREATURE).sized(1.75F,2).eyeHeight(0.42F).build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "beetle")))
    );
    public static final Supplier<EntityType<BoreholeWorker>> BOREHOLE_ANT = ENTITY_TYPES.register(
            "borehole_ant", () -> EntityType.Builder.<BoreholeWorker>of(BoreholeWorker::new, MobCategory.CREATURE).sized(1, 1).build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "borehole_ant")))
    );
    public static final Supplier<EntityType<AppleCow>> ORCHAUROCH = ENTITY_TYPES.register(
            "orchauroch", () -> EntityType.Builder.<AppleCow>of(AppleCow::new, MobCategory.CREATURE).sized(1.5F,2).build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "orchauroch")))
    );
}
