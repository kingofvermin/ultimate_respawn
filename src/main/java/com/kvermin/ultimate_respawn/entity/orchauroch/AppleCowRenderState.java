package com.kvermin.ultimate_respawn.entity.orchauroch;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class AppleCowRenderState extends LivingEntityRenderState {
    public int growthStage;
    public final AnimationState shakeAnimationState;

    public AppleCowRenderState() {
        this.shakeAnimationState = new AnimationState();
    }
}
