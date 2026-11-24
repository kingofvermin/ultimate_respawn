package com.kvermin.ultimate_respawn.entity.worms;
// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.kvermin.ultimate_respawn.UltimateRespawn;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class WormsModel<T extends Entity> extends EntityModel<WormsRenderState> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "worms"), "main");
    private final ModelPart mass;
    private final ModelPart worms;

    public WormsModel(ModelPart root) {
        super(root);
        this.mass = root.getChild("mass");
        this.worms = this.mass.getChild("worms");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition mass = partdefinition.addOrReplaceChild("mass", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -8.0F, -7.0F, 14.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition worms = mass.addOrReplaceChild("worms", CubeListBuilder.create().texOffs(0, 22).addBox(-10.0F, -9.0F, -5.0F, 20.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-10.0F, -9.0F, 5.0F, 20.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = worms.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 49).addBox(-10.0F, -9.0F, -1.0F, 20.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = worms.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 40).addBox(-10.0F, -9.0F, -1.0F, 20.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(WormsRenderState state) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float ageInTicks = state.ageInTicks;
        float writhingTime = ageInTicks - state.writhingStartTick;
        float intensity;
        float speed;

        if (writhingTime < 40) {
            intensity = 3F;
            speed = writhingTime * 0.5F;
        } else if (writhingTime > 60) {
            intensity = 1F;
            speed = (writhingTime - 60F) * 0.25F + 27.5F;
        } else {
            float t = writhingTime - 40F;
            intensity = 3F - (t / 20F);
            speed = -0.00625F * t * t + 0.5F * t + 20.0F;
        }


        mass.xScale = 1F + Mth.sin(speed) * 0.05F * intensity;
        mass.zScale = 1F + Mth.cos(speed) * 0.05F * intensity;
        mass.yScale = 1F - Mth.sin(speed) * 0.05F * intensity;
    }
}