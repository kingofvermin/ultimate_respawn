package com.kvermin.ultimate_respawn.particle;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class URParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, UltimateRespawn.MODID);

    public static final Supplier<SimpleParticleType> WORMS = PARTICLE_TYPES.register(
            "worms",
            () -> new SimpleParticleType(false)
    );
}
