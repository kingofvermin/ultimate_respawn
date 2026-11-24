package com.kvermin.ultimate_respawn.particle;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public class EldritchTendrilParticle extends SingleQuadParticle {
    SpriteSet spriteSet;
    boolean flipped;
    float yRot;
    float zRot;

    public EldritchTendrilParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z, 0, 0, 0, spriteSet.first());
        this.lifetime = 20 + random.nextInt(20);
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.flipped = random.nextBoolean();
        this.yRot = random.nextFloat() * Mth.TWO_PI;
        this.zRot = random.nextFloat() * Mth.TWO_PI;
        this.quadSize = 2;
        this.spriteSet = spriteSet;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteSet);
    }

    @Override
    protected Layer getLayer() { return Layer.OPAQUE; }
    @Override
    public float getU0() { return this.flipped ? super.getU1() : super.getU0(); }
    @Override
    public float getU1() { return this.flipped ? super.getU0() : super.getU1(); }

    @Override
    public void extract(QuadParticleRenderState particleTypeRenderState, Camera camera, float partialTickTime) {
        Quaternionf rotation = new Quaternionf();
        rotation.rotationYXZ(this.yRot, 0F, this.zRot);
        this.extractRotatedQuad(particleTypeRenderState, camera, rotation, partialTickTime);
    }
}
