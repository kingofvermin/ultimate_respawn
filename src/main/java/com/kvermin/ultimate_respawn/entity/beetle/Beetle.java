package com.kvermin.ultimate_respawn.entity.beetle;

import com.kvermin.ultimate_respawn.entity.FlyableMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Beetle extends FlyableMob {
    private int exhaustion;
    private int attackCooldown;
    private boolean slamAttack;
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState takeoffAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState landAnimationState = new AnimationState();
    public static final EntityDataAccessor<Integer> ANIMATION_TICKS = SynchedEntityData.defineId(Beetle.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ANIMATION_ID = SynchedEntityData.defineId(Beetle.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> PRONE = SynchedEntityData.defineId(Beetle.class, EntityDataSerializers.BOOLEAN);

    public Beetle(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        this.setPathfindingMalus(PathType.LEAVES, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 50D).add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.ATTACK_KNOCKBACK, 1.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.25D).add(Attributes.MOVEMENT_SPEED, 0.23D).add(Attributes.FLYING_SPEED, 0.46D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new ClampHeightGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollOrFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.goalSelector.addGoal(2, new TakeOffGoal(this));
        this.goalSelector.addGoal(2, new LandGoal(this));
        this.goalSelector.addGoal(3, new SlamAttackGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.25D, false));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }
    public static class TakeOffGoal extends Goal {
        protected final Beetle mob;

        public TakeOffGoal(Beetle beetle) {
            this.mob = beetle;
        }
        @Override
        public boolean canUse() {
            return !mob.isFlying() && mob.exhaustion <= 100 && mob.getTarget() != null && mob.getTarget().distanceToSqr(mob) >= 100;
        }
        public void start() {
            this.mob.setFlying(true);
            this.mob.level().broadcastEntityEvent(this.mob, (byte) 8);
            this.mob.setExhaustion(0);
        }
    }
    public static class SlamAttackGoal extends Goal {
        protected final Beetle mob;

        public SlamAttackGoal(Beetle beetle) { this.mob = beetle; }
        @Override
        public boolean canUse() {
            return (mob.isFlying() || mob.getSlamAttack()) && mob.getTarget() != null;
        }

        @Override
        public void start() {
            this.mob.getNavigation().moveTo(mob.getTarget().getX(), mob.getTarget().getY() + 5F, mob.getTarget().getZ(), 1.0F);
        }

        public void spawnSlamEffects() {
            this.mob.playSound(SoundEvents.ANVIL_LAND);
            ((ServerLevel) this.mob.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, this.mob.level().getBlockState(FlyableMob.getBlockBelow(this.mob))), this.mob.getX() - 0.5, this.mob.getY(), this.mob.getZ() - 0.5, 200, 2, 0, 2, 1);
            this.mob.setSlamAttack(false);
            this.mob.setAttackCooldown(80);
        }

        @Override
        public void tick() {
            super.tick();

            LivingEntity target = mob.getTarget();
            int hoverHeight = 4;

            if (!mob.getSlamAttack()) {
                if (this.mob.getNavigation().getTargetPos() != null && !this.mob.getNavigation().getTargetPos().equals(target.position().add(0,hoverHeight,0))) {
                    this.mob.getNavigation().moveTo(target.getX(), target.getY() + hoverHeight, target.getZ(), 1.0F);
                }
                if ((this.mob.distanceToSqr(target.position().add(0,hoverHeight,0)) < 6) && (target.getY() + hoverHeight - this.mob.getY() < 0.5)) {
                    this.mob.getNavigation().stop();
                    this.mob.setFlying(false);
                    this.mob.level().broadcastEntityEvent(this.mob, (byte) 56);
                    this.mob.setSlamAttack(true);
                }
            } else {
                if (this.mob.distanceToSqr(target) < 5) {
                    if (target.isBlocking()) {
                        this.mob.entityData.set(PRONE, true);
                        this.mob.level().broadcastEntityEvent(this.mob, (byte) 39);
                    } else {
                        this.mob.doHurtTarget(target);
                    }
                    this.spawnSlamEffects();
                    this.stop();
                } else if (this.mob.onGround()) {
                    this.spawnSlamEffects();
                    this.stop();
                }
            }
        }

        @Override
        public void stop() {
            this.mob.navigation.stop();
            super.stop();
        }
    }
    public static class LandGoal extends Goal {
        protected final Beetle mob;

        public LandGoal(Beetle pMob) { this.mob = pMob; }

        @Override
        public boolean canUse() {
            return mob.isFlying() && ((mob.getTarget() == null && mob.getExhaustion() > 300) || mob.getExhaustion() > 600);
        }

        @Override
        public void tick() {
            super.tick();
            if (this.mob.getY() - getBlockBelow(this.mob).getY() < 2) {
                this.mob.getNavigation().stop();
                this.mob.setFlying(false);
                this.mob.level().broadcastEntityEvent(this.mob, (byte) 56);
            } else if (this.mob.getNavigation().isDone()) {
                BlockPos destination = getBlockBelow(this.mob);
                this.mob.getNavigation().moveTo(destination.getX(), destination.getY(), destination.getZ(), 1);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.entityData.set(ANIMATION_TICKS, Math.max(this.entityData.get(ANIMATION_TICKS) - 1, 0));
        if (this.entityData.get(ANIMATION_TICKS) == 0) {
            this.entityData.set(ANIMATION_ID, -1);
        }

        this.attackCooldown -= this.attackCooldown > 0 ? 1 : 0;

        if (this.isFlying()) {
            this.exhaustion++;
        } else {
            this.exhaustion -= this.exhaustion > 0 ? 1 : 0;
        }
        if (this.entityData.get(PRONE)) {
            this.removeAllGoals(
                    goal -> !(goal instanceof RandomLookAroundGoal || goal instanceof LookAtPlayerGoal)
            );
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.isFlying() && this.entityData.get(ANIMATION_TICKS) == 0) {
            this.attackAnimationState.stop();
            this.takeoffAnimationState.stop();
            this.landAnimationState.stop();
            this.flyAnimationState.startIfStopped(this.tickCount);
        } else if (this.entityData.get(ANIMATION_TICKS) > 0) {
            switch (this.entityData.get(ANIMATION_ID)) {
                case 0:
                    this.attackAnimationState.stop();
                    this.takeoffAnimationState.stop();
                    this.flyAnimationState.stop();
                    this.landAnimationState.stop();
                    this.attackAnimationState.startIfStopped(this.tickCount);
                    break;
                case 1:
                    this.attackAnimationState.stop();
                    this.flyAnimationState.stop();
                    this.landAnimationState.stop();
                    this.takeoffAnimationState.startIfStopped(this.tickCount);
                    break;
                case 2:
                    this.attackAnimationState.stop();
                    this.takeoffAnimationState.stop();
                    this.flyAnimationState.stop();
                    this.landAnimationState.startIfStopped(this.tickCount);
                    break;
            }
        } else {
            this.attackAnimationState.stop();
            this.takeoffAnimationState.stop();
            this.flyAnimationState.stop();
            this.landAnimationState.stop();
        }
    }

    public boolean doHurtTarget(Entity pEntity) {
        if (this.attackCooldown == 0 && pEntity instanceof LivingEntity && !this.isFlying()) {
            this.attackCooldown = 20;
            this.level().broadcastEntityEvent(this, (byte) 4);
            return hurtAndThrowTarget((ServerLevel) this.level(), this, (LivingEntity) pEntity);
        }
        return false;
    }

    static boolean hurtAndThrowTarget(ServerLevel level, Beetle beetle, LivingEntity target) {
        float attackDamage = (float) beetle.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float actualDamage;
        if ((int)attackDamage > 0) {
            actualDamage = attackDamage / 2.0F + (float) level.getRandom().nextInt((int)attackDamage);
        } else {
            actualDamage = attackDamage;
        }

        DamageSource damageSource = beetle.damageSources().mobAttack(beetle);
        boolean wasHurt = target.hurtServer(level, damageSource, actualDamage);
        if (wasHurt) {
            EnchantmentHelper.doPostAttackEffects(level, target, damageSource);
            throwTarget(beetle, target);
        }

        return wasHurt;
    }

    static void throwTarget(Beetle beetle, LivingEntity target) {
        double knockbackPower = beetle.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        double knockbackResistance = target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
        double effectiveKnockbackPower = knockbackPower - knockbackResistance;
        if (!(effectiveKnockbackPower <= (double) 0.0F)) {
            double xd = target.getX() - beetle.getX();
            double zd = target.getZ() - beetle.getZ();
            RandomSource random = beetle.level().getRandom();
            float horizontalPushAngle = (float) (random.nextInt(21) - 10);
            double horizontalScale = effectiveKnockbackPower * (double)(random.nextFloat() * 0.5F + 0.2F);
            Vec3 horizontalPushVector = (new Vec3(xd, (double) 0.0F, zd)).normalize().scale(horizontalScale).yRot(horizontalPushAngle);
            double verticalScale = effectiveKnockbackPower * (double) random.nextFloat() * (double)0.5F;
            target.push(horizontalPushVector.x, verticalScale, horizontalPushVector.z);
            target.syncVelocity = true;
        }
    }

    @Override
    public float getSecondsToDisableBlocking() {
        if (this.isTryingToSlamAttack() && this.getTarget().isBlocking()) {
            return 2.0F;
        }
        return super.getSecondsToDisableBlocking();
    }

    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case (byte) 4: // Attack animation
                this.entityData.set(ANIMATION_TICKS, 10);
                this.entityData.set(ANIMATION_ID, 0);
                break;
            case (byte) 8: // Takeoff animation
                this.entityData.set(ANIMATION_TICKS, 10);
                this.entityData.set(ANIMATION_ID, 1);
                break;
            case (byte) 39: // Falling prone
                this.entityData.set(PRONE, true);
                break;
            case (byte) 56: // Landing animation
                this.entityData.set(ANIMATION_TICKS, 10);
                this.entityData.set(ANIMATION_ID, 2);
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
        entityData.define(PRONE, false);
    }
    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("Prone", this.entityData.get(PRONE));
    }
    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.entityData.set(PRONE, input.getBooleanOr("Prone", false));
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.STRIDER_AMBIENT;
    }
    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.BASALT_BREAK;
    }
    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.STRIDER_DEATH;
    }
    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        this.playSound(SoundEvents.RAVAGER_STEP, 0.15F, 1.0F);
    }

    @Override
    public int minHeight() {
        return 1;
    }
    @Override
    public int maxHeight() {
        if (this.isTryingToSlamAttack()) {
            return 5;
        } else {
            return 2;
        }
    }

    public int getExhaustion() {
        return this.exhaustion;
    }
    public void setExhaustion(int value) {
        this.exhaustion = value;
    }
    public int getAttackCooldown() {
        return this.attackCooldown;
    }
    public void setAttackCooldown(int value) {
        this.attackCooldown = value;
    }
    public boolean getSlamAttack() {
        return this.slamAttack;
    }
    public void setSlamAttack(boolean value) {
        this.slamAttack = value;
    }
    boolean isTryingToSlamAttack() {
        return this.goalSelector.getAvailableGoals().stream().filter(wrappedGoal -> wrappedGoal.isRunning()).anyMatch(wrappedGoal -> wrappedGoal.getGoal() instanceof SlamAttackGoal);
    }
}
