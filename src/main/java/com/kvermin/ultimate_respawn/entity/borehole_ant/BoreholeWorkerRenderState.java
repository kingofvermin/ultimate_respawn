package com.kvermin.ultimate_respawn.entity.borehole_ant;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemStack;

public class BoreholeWorkerRenderState extends LivingEntityRenderState {
    public boolean holdingValidItem;
    public ItemStack holdingItem;
    public final AnimationState idleAnimationState;

    public BoreholeWorkerRenderState() {
        this.holdingItem = ItemStack.EMPTY;
        this.idleAnimationState = new AnimationState();
    }
}
