package com.kvermin.ultimate_respawn.entity.beetle;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class BeetleRenderer extends MobRenderer<Beetle, BeetleRenderState, BeetleModel<Beetle>> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "textures/entity/beetle/druid_blue.png");

    public BeetleRenderer(EntityRendererProvider.Context context) {
        super(context, new BeetleModel<Beetle>(context.bakeLayer(BeetleModel.LAYER_LOCATION), BeetleAnimation.BEETLE_WALK, BeetleAnimation.BEETLE_ATTACK, BeetleAnimation.BEETLE_TAKE_OFF, BeetleAnimation.BEETLE_FLY, BeetleAnimation.BEETLE_LAND), 1.5f);
    }

    public BeetleRenderState createRenderState() {
        return new BeetleRenderState();
    }

    public static void extractAdditionalState(Beetle entity, BeetleRenderState state, float partialTicks) {
        state.isProne = entity.getEntityData().get(Beetle.PRONE);
        state.isFlying = entity.isFlying();
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
        state.takeoffAnimationState.copyFrom(entity.takeoffAnimationState);
        state.flyAnimationState.copyFrom(entity.flyAnimationState);
        state.landAnimationState.copyFrom(entity.landAnimationState);
    }

    @Override
    public Identifier getTextureLocation(BeetleRenderState beetleRenderState) {
        return TEXTURE_LOCATION;
    }
}