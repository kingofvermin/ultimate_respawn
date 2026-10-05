package com.kvermin.ultimate_respawn.entity.borehole_ant;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.Nullable;

public class BoreholeWorker extends PathfinderMob {
    public final AnimationState idleAnimationState = new AnimationState();
    public static final EntityDataAccessor<Integer> ANIMATION_TICKS = SynchedEntityData.defineId(BoreholeWorker.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ANIMATION_ID = SynchedEntityData.defineId(BoreholeWorker.class, EntityDataSerializers.INT);

    public BoreholeWorker(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10D).add(Attributes.ATTACK_DAMAGE, 1.0D).add(Attributes.ATTACK_KNOCKBACK, 1.0D).add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.STEP_HEIGHT, 1.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, BoreholeWorker.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1D, false));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() { return SoundEvents.PARROT_AMBIENT; }
    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) { return SoundEvents.BASALT_BREAK; }
    @Override
    protected @Nullable SoundEvent getDeathSound() { return SoundEvents.PARROT_DEATH; }
    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        this.playSound(SoundEvents.ARMADILLO_STEP, 0.15F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        this.entityData.set(ANIMATION_TICKS, Math.max(this.entityData.get(ANIMATION_TICKS) - 1, 0));
        if (this.entityData.get(ANIMATION_TICKS) == 0) {
            this.entityData.set(ANIMATION_ID, -1);
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        } else if (!this.holdingValidItem() && this.tickCount % 600 == 0 && this.random.nextInt(2) == 0) {
            this.level().broadcastEntityEvent(this, (byte) 8);
        }
    }

    public boolean holdingValidItem() {
        return this.getMainHandItem().is(ItemTags.LEAVES);
    }

    private void setupAnimationStates() {
        if (this.entityData.get(ANIMATION_TICKS) > 0) {
            switch (this.entityData.get(ANIMATION_ID)) {
                case 0:
                    this.idleAnimationState.startIfStopped(this.tickCount);
                    break;
            }
        } else {
            this.idleAnimationState.stop();
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case (byte) 8: // Chitter animation
                this.entityData.set(ANIMATION_TICKS, 30);
                this.entityData.set(ANIMATION_ID, 0);
                break;
            default:
                super.handleEntityEvent(id);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(ANIMATION_TICKS, 0);
        entityData.define(ANIMATION_ID, -1);
    }
}
