package com.kvermin.ultimate_respawn.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FallingParticle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import java.util.List;

public class EldritchCloudParticle extends SingleQuadParticle {
    SpriteSet spriteSet;

    public EldritchCloudParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z, 0, 0, 0, spriteSet.first());
        this.lifetime = 40;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.quadSize = 2;
        this.spriteSet = spriteSet;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteSet);
        this.oRoll = this.roll;
        this.roll -= 0.025F;
    }

    @Override
    protected Layer getLayer() { return Layer.OPAQUE; }
}
