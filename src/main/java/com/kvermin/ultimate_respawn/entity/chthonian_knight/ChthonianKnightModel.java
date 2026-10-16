package com.kvermin.ultimate_respawn.entity.chthonian_knight;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.effects.SpearAnimations;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;

public class ChthonianKnightModel extends HumanoidModel<ChthonianKnightRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "chthonian_knight"), "main");
    private final ModelPart body;
    private final ModelPart chest_worms;
    private final ModelPart loincloth;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart helmet;
    private final ModelPart head_mass;
    private final ModelPart head_worms;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart left_wing;
    private final ModelPart right_wing;

    private final KeyframeAnimation readySwingAnimation;
    private final KeyframeAnimation swingAnimation;

    public ChthonianKnightModel(
            ModelPart root,
            AnimationDefinition readySwing,
            AnimationDefinition swing
    ) {
        super(root);
        this.body = root.getChild("body");
        this.chest_worms = this.body.getChild("chest_worms");
        this.loincloth = this.body.getChild("loincloth");
        this.head = root.getChild("head");
        this.hat = this.head.getChild("hat");
        this.helmet = this.head.getChild("helmet");
        this.head_mass = this.head.getChild("head_mass");
        this.head_worms = this.head_mass.getChild("head_worms");
        this.left_arm = root.getChild("left_arm");
        this.right_arm = root.getChild("right_arm");
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
        this.left_wing = root.getChild("left_wing");
        this.right_wing = root.getChild("right_wing");

        this.readySwingAnimation = readySwing.bake(root);
        this.swingAnimation = swing.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 34).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 34).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(50, 8).addBox(-2.0F, 7.0F, 2.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition chest_worms = body.addOrReplaceChild("chest_worms", CubeListBuilder.create().texOffs(32, 8).addBox(-3.0F, 4.5F, -1.5F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition loincloth = body.addOrReplaceChild("loincloth", CubeListBuilder.create().texOffs(24, 40).addBox(-4.0F, 12.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 16).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(10, 26).addBox(-2.5F, -6.5F, -5.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 23).addBox(-6.5F, -0.5F, -2.5F, 13.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = helmet.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(19, 26).addBox(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.75F, -4.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r2 = helmet.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(1, 26).addBox(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.75F, -4.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition head_mass = head.addOrReplaceChild("head_mass", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head_worms = head_mass.addOrReplaceChild("head_worms", CubeListBuilder.create().texOffs(50, 4).addBox(-8.0F, -12.0F, 0.0F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 45).addBox(-1.0F, -2.0F, -1.5F, 2.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -2.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(-1.0F, -2.0F, -1.5F, 2.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -2.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(10, 45).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 8.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(10, 45).mirror().addBox(-1.0F, 0.0F, -1.5F, 2.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 8.0F, 0.0F));

        PartDefinition left_wing = partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(20, 48).addBox(-1.0F, -1.0F, 0.0F, 8.0F, 26.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -3.0F, 2.25F));

        PartDefinition right_wing = partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(20, 48).mirror().addBox(-7.0F, -1.0F, 0.0F, 8.0F, 26.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, -3.0F, 2.25F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void translateToHand(HumanoidRenderState state, HumanoidArm arm, PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        (arm == HumanoidArm.LEFT ? this.leftArm : this.rightArm).translateAndRotate(poseStack);
        poseStack.translate(arm == HumanoidArm.LEFT ? -0.0625F : 0.0625F, 0.25F, 0.0F);
    }

    @Override
    public void setupAnim(ChthonianKnightRenderState state) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float ageInTicks = state.ageInTicks;

        this.readySwingAnimation.apply(state.readySwingAnimationState, state.ageInTicks);
        this.swingAnimation.apply(state.swingAnimationState, state.ageInTicks);
        if (state.readySwingAnimationState.isStarted() && state.animationTimeElapsed > 10) {
            this.right_arm.zRot += Mth.sin(ageInTicks  * 0.5F) * Math.min(0.125F, Mth.lerp(Math.max(0, state.animationTimeElapsed - 10F) / 40F, 0.0F, 0.125F));
        }
        if (!(state.readySwingAnimationState.isStarted() || state.swingAnimationState.isStarted())) {
            setupIdlePose(state);
        }
        float drift = Mth.sin(ageInTicks * 0.125F);
        float driftOffset = Mth.cos(ageInTicks * 0.125F);
        this.root.y -= driftOffset;
        this.left_wing.xRot += 0.025F*drift + 0.025F;
        this.left_wing.zRot -= 0.0125F*drift;
        this.right_wing.zRot += 0.0125F*drift;
        this.left_arm.xRot = -0.025F*driftOffset;
        this.right_arm.xRot = 0.025F*driftOffset;
        this.left_leg.xRot += 0.05F*driftOffset;
        this.left_leg.y += 0.5F*drift;
        this.right_leg.xRot -= 0.05F*driftOffset;

        if (state.hasHelmet) {
            this.head.yRot = this.body.yRot;

            this.chest_worms.xScale = 1F + Mth.sin(ageInTicks * 0.25F) * 0.05F;
            this.chest_worms.zScale = 1F + Mth.cos(ageInTicks * 0.25F) * 0.05F;
            this.chest_worms.yScale = 1F - Mth.sin(ageInTicks * 0.25F) * 0.025F;
        } else {
            this.head_mass.xRot = state.xRot * ((float) Math.PI / 180F);
            this.head_mass.yRot = state.yRot * ((float) Math.PI / 180F);

            this.head_mass.xScale = this.chest_worms.xScale = 1F + Mth.sin(ageInTicks * 0.5F) * 0.15F;
            this.head_mass.zScale = this.chest_worms.zScale = 1F + Mth.cos(ageInTicks * 0.5F) * 0.15F;
            this.head_mass.yScale = 1F - Mth.sin(ageInTicks * 0.5F) * 0.15F;
            this.chest_worms.yScale = 1F - Mth.sin(ageInTicks * 0.5F) * 0.075F;
        }
    }

    public void setupIdlePose(ChthonianKnightRenderState state) {
        float ageInTicks = state.ageInTicks;

        float toRadians = Mth.PI / 180F;
        this.root.y -= 1.5F;
        this.body.yRot = 12.5F * toRadians;
        this.left_wing.xRot = this.right_wing.xRot = 5F * toRadians;
        this.left_wing.zRot = -1.25F * toRadians;
        this.right_wing.zRot = 1.25F * toRadians;
        this.right_arm.z += 0.5F;
        this.left_leg.xRot = 22.5F * toRadians;
        this.left_leg.y -= 1F;
        this.left_leg.z += -3F;
        this.right_leg.xRot = 12.5F * toRadians;
        this.right_leg.z += -1F;
    }
}
