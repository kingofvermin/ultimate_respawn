package com.kvermin.ultimate_respawn.particle;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class URParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, UltimateRespawn.MOD_ID);

    public static final Supplier<SimpleParticleType> ELDRITCH_CLOUD = PARTICLE_TYPES.register(
            "eldritch_cloud",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> ELDRITCH_TENDRIL = PARTICLE_TYPES.register(
            "eldritch_tendril",
            () -> new SimpleParticleType(false)
    );
    public static final Supplier<SimpleParticleType> WORMS = PARTICLE_TYPES.register(
            "worms",
            () -> new SimpleParticleType(false)
    );
}
