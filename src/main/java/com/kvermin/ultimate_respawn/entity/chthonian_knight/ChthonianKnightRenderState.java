package com.kvermin.ultimate_respawn.entity.chthonian_knight;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.AnimationState;

public class ChthonianKnightRenderState extends HumanoidRenderState {
    public boolean hasHelmet;
    public int animationTimeElapsed;
    public final AnimationState readySwingAnimationState;
    public final AnimationState swingAnimationState;

    public ChthonianKnightRenderState() {
        this.readySwingAnimationState = new AnimationState();
        this.swingAnimationState = new AnimationState();
    }
}
