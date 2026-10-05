// Made with Blockbench 5.2.0
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
package com.kvermin.ultimate_respawn.entity.beetle;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

public class BeetleModel<T extends Entity> extends EntityModel<BeetleRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "beetle"), "main");
	private final ModelPart abdomen;
	private final ModelPart head;
	private final ModelPart left_antenna;
	private final ModelPart right_antenna;
	private final ModelPart left_elytra;
	private final ModelPart left_wing;
	private final ModelPart right_elytra;
	private final ModelPart right_wing;
	private final ModelPart left_leg_front;
	private final ModelPart left_leg_middle;
	private final ModelPart left_leg_back;
	private final ModelPart right_leg_front;
	private final ModelPart right_leg_middle;
	private final ModelPart right_leg_back;
	private final KeyframeAnimation walkAnimation;
	private final KeyframeAnimation attackAnimation;
	private final KeyframeAnimation takeoffAnimation;
	private final KeyframeAnimation flyAnimation;
	private final KeyframeAnimation landAnimation;

	public BeetleModel(
			ModelPart root,
			AnimationDefinition walk,
			AnimationDefinition attack,
			AnimationDefinition takeoff,
			AnimationDefinition fly,
			AnimationDefinition land
	) {
		super(root);
		this.abdomen = root.getChild("abdomen");
		this.head = this.abdomen.getChild("head");
		this.left_antenna = this.head.getChild("left_antenna");
		this.right_antenna = this.head.getChild("right_antenna");
		this.left_elytra = this.abdomen.getChild("left_elytra");
		this.left_wing = this.abdomen.getChild("left_wing");
		this.right_elytra = this.abdomen.getChild("right_elytra");
		this.right_wing = this.abdomen.getChild("right_wing");
		this.left_leg_front = root.getChild("left_leg_front");
		this.left_leg_middle = root.getChild("left_leg_middle");
		this.left_leg_back = root.getChild("left_leg_back");
		this.right_leg_front = root.getChild("right_leg_front");
		this.right_leg_middle = root.getChild("right_leg_middle");
		this.right_leg_back = root.getChild("right_leg_back");

		this.walkAnimation = walk.bake(root);
		this.attackAnimation = attack.bake(root);
		this.takeoffAnimation = takeoff.bake(root);
		this.flyAnimation = fly.bake(root);
		this.landAnimation = land.bake(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition abdomen = partdefinition.addOrReplaceChild("abdomen", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -18.0F, -19.6655F, 22.0F, 22.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(-5, 40).addBox(-4.0F, -18.0F, -1.6655F, 8.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 45).addBox(-2.5F, -22.0F, -19.6655F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 67).addBox(-10.0F, -16.0F, -1.6655F, 20.0F, 11.0F, 17.0F, new CubeDeformation(0.0F))
				.texOffs(0, 40).addBox(-11.0F, -5.0F, -1.6655F, 22.0F, 9.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 2.6655F));

		PartDefinition head = abdomen.addOrReplaceChild("head", CubeListBuilder.create().texOffs(80, 50).addBox(-5.0F, -4.0F, -8.0F, 10.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -19.6655F));

		PartDefinition left_antenna = head.addOrReplaceChild("left_antenna", CubeListBuilder.create().texOffs(116, 53).addBox(0.0F, -6.5F, -5.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -0.5F, -8.0F));

		PartDefinition right_antenna = head.addOrReplaceChild("right_antenna", CubeListBuilder.create().texOffs(116, 53).addBox(0.0F, -6.5F, -5.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -0.5F, -8.0F));

		PartDefinition left_elytra = abdomen.addOrReplaceChild("left_elytra", CubeListBuilder.create().texOffs(80, 0).addBox(-11.0F, 0.0F, 0.0F, 11.0F, 13.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, -18.0F, -1.6655F));

		PartDefinition left_wing = abdomen.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(113, 6).addBox(-8.5F, 0.0F, 0.0F, 10.0F, 0.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(8.5F, -16.25F, -8.6655F));

		PartDefinition right_elytra = abdomen.addOrReplaceChild("right_elytra", CubeListBuilder.create().texOffs(80, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 11.0F, 13.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-11.0F, -18.0F, -1.6655F));

		PartDefinition right_wing = abdomen.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(113, 6).mirror().addBox(-1.5F, 0.0F, 0.0F, 10.0F, 0.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.5F, -16.25F, -8.6655F));

		PartDefinition left_leg_front = partdefinition.addOrReplaceChild("left_leg_front", CubeListBuilder.create().texOffs(102, 37).addBox(-1.5F, 0.0F, -10.337F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(128, 41).addBox(-1.5F, 3.0F, -10.337F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(134, 46).addBox(-1.5F, 10.0F, -14.337F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5F, 14.0F, -5.8345F, 0.0F, -0.7854F, 0.0F));

		PartDefinition left_leg_middle = partdefinition.addOrReplaceChild("left_leg_middle", CubeListBuilder.create().texOffs(102, 37).addBox(-1.5F, 0.0F, -9.9988F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(128, 41).addBox(-1.5F, 3.0F, -9.9988F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(134, 46).addBox(-1.5F, 10.0F, -13.9988F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5F, 14.0F, -4.0012F, 0.0F, -1.5708F, 0.0F));

		PartDefinition left_leg_back = partdefinition.addOrReplaceChild("left_leg_back", CubeListBuilder.create().texOffs(64, 31).addBox(-1.5F, 0.0F, -0.1655F, 3.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.2039F, 14.0F, -3.3778F, 0.0F, 0.3927F, 0.0F));

		PartDefinition cube_r1 = left_leg_back.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(134, 46).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 17.8345F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r2 = left_leg_back.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(128, 41).addBox(-1.5F, -3.5F, -1.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.5F, 14.8345F, 0.0F, 3.1416F, 0.0F));

		PartDefinition right_leg_back = partdefinition.addOrReplaceChild("right_leg_back", CubeListBuilder.create().texOffs(64, 31).mirror().addBox(-1.5F, 0.0F, -0.1655F, 3.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.2039F, 14.0F, -3.3778F, 0.0F, -0.3927F, 0.0F));

		PartDefinition cube_r3 = right_leg_back.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(134, 46).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 10.0F, 17.8345F, 0.0F, -3.1416F, 0.0F));

		PartDefinition cube_r4 = right_leg_back.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(128, 41).mirror().addBox(-1.5F, -3.5F, -1.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.5F, 14.8345F, 0.0F, -3.1416F, 0.0F));

		PartDefinition right_leg_middle = partdefinition.addOrReplaceChild("right_leg_middle", CubeListBuilder.create().texOffs(102, 37).mirror().addBox(-1.5F, 0.0F, -9.9988F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(128, 41).mirror().addBox(-1.5F, 3.0F, -9.9988F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(134, 46).mirror().addBox(-1.5F, 10.0F, -13.9988F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.5F, 14.0F, -4.0012F, 0.0F, 1.5708F, 0.0F));

		PartDefinition right_leg_front = partdefinition.addOrReplaceChild("right_leg_front", CubeListBuilder.create().texOffs(102, 37).mirror().addBox(-1.5F, 0.0F, -10.337F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(128, 41).mirror().addBox(-1.5F, 3.0F, -10.337F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(134, 46).mirror().addBox(-1.5F, 10.0F, -14.337F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.5F, 14.0F, -5.8345F, 0.0F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(BeetleRenderState state) {
		float ageInTicks = state.ageInTicks;

		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.head.xRot = state.xRot * ((float) Math.PI / 180F);
		this.head.yRot = state.yRot * ((float) Math.PI / 180F);
		this.left_antenna.xRot += Math.sin(ageInTicks * 0.25F) * 0.33F;
		this.left_antenna.zRot += Math.cos(ageInTicks * 0.25F) * 0.25F;
		this.right_antenna.xRot += Math.cos(ageInTicks * 0.25F) * 0.33F;
		this.right_antenna.zRot += Math.sin(ageInTicks * 0.25F) * 0.25F;

		this.attackAnimation.apply(state.attackAnimationState, ageInTicks);
		this.takeoffAnimation.apply(state.takeoffAnimationState, ageInTicks);
		this.flyAnimation.apply(state.flyAnimationState, ageInTicks);
		this.landAnimation.apply(state.landAnimationState, ageInTicks);
		if (state.isProne) {
			this.root().zRot = (float) Math.PI;
			this.root().y = 16F;

			this.root.zRot += Math.sin(ageInTicks * 0.25F) * 0.125F;

			this.left_leg_front.zRot += Math.sin(ageInTicks * 0.5F) * 0.35F;
			this.left_leg_middle.zRot += Math.sin(ageInTicks * 0.5F + 1) * 0.35F;
			this.left_leg_back.zRot += Math.sin(ageInTicks * 0.5F + 2) * 0.35F;
			this.right_leg_front.zRot += Math.sin(ageInTicks * 0.5F + 3) * 0.35F;
			this.right_leg_middle.zRot += Math.sin(ageInTicks * 0.5F + 4) * 0.35F;
			this.right_leg_back.zRot += Math.sin(ageInTicks * 0.5F + 5) * 0.35F;
		} else if (state.isFlying) {
			this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed * 2F, 2F, 1F);
		} else {
			this.root.xRot += Math.sin(ageInTicks * 0.125F) * 0.1F;
			this.root.zRot += Math.sin(ageInTicks * 0.125F) * 0.1F;

			this.left_leg_front.xRot += Math.sin(ageInTicks * 0.125F) * 0.2F + 0.2F;
			this.left_leg_middle.xRot += Math.sin(ageInTicks * 0.125F + 1) * 0.2F + 0.2F;
			this.left_leg_back.xRot += Math.sin(ageInTicks * 0.125F + 2) * 0.2F + 0.2F;
			this.right_leg_front.xRot += Math.sin(ageInTicks * 0.125F + 3) * 0.2F + 0.2F;
			this.right_leg_middle.xRot += Math.sin(ageInTicks * 0.125F + 4) * 0.2F + 0.2F;
			this.right_leg_back.xRot += Math.sin(ageInTicks * 0.125F + 5) * 0.2F + 0.2F;
		}
	}
}