package com.kvermin.ultimate_respawn.entity.chthonian_knight;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.AnimationState;

public class ChthonianKnightRenderState extends HumanoidRenderState {
    public float lastTick;
    public boolean hasHelmet;
    public boolean isIdle;
    public double xMovement;
    public double xMovementOld;
    public double zMovement;
    public double zMovementOld;
    public int animationTimeElapsed;
    public final AnimationState defaultAnimationState;
    public final AnimationState readySwingAnimationState;
    public final AnimationState swingAnimationState;
    public final AnimationState blockAnimationState;
    public final AnimationState riposteAnimationState;
    public final AnimationState lowerGuardAnimationState;

    public ChthonianKnightRenderState() {
        this.defaultAnimationState = new AnimationState();
        this.readySwingAnimationState = new AnimationState();
        this.swingAnimationState = new AnimationState();
        this.blockAnimationState = new AnimationState();
        this.riposteAnimationState = new AnimationState();
        this.lowerGuardAnimationState = new AnimationState();
    }
}
