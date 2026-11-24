package com.kvermin.ultimate_respawn.entity.util;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;

public interface AnimatedMob {
    // public static final EntityDataAccessor<Integer> ANIMATION_START = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.INT);
    // public static final EntityDataAccessor<Integer> ANIMATION_LENGTH = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.INT);
    // public static final EntityDataAccessor<Integer> ANIMATION_ID = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.INT);
    //
    // public final Animation firstAnimation = new Animation(new AnimationState(), 1, 20);
    // public final Animation secondAnimation = new Animation(new AnimationState(), 2, 20);
    // public final Animation thirdAnimation = new Animation(new AnimationState(), 3, 20);

    void setupAnimationStates();
    // Follows the model:
    // if (this.animationPlaying()) {
    //     if (this.getAnimationId() == firstAnimation.id()) {
    //         this.firstAnimation.animationState().startIfStopped(this.tickCount);
    //         this.secondAnimation.animationState().stop();
    //         this.thirdAnimation.animationState().stop();
    //     } else if (this.getAnimationId() == secondAnimation.id()) {
    //         this.secondAnimation.animationState().startIfStopped(this.tickCount);
    //         this.firstAnimation.animationState().stop();
    //         this.thirdAnimation.animationState().stop();
    //     } else if (this.getAnimationId() == thirdAnimation.id()) {
    //         this.thirdAnimation.animationState().startIfStopped(this.tickCount);
    //         this.firstAnimation.animationState().stop();
    //         this.secondAnimation.animationState().stop();
    //     }
    // } else {
    //     this.firstAnimation.animationState().stop();
    //     this.secondAnimation.animationState().stop();
    //     this.thirdAnimation.animationState().stop();
    // }

    void setAnimationState(Animation animation);
    // Follows the model:
    // this.entityData.set(ANIMATION_START, this.tickCount);
    // this.entityData.set(ANIMATION_LENGTH, animation.length());
    // this.entityData.set(ANIMATION_ID, animation.id());

    default int getAnimationTimeElapsed() { return getTickCount() - getAnimationStart(); }
    default boolean animationPlaying() { return getAnimationTimeElapsed() < getAnimationLength(); }
    default boolean isIdle() { return animationPlaying(); }

    SynchedEntityData getEntityData();
    int getTickCount();
    int getAnimationId();
    int getAnimationLength();
    int getAnimationStart();

    record Animation(AnimationState animationState, int id, int length) {}

    // @Override
    // public int getTickCount() { return this.tickCount; }
    // @Override
    // public int getAnimationId() { return this.entityData.get(ANIMATION_ID); }
    // @Override
    // public int getAnimationLength() { return this.entityData.get(ANIMATION_LENGTH); }
    // @Override
    // public int getAnimationStart() { return this.entityData.get(ANIMATION_START); }

    // default void defineSynchedData(SynchedEntityData.Builder entityData) {
    //     entityData.define(ANIMATION_START, getTickCount());
    //     entityData.define(ANIMATION_LENGTH, 0);
    //     entityData.define(ANIMATION_ID, 0);
    // }
}


// OTHER CLASSES

// In EntityAnimation.java:
// public static final AnimationDefinition ENTITY_FIRST_ANIMATION_NAME
// public static final AnimationDefinition ENTITY_SECOND_ANIMATION_NAME
// public static final AnimationDefinition ENTITY_THIRD_ANIMATION_NAME

// In EntityRenderState.java:
// public final AnimationState firstAnimationState;
// public final AnimationState secondAnimationState;
// public final AnimationState thirdAnimationState;
//
// public EntityRenderState() {
//     ...
//     this.firstAnimationState = new AnimationState();
//     this.secondAnimationState = new AnimationState();
//     this.thirdAnimationState = new AnimationState();
// }

// In EntityRenderer.java:
// public EntityRenderer(EntityRendererProvider.Context context) {
//     super(context, new EntityModel(
//             context.bakeLayer(EntityModel.LAYER_LOCATION),
//             EntityAnimation.ENTITY_FIRST_ANIMATION_NAME,
//             EntityAnimation.ENTITY_SECOND_ANIMATION_NAME,
//             EntityAnimation.ENTITY_THIRD_ANIMATION_NAME
//     ), ... );
// }
//
// public void extractRenderState(Entity entity, EntityRenderState state, float partialTicks) {
//     super.extractRenderState(entity, state, partialTicks);
//     ...
//     state.firstAnimationState.copyFrom(entity.firstAnimation.animationState());
//     state.secondAnimationState.copyFrom(entity.secondAnimation.animationState());
//     state.thirdAnimationState.copyFrom(entity.thirdAnimation.animationState());
// }

// In EntityModel.java:
// private final KeyframeAnimation firstAnimation;
// private final KeyframeAnimation secondAnimation;
// private final KeyframeAnimation thirdAnimation;
//
// public EntityModel(
//     ModelPart root,
//     AnimationDefinition firstAnimationName,
//     AnimationDefinition secondAnimationName,
//     AnimationDefinition thirdAnimationName
// ) {
//     ...
//     this.firstAnimation = firstAnimationName.bake(root);
//     this.secondAnimation = secondAnimationName.bake(root);
//     this.thirdAnimation = thirdAnimationName.bake(root);
// }
//
// public void setupAnim(EntityRenderState state) {
//     this.root().getAllParts().forEach(ModelPart::resetPose);
//     this.firstAnimation.apply(state.firstAnimationState, state.ageInTicks);
//     this.secondAnimation.apply(state.secondAnimationState, state.ageInTicks);
//     this.thirdAnimation.apply(state.thirdAnimationState, state.ageInTicks);