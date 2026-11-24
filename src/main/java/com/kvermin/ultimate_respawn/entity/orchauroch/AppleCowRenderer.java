package com.kvermin.ultimate_respawn.entity.orchauroch;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.UvMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.NeoForgeRenderTypes;

public class AppleCowRenderer extends MobRenderer<AppleCow, AppleCowRenderState, AppleCowModel<AppleCow>> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "textures/entity/orchauroch/orchauroch.png");

    public AppleCowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AppleCowModel<>(pContext.bakeLayer(AppleCowModel.LAYER_LOCATION), AppleCowAnimation.ORCHAUROCH_SHAKE), 1.25f);
        this.addLayer(new AppleCowGrowthLayer(this));
        this.addLayer(new AppleCowTreeLayer(this));
    }

    @Override
    public AppleCowRenderState createRenderState() {
        return new AppleCowRenderState();
    }

    public void extractRenderState(AppleCow entity, AppleCowRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.growthStage = entity.getEntityData().get(AppleCow.GROWTH_STAGE);
        state.shakeAnimationState.copyFrom(entity.shakeAnimationState);
    }

    @Override
    public Identifier getTextureLocation(AppleCowRenderState appleCowRenderState) {
        return TEXTURE_LOCATION;
    }

    public class AppleCowTreeLayer extends RenderLayer<AppleCowRenderState, AppleCowModel<AppleCow>> {
        public AppleCowTreeLayer(RenderLayerParent<AppleCowRenderState, AppleCowModel<AppleCow>> pRenderer) {
            super(pRenderer);
        }
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, AppleCowRenderState state, float yRot, float xRot) {
            submitNodeCollector.order(0).submitModel(model, state, poseStack, NeoForgeRenderTypes.getEntityUnlitTranslucent(Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "textures/entity/orchauroch/tree.png")), lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F), -1, (UvMapping)null, state.outlineColor);
        }
    }

    public class AppleCowGrowthLayer extends RenderLayer<AppleCowRenderState, AppleCowModel<AppleCow>> {
        private static final Identifier GROWTH_STAGE_1_TEXTURE = Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "textures/entity/orchauroch/growth_stage_1.png");
        private static final Identifier GROWTH_STAGE_2_TEXTURE = Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "textures/entity/orchauroch/growth_stage_2.png");
        private static final Identifier GROWTH_STAGE_3_TEXTURE = Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "textures/entity/orchauroch/growth_stage_3.png");

        public AppleCowGrowthLayer(RenderLayerParent<AppleCowRenderState, AppleCowModel<AppleCow>> pRenderer) {
            super(pRenderer);
        }
        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, AppleCowRenderState state, float yRot, float xRot) {
            Identifier texture = switch (state.growthStage) {
                case 1 -> GROWTH_STAGE_1_TEXTURE;
                case 2 -> GROWTH_STAGE_2_TEXTURE;
                case 3 -> GROWTH_STAGE_3_TEXTURE;
                default -> null;
            };
            if (texture != null) {
                submitNodeCollector.order(1).submitModel(model, state, poseStack, NeoForgeRenderTypes.getEntityUnlitTranslucent(texture), lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F), -1, (UvMapping)null, state.outlineColor);
            }
        }
    }
}
