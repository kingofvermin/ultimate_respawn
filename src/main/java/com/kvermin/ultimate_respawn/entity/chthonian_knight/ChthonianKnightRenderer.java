package com.kvermin.ultimate_respawn.entity.chthonian_knight;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.Identifier;

public class ChthonianKnightRenderer extends HumanoidMobRenderer<ChthonianKnight, ChthonianKnightRenderState, ChthonianKnightModel> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "textures/entity/chthonian_knight/chthonian_knight.png");
    private static final Identifier ANIMATED_TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "textures/entity/chthonian_knight/chthonian_knight_alt.png");
    private static final Identifier HELMED_TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "textures/entity/chthonian_knight/chthonian_knight_helmed.png");


    public ChthonianKnightRenderer(EntityRendererProvider.Context context) {
        super(context, new ChthonianKnightModel(
                context.bakeLayer(ChthonianKnightModel.LAYER_LOCATION),
                ChthonianKnightAnimation.CHTHONIAN_KNIGHT_READY_SWING,
                ChthonianKnightAnimation.CHTHONIAN_KNIGHT_SWING
        ), 0.75f);
    }

    @Override
    public ChthonianKnightRenderState createRenderState() { return new ChthonianKnightRenderState(); }

    @Override
    public void extractRenderState(ChthonianKnight entity, ChthonianKnightRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.hasHelmet = entity.hasHelmet();
        state.animationTimeElapsed = entity.getAnimationTimeElapsed();
        state.readySwingAnimationState.copyFrom(entity.readySwingAnimationState);
        state.swingAnimationState.copyFrom(entity.swingAnimationState);
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
