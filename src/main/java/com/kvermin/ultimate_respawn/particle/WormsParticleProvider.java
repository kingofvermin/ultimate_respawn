package com.kvermin.ultimate_respawn.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.Nullable;

public class WormsParticleProvider implements ParticleProvider<SimpleParticleType> {

    private final SpriteSet spriteSet;

    public WormsParticleProvider(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xd, double yd, double zd, RandomSource random) {
        return new WormsParticle(level, x, y, z, this.spriteSet, random);
    }
}
