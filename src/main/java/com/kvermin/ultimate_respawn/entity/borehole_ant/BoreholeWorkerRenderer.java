package com.kvermin.ultimate_respawn.entity.borehole_ant;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.entity.beetle.Beetle;
import com.kvermin.ultimate_respawn.entity.beetle.BeetleRenderState;
import com.kvermin.ultimate_respawn.entity.orchauroch.AppleCow;
import com.kvermin.ultimate_respawn.entity.orchauroch.AppleCowModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;

public class BoreholeWorkerRenderer extends MobRenderer<BoreholeWorker, BoreholeWorkerRenderState, BoreholeWorkerModel<BoreholeWorker>> {
    private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "textures/entity/borehole_ant/worker.png");

    public BoreholeWorkerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BoreholeWorkerModel<BoreholeWorker>(pContext.bakeLayer(BoreholeWorkerModel.LAYER_LOCATION), BoreholeWorkerAnimation.BOREHOLE_ANT_WALK, BoreholeWorkerAnimation.BOREHOLE_ANT_CHITTER), 1.5f);
        this.shadowRadius = 1F;
    }

    @Override
    public BoreholeWorkerRenderState createRenderState() {
        return new BoreholeWorkerRenderState();
    }

    public static void extractAdditionalState(BoreholeWorker entity, BoreholeWorkerRenderState state, float partialTicks) {
        state.holdingItem = entity.getMainHandItem();
        state.holdingValidItem = entity.holdingValidItem();
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
    }

    @Override
    public Identifier getTextureLocation(BoreholeWorkerRenderState boreholeWorkerRenderState) {
        return TEXTURE_LOCATION;
    }

    public class CarriedItemLayer extends RenderLayer<BoreholeWorkerRenderState, BoreholeWorkerModel<BoreholeWorker>> {
        private static final Identifier CARRIED_LEAF_TEXTURE = Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "textures/entity/borehole_ant/item/leaf.png");

        public CarriedItemLayer(RenderLayerParent<BoreholeWorkerRenderState, BoreholeWorkerModel<BoreholeWorker>> pRenderer) { super(pRenderer); }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, BoreholeWorkerRenderState state, float yRot, float xRot) {
            Identifier texture = null;
            if (state.holdingItem.is(ItemTags.LEAVES)) {
                texture = CARRIED_LEAF_TEXTURE;
            }

            if (texture != null) {
                renderColoredCutoutModel(this.getParentModel(), texture, poseStack, submitNodeCollector, lightCoords, state, 1, 1);
            }
        }
    }
}