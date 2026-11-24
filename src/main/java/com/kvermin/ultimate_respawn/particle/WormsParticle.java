package com.kvermin.ultimate_respawn.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FallingParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;

import java.util.logging.Logger;

public class WormsParticle extends FallingParticle {
    SpriteSet spriteSet;
    int randomOffset;

    public WormsParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, RandomSource random) {
        this.spriteSet = spriteSet;
        super(level, x, y, z, spriteSet.get(random), 16F, 10.0F, false, false, 6.4F, 0F);
        this.randomOffset = random.nextInt(8);
        this.setSprite(spriteSet.get(randomOffset, 8));
        this.xd = random.nextFloat()*0.25 - 0.125;
        this.yd = random.nextFloat()*0.25 + 0.125;
        this.zd = random.nextFloat()*0.25 - 0.125;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.lifetime-- > 0 && !this.onGround) {
            this.removed = false;
        }
        if (this.lifetime % 8 == 0) this.setSprite(spriteSet.get((randomOffset + this.age++) % 9, 8));
    }
}
