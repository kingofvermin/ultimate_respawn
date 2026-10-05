package com.kvermin.ultimate_respawn.entity.beetle;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class BeetleRenderState extends LivingEntityRenderState {
    public boolean isProne;
    public boolean isFlying;
    public final AnimationState attackAnimationState;
    public final AnimationState takeoffAnimationState;
    public final AnimationState flyAnimationState;
    public final AnimationState landAnimationState;

    public BeetleRenderState() {
        this.attackAnimationState = new AnimationState();
        this.takeoffAnimationState = new AnimationState();
        this.flyAnimationState = new AnimationState();
        this.landAnimationState = new AnimationState();
    }
}
