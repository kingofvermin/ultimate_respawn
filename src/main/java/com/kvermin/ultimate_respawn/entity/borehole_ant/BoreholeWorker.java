package com.kvermin.ultimate_respawn.entity.borehole_ant;

import com.kvermin.ultimate_respawn.entity.beetle.Beetle;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class BoreholeWorker extends PathfinderMob {
    public final AnimationState idleAnimationState = new AnimationState();
    public static final EntityDataAccessor<Integer> LINE_ORDER = SynchedEntityData.defineId(BoreholeWorker.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ANIMATION_START = SynchedEntityData.defineId(BoreholeWorker.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ANIMATION_LENGTH = SynchedEntityData.defineId(BoreholeWorker.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ANIMATION_ID = SynchedEntityData.defineId(BoreholeWorker.class, EntityDataSerializers.INT);

    public static final int CHITTER_ANIMATION_ID = 1;

    public BoreholeWorker(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20D).add(Attributes.ATTACK_DAMAGE, 2.4D).add(Attributes.ATTACK_KNOCKBACK, 1.0D).add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.STEP_HEIGHT, 1.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new FindAndFollowLeaderGoal(this, 4 + random.nextInt(4)));
        this.goalSelector.addGoal(5, new LeadingStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, BoreholeWorker.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1D, false));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    }
    public static class LeadingStrollGoal extends RandomStrollGoal {
        private BoreholeWorker mob;

        public LeadingStrollGoal(BoreholeWorker mob, double speedModifier) {
            super(mob, speedModifier, 60);
            this.mob = mob;
        }
        @Override
        public boolean canUse() {
            return mob.getLineOrder() == 0 && super.canUse();
        }
        @Override
        protected @Nullable Vec3 getPosition() {
            return DefaultRandomPos.getPos(this.mob, 32, 7);
        }
    }
    public static class FindAndFollowLeaderGoal extends Goal {
        private int searchRange = 16;
        private int preferredDistance;
        private boolean isLeader;
        private BoreholeWorker mob;
        private BoreholeWorker leader;
        private List<BoreholeWorker> nearbyAllies;

        public FindAndFollowLeaderGoal(BoreholeWorker mob, int preferredDistance) {
            this.mob = mob;
            this.preferredDistance = preferredDistance;
            this.isLeader = false;
        }
        @Override
        public boolean canUse() {
            this.findNearbyAllies();
            return this.mob.getTarget() == null && !this.nearbyAllies.isEmpty() && !this.isLeader;
        }
        @Override
        public void tick() {
            super.tick();
            this.findNearbyAllies();
            if (this.nearbyAllies.stream().anyMatch(ally -> ally.getLineOrder() == this.mob.getLineOrder()) || (this.mob.getLineOrder() != 0 && !this.nearbyAllies.stream().anyMatch(ally -> ally.getLineOrder() == this.mob.getLineOrder() - 1))) {
                this.mob.setLineOrder(this.nearbyAllies.stream().sorted(Comparator.comparingInt(ally -> -ally.getLineOrder())).findFirst().get().getLineOrder() + 1);
            } else if (this.nearbyAllies.stream().anyMatch(ally -> ally.getLineOrder() == this.mob.getLineOrder() - 1)) {
                this.leader = this.nearbyAllies.stream().filter(ally -> ally.getLineOrder() == this.mob.getLineOrder() - 1).findFirst().get();
                if (this.mob.distanceToSqr(this.leader) > this.preferredDistance) {
                    this.mob.navigation.moveTo(this.leader, 1);
                } if (this.mob.navigation.getTargetPos() != null && this.leader.distanceToSqr(Vec3.atLowerCornerOf(this.mob.navigation.getTargetPos())) > this.preferredDistance) {
                    this.mob.navigation.stop();
                }
            }
            if (this.mob.getLineOrder() == 0) {
                this.isLeader = true;
            }
        }

        private void findNearbyAllies() {
            this.nearbyAllies = ((ServerLevel) this.mob.level()).getNearbyEntities(BoreholeWorker.class, TargetingConditions.forNonCombat().range(this.searchRange).ignoreLineOfSight(), this.mob, this.mob.getBoundingBox().inflate(searchRange, searchRange, searchRange));
        }
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

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        } else if (!this.holdingValidItem() && this.tickCount % 600 == 0 && this.random.nextInt(2) == 0) {
            this.setAnimationState(CHITTER_ANIMATION_ID);
        }
    }

    public boolean holdingValidItem() {
        return this.getMainHandItem().is(ItemTags.LEAVES);
    }

    private void setupAnimationStates() {
        if (this.tickCount - this.entityData.get(ANIMATION_START) < this.entityData.get(ANIMATION_LENGTH)) {
            switch (this.entityData.get(ANIMATION_ID)) {
                case CHITTER_ANIMATION_ID:
                    this.idleAnimationState.startIfStopped(this.tickCount);
                    break;
            }
        } else {
            this.idleAnimationState.stop();
        }
    }

    private void setAnimationState(int id) {
        this.entityData.set(ANIMATION_START, this.tickCount);
        if (id == CHITTER_ANIMATION_ID) {
            this.entityData.set(ANIMATION_LENGTH, 30);
            this.entityData.set(ANIMATION_ID, CHITTER_ANIMATION_ID);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(LINE_ORDER, 0);
        entityData.define(ANIMATION_START, this.tickCount);
        entityData.define(ANIMATION_LENGTH, 0);
        entityData.define(ANIMATION_ID, 0);
    }

    public int getLineOrder() {
        return entityData.get(LINE_ORDER);
    }
    public void setLineOrder(int value) {
        entityData.set(LINE_ORDER, value);
    }
}
