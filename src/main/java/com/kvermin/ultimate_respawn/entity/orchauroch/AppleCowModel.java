package com.kvermin.ultimate_respawn.entity.orchauroch;

// Made with Blockbench 5.2.0
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.entity.beetle.BeetleAnimation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.definitions.CamelAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class AppleCowModel<T extends Entity> extends EntityModel<AppleCowRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "orchauroch"), "main");
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart left_front_leg;
    private final ModelPart left_hind_leg;
    private final ModelPart right_front_leg;
    private final ModelPart right_hind_leg;
    private final ModelPart tree;
    private final KeyframeAnimation shakeAnimation;

    public AppleCowModel(
            ModelPart root,
            AnimationDefinition shake
    ) {
        super(root);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.left_front_leg = root.getChild("left_front_leg");
        this.left_hind_leg = root.getChild("left_hind_leg");
        this.right_front_leg = root.getChild("right_front_leg");
        this.right_hind_leg = root.getChild("right_hind_leg");
        this.tree = root.getChild("tree");

        this.shakeAnimation = shake.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 76).addBox(-4.0F, -3.7F, -6.05F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 90).addBox(-3.5F, 0.3F, -7.05F, 7.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 76).addBox(4.0F, -2.7F, -4.05F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 77).addBox(8.0F, -4.7F, -4.05F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(28, 82).mirror().addBox(4.0F, 0.3F, -4.05F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(22, 76).mirror().addBox(-10.0F, -2.7F, -4.05F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(40, 77).mirror().addBox(-10.0F, -4.7F, -4.05F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(28, 82).addBox(-10.0F, 0.3F, -4.05F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(47, 82).addBox(4.0F, 0.3F, -2.55F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(47, 82).mirror().addBox(-7.0F, 0.3F, -2.55F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 7.7F, -12.95F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -17.5F, -12.25F, 14.0F, 26.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(68, 0).addBox(-6.0F, -7.5F, 7.75F, 12.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 46).addBox(-7.0F, -17.5F, -12.25F, 14.0F, 10.0F, 20.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 5.5F, -0.75F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(120, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, 13.75F));

        PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(68, 22).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 10.0F, -7.0F));

        PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(104, 0).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 9.0F, 10.5F));

        PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(68, 22).mirror().addBox(-3.0F, -2.0F, -3.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.5F, 10.0F, -7.0F));

        PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(104, 0).mirror().addBox(-2.0F, -5.0F, -2.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, 9.0F, 10.5F));

        PartDefinition tree = partdefinition.addOrReplaceChild("tree", CubeListBuilder.create().texOffs(92, 4).addBox(0.0F, -16.0F, -10.5F, 0.0F, 16.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(92, 40).addBox(-7.0F, -16.0F, 0.5F, 14.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, -2.5F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(AppleCowRenderState state) {
        float ageInTicks = state.ageInTicks;
        float limbSwing = state.walkAnimationPos;
        float limbSwingAmount = state.walkAnimationSpeed;

        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = state.xRot * ((float) Math.PI / 180F);
        this.head.yRot = state.yRot * ((float) Math.PI / 180F);

        this.tail.xRot = 0.08F + Mth.sin(ageInTicks * 0.125F) * 0.04F;

        this.right_hind_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.left_hind_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.right_front_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.left_front_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.body.zRot += Mth.cos(limbSwing * 0.5F) * 0.25F * limbSwingAmount;

        this.shakeAnimation.apply(state.shakeAnimationState, ageInTicks);

        this.tree.zRot = this.body.zRot;
        this.tree.x = (float) 17.5F * Mth.sin(body.zRot);
        this.tree.y = (float) 5.5 - 17.5F * Mth.cos(body.zRot);
    }
}