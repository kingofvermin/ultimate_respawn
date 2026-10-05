package com.kvermin.ultimate_respawn.entity.orchauroch;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.entity.borehole_ant.BoreholeWorker;
import com.kvermin.ultimate_respawn.entity.borehole_ant.BoreholeWorkerRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

public class AppleCowRenderer extends MobRenderer<AppleCow, AppleCowRenderState, AppleCowModel<AppleCow>> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "textures/entity/orchauroch/orchauroch.png");

    public AppleCowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AppleCowModel<>(pContext.bakeLayer(AppleCowModel.LAYER_LOCATION), AppleCowAnimation.ORCHAUROCH_SHAKE), 1.5f);
        this.addLayer(new AppleCowGrowthLayer(this));
    }

    @Override
    public AppleCowRenderState createRenderState() {
        return new AppleCowRenderState();
    }

    public static void extractAdditionalState(AppleCow entity, AppleCowRenderState state, float partialTicks) {
        state.growthStage = entity.getEntityData().get(AppleCow.GROWTH_STAGE);
        state.shakeAnimationState.copyFrom(entity.shakeAnimationState);
    }

    @Override
    public Identifier getTextureLocation(AppleCowRenderState appleCowRenderState) {
        return TEXTURE_LOCATION;
    }

    public class AppleCowGrowthLayer extends RenderLayer<AppleCowRenderState, AppleCowModel<AppleCow>> {
        private static final Identifier GROWTH_STAGE_1_TEXTURE = Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "textures/entity/orchauroch/growth_stage_1.png");
        private static final Identifier GROWTH_STAGE_2_TEXTURE = Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "textures/entity/orchauroch/growth_stage_2.png");
        private static final Identifier GROWTH_STAGE_3_TEXTURE = Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "textures/entity/orchauroch/growth_stage_3.png");

        public AppleCowGrowthLayer(RenderLayerParent<AppleCowRenderState, AppleCowModel<AppleCow>> pRenderer) {
            super(pRenderer);
        }
        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, AppleCowRenderState state, float v, float v1) {
            Identifier texture = switch (state.growthStage) {
                case 1 -> GROWTH_STAGE_1_TEXTURE;
                case 2 -> GROWTH_STAGE_2_TEXTURE;
                case 3 -> GROWTH_STAGE_3_TEXTURE;
                default -> null;
            };
            if (texture != null) {
                renderColoredCutoutModel(this.getParentModel(), texture, poseStack, submitNodeCollector, lightCoords, state, 1, 1);
            }
        }
    }
}
