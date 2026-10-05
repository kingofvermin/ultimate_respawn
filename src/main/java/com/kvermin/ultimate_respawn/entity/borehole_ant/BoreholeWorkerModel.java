// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
package com.kvermin.ultimate_respawn.entity.borehole_ant;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.entity.beetle.BeetleAnimation;
import com.kvermin.ultimate_respawn.entity.borehole_ant.BoreholeWorker;
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
import net.minecraft.world.item.Items;

public class BoreholeWorkerModel<T extends Entity> extends EntityModel<BoreholeWorkerRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(UltimateRespawn.MODID, "boreholeworker"), "main");
	private final ModelPart head;
	private final ModelPart left_mandible;
	private final ModelPart right_mandible;
	private final ModelPart left_antenna;
	private final ModelPart right_antenna;
	private final ModelPart carrying;
	private final ModelPart abdomen;
	private final ModelPart thorax;
	private final ModelPart left_leg_1;
	private final ModelPart left_leg_2;
	private final ModelPart left_leg_3;
	private final ModelPart right_leg_1;
	private final ModelPart right_leg_2;
	private final ModelPart right_leg_3;

	private final KeyframeAnimation walkAnimation;
	private final KeyframeAnimation idleAnimation;

	public BoreholeWorkerModel(
			ModelPart root,
			AnimationDefinition walk,
			AnimationDefinition idle
	) {
        super(root);
		this.head = root.getChild("head");
		this.left_mandible = this.head.getChild("left_mandible");
		this.right_mandible = this.head.getChild("right_mandible");
		this.left_antenna = this.head.getChild("left_antenna");
		this.right_antenna = this.head.getChild("right_antenna");
		this.carrying = this.head.getChild("carrying");
		this.abdomen = root.getChild("abdomen");
		this.thorax = root.getChild("thorax");
		this.left_leg_1 = root.getChild("left_leg_front");
		this.left_leg_2 = root.getChild("left_leg_middle");
		this.left_leg_3 = root.getChild("left_leg_back");
		this.right_leg_1 = root.getChild("right_leg_front");
		this.right_leg_2 = root.getChild("right_leg_middle");
		this.right_leg_3 = root.getChild("right_leg_back");

		this.walkAnimation = walk.bake(root);
		this.idleAnimation = idle.bake(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 32).addBox(-3.5F, -3.0F, -4.0F, 7.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, -8.0F));

		PartDefinition left_mandible = head.addOrReplaceChild("left_mandible", CubeListBuilder.create().texOffs(22, 36).addBox(-3.0F, 0.0F, 2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 3.0F, -6.0F));

		PartDefinition right_mandible = head.addOrReplaceChild("right_mandible", CubeListBuilder.create().texOffs(22, 36).mirror().addBox(0.0F, 0.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.5F, 3.0F, -2.0F));

		PartDefinition left_antenna = head.addOrReplaceChild("left_antenna", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -0.5F, -10.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -1.5F, -4.0F));

		PartDefinition right_antenna = head.addOrReplaceChild("right_antenna", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -0.5F, -10.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -1.5F, -4.0F));

		PartDefinition carrying = head.addOrReplaceChild("carrying", CubeListBuilder.create().texOffs(0, 48).addBox(0.0F, 1.0F, -9.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 48).addBox(-2.0F, 3.0F, -6.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition abdomen = partdefinition.addOrReplaceChild("abdomen", CubeListBuilder.create().texOffs(0, 20).addBox(-3.0F, -2.5F, -3.5F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.5F, -4.5F));

		PartDefinition thorax = partdefinition.addOrReplaceChild("thorax", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 6).addBox(0.0F, 3.0F, 10.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, -1.0F));

		PartDefinition left_leg_front = partdefinition.addOrReplaceChild("left_leg_front", CubeListBuilder.create(), PartPose.offset(2.0F, 14.0F, -6.5F));

		PartDefinition cube_r1 = left_leg_front.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 0).addBox(0.0F, 0.0F, 0.0F, 9.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 0.0F, 1.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition left_leg_middle = partdefinition.addOrReplaceChild("left_leg_middle", CubeListBuilder.create().texOffs(28, 0).addBox(-2.0F, 0.0F, 0.0F, 9.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 14.0F, -4.0F));

		PartDefinition left_leg_back = partdefinition.addOrReplaceChild("left_leg_back", CubeListBuilder.create(), PartPose.offset(2.0F, 14.0F, -2.0F));

		PartDefinition cube_r2 = left_leg_back.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(26, 20).addBox(0.0F, 0.0F, 0.0F, 14.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 0.0F, -1.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition right_leg_front = partdefinition.addOrReplaceChild("right_leg_front", CubeListBuilder.create(), PartPose.offset(-2.0F, 14.0F, -6.5F));

		PartDefinition cube_r3 = right_leg_front.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(28, 0).mirror().addBox(-9.0F, 0.0F, 0.0F, 9.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.25F, 0.0F, 1.5F, 0.0F, -0.7854F, 0.0F));

		PartDefinition right_leg_middle = partdefinition.addOrReplaceChild("right_leg_middle", CubeListBuilder.create().texOffs(28, 0).mirror().addBox(-7.0F, 0.0F, 0.0F, 9.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 14.0F, -4.0F));

		PartDefinition right_leg_back = partdefinition.addOrReplaceChild("right_leg_back", CubeListBuilder.create(), PartPose.offset(-2.0F, 14.0F, -2.0F));

		PartDefinition cube_r4 = right_leg_back.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(26, 20).mirror().addBox(-14.0F, 0.0F, 0.0F, 14.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.25F, 0.0F, -1.0F, 0.0F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(BoreholeWorkerRenderState state) {
		float ageInTicks = state.ageInTicks;

		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.head.yRot = state.yRot * ((float) Math.PI / 180F);
		if (state.holdingValidItem) {
			this.head.xRot = (float) -Math.PI / 4;
			this.left_antenna.xRot = (float) Math.PI / 8;
			this.right_antenna.xRot = (float) Math.PI / 8;
		} else {
			this.head.xRot = state.xRot * ((float) Math.PI / 180F);
		}

		this.left_antenna.xRot += Math.sin(ageInTicks * 0.25F) * 0.33F;
		this.left_antenna.zRot += Math.cos(ageInTicks * 0.25F) * 0.25F;
		this.right_antenna.xRot += Math.cos(ageInTicks * 0.25F) * 0.33F;
		this.right_antenna.zRot += Math.sin(ageInTicks * 0.25F) * 0.25F;

		this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed * 8F, 2F, 1F);
		this.idleAnimation.apply(state.idleAnimationState, ageInTicks);
	}
}