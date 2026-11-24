package com.kvermin.ultimate_respawn.entity.chthonian_knight;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.Identifier;

public class ChthonianKnightRenderer extends HumanoidMobRenderer<ChthonianKnight, ChthonianKnightRenderState, ChthonianKnightModel> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "textures/entity/chthonian_knight/chthonian_knight.png");
    private static final Identifier ANIMATED_TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "textures/entity/chthonian_knight/chthonian_knight_alt.png");
    private static final Identifier HELMED_TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "textures/entity/chthonian_knight/chthonian_knight_helmed.png");


    public ChthonianKnightRenderer(EntityRendererProvider.Context context) {
        super(context, new ChthonianKnightModel(
                context.bakeLayer(ChthonianKnightModel.LAYER_LOCATION),
                ChthonianKnightAnimation.CHTHONIAN_KNIGHT_IDLE,
                ChthonianKnightAnimation.CHTHONIAN_KNIGHT_READY_SWING,
                ChthonianKnightAnimation.CHTHONIAN_KNIGHT_SWING,
                ChthonianKnightAnimation.CHTHONIAN_KNIGHT_BLOCK,
                ChthonianKnightAnimation.CHTHONIAN_KNIGHT_RIPOSTE,
                ChthonianKnightAnimation.CHTHONIAN_KNIGHT_LOWER_GUARD
        ), 0.75f);
    }

    @Override
    public ChthonianKnightRenderState createRenderState() { return new ChthonianKnightRenderState(); }

    @Override
    public void extractRenderState(ChthonianKnight entity, ChthonianKnightRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.hasHelmet = entity.hasHelmet();
        state.isIdle = entity.isIdle();
        state.animationTimeElapsed = entity.getAnimationTimeElapsed();
        state.defaultAnimationState.copyFrom(entity.defaultAnimation.animationState());
        state.readySwingAnimationState.copyFrom(entity.readySwingAnimation.animationState());
        state.swingAnimationState.copyFrom(entity.swingAnimation.animationState());
        state.blockAnimationState.copyFrom(entity.blockAnimation.animationState());
        state.riposteAnimationState.copyFrom(entity.riposteAnimation.animationState());
        state.lowerGuardAnimationState.copyFrom(entity.lowerGuardAnimation.animationState());

        if (entity.getDisplacement() != null && entity.getDisplacementOld() != null) {
            state.xMovement = entity.getDisplacement().x();
            state.xMovementOld = entity.getDisplacementOld().x();
            state.zMovement = entity.getDisplacement().z();
            state.zMovementOld = entity.getDisplacementOld().z();
        } else {
            state.xMovement = state.xMovementOld = state.zMovement = state.zMovementOld = 0;
        }
    }

    @Override
    public Identifier getTextureLocation(ChthonianKnightRenderState state) {
        float cycleLength = 15F;

        if (state.hasHelmet) {
            return HELMED_TEXTURE_LOCATION;
        } else if (state.ageInTicks % cycleLength <= cycleLength / 2F) {
            return ANIMATED_TEXTURE_LOCATION;
        } else {
            return TEXTURE_LOCATION;
        }
    }
}
