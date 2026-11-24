package com.kvermin.ultimate_respawn.entity.worms;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class WormsRenderer extends MobRenderer<Worms, WormsRenderState, WormsModel<Worms>> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "textures/entity/worms/worms.png");
    private static final Identifier ANIMATED_TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "textures/entity/worms/worms_alt.png");

    public WormsRenderer(EntityRendererProvider.Context context) {
        super(context, new WormsModel<Worms>(context.bakeLayer(WormsModel.LAYER_LOCATION)), 0.75f);
    }

    @Override
    public WormsRenderState createRenderState() {
        return new WormsRenderState();
    }

    @Override
    public void extractRenderState(Worms entity, WormsRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.writhingStartTick = entity.getWrithingStartTick();

        if (state.deathTime > 0.0F) {
            state.deathTime = 0.0F;
        }
    }

    @Override
    public Identifier getTextureLocation(WormsRenderState state) {
        float cycleLength = 60F / (state.ageInTicks - state.writhingStartTick < 50 ? 4 : 1);

        if (state.ageInTicks % cycleLength <= cycleLength / 2F) {
            return TEXTURE_LOCATION;
        } else {
            return ANIMATED_TEXTURE_LOCATION;
        }
    }

    @Override
    protected void setupRotations(WormsRenderState state, PoseStack poseStack, float bodyRot, float entityScale) {
        super.setupRotations(state, poseStack, bodyRot, entityScale);
        if (state.deathTime > 0.0F) {
            float fall = (state.deathTime - 1.0F) / 20.0F * 1.6F;
            fall = Mth.sqrt(fall);
            if (fall > 1.0F) { fall = 1.0F; }

            poseStack.rotateDegrees(Axis.ZP, -fall * this.getFlipDegrees());
        }
    }
}
