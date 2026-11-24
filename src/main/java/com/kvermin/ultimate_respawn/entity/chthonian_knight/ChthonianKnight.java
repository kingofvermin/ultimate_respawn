package com.kvermin.ultimate_respawn.entity.chthonian_knight;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.entity.util.AnimatedMob;
import com.kvermin.ultimate_respawn.entity.worms.Worms;
import com.kvermin.ultimate_respawn.item.URItems;
import com.kvermin.ultimate_respawn.particle.URParticleTypes;
import com.kvermin.ultimate_respawn.sound.URSounds;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.kvermin.ultimate_respawn.entity.util.URMobUtilities.getBlockBelow;

public class ChthonianKnight extends Monster implements AnimatedMob {
    private Vec3 displacement;
    private Vec3 displacementOld;

    private int attackCooldown = 0;
    private boolean isRiposte = false;

    public static final int WARP_LENGTH = 20;
    private int warpStart = -WARP_LENGTH * 2;

    public static final int HELMET_HEALTH = 3;
    private static final EntityDataAccessor<Integer> HELMET_DAMAGE = SynchedEntityData.defineId(ChthonianKnight.class, EntityDataSerializers.INT);

    private final Set<UUID> damagedEntities = new HashSet<>();

    public static final EntityDataAccessor<Integer> ANIMATION_START = SynchedEntityData.defineId(ChthonianKnight.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ANIMATION_LENGTH = SynchedEntityData.defineId(ChthonianKnight.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ANIMATION_ID = SynchedEntityData.defineId(ChthonianKnight.class, EntityDataSerializers.INT);

    public final Animation defaultAnimation = new Animation(new AnimationState(), 0, 0);
    public final Animation readySwingAnimation = new Animation(new AnimationState(), 1, 10);
    public final Animation swingAnimation = new Animation(new AnimationState(), 2, 15);
    public final Animation blockAnimation = new Animation(new AnimationState(), 3, 5);
    public final Animation riposteAnimation = new Animation(new AnimationState(), 4, 20);
    public final Animation lowerGuardAnimation = new Animation(new AnimationState(), 5, 10);

    public ChthonianKnight(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.navigation = new FlyingPathNavigation(this, level());
        this.setNoGravity(true);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new KnightAttackGoal(this));
        this.goalSelector.addGoal(3, new WarpGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    public static class KnightAttackGoal extends MeleeAttackGoal {
        private ChthonianKnight mob;
        private int attackId;
        private boolean attackSelected = false;

        private int SWING_ATTACK_ID = 1;
        private int RIPOSTE_ATTACK_ID = 2;

        public KnightAttackGoal(ChthonianKnight mob) {
            super(mob, 6.0D, false);
            this.mob = mob;
        }
        @Override
        public boolean canUse() {
            this.pickAttack();
            return super.canUse() ;
        }

        private void pickAttack() {
            if (this.mob.getHealth() <= this.mob.getMaxHealth() * 0.75 && this.mob.random.nextFloat() <= 0.25) {
                this.attackId = RIPOSTE_ATTACK_ID;
            } else {
                this.attackId = SWING_ATTACK_ID;
            }
            this.attackSelected = true;
        }
        @Override
        protected boolean canPerformAttack(LivingEntity target) {
            return this.mob.attackCooldown == 0 && this.isTimeToAttack() && this.mob.distanceToSqr(target) <= 32 && this.mob.getSensing().hasLineOfSight(target);
        }
        @Override
        protected void checkAndPerformAttack(LivingEntity target) {
            if (!this.attackSelected && this.mob.attackCooldown > 0) {
                this.pickAttack();
            }
            if (this.attackId == SWING_ATTACK_ID && this.canPerformAttack(target)) {
                if (this.mob.isIdle()) {
                    this.mob.setAnimationState(this.mob.readySwingAnimation);
                    this.attackSelected = false;
                }
            } else if (this.attackId == RIPOSTE_ATTACK_ID && this.canPerformAttack(target) && this.mob.isIdle()) {
                this.mob.setAnimationState(this.mob.blockAnimation);
                this.attackSelected = false;
            }
        }

        private static final AttributeModifier BLOCK_SLOWDOWN = new AttributeModifier(
                Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "block_slowdown"),
                -0.65D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        @Override
        public void tick() {
            super.tick();
            if (this.mob.getAnimationId() == this.mob.blockAnimation.id()) { this.mob.getAttribute(Attributes.FLYING_SPEED).addOrUpdateTransientModifier(BLOCK_SLOWDOWN); }
            else { this.mob.getAttribute(Attributes.FLYING_SPEED).removeModifier(Identifier.fromNamespaceAndPath(UltimateRespawn.MOD_ID, "block_slowdown")); }
        }
    }
    public static class WarpGoal extends Goal {
        private ChthonianKnight mob;
        private boolean warp;

        public WarpGoal(ChthonianKnight mob) {
            this.mob = mob;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            return this.mob.getWarpTimeElapsed() > WARP_LENGTH * 2 + 300 && (this.mob.isIdle() || this.mob.getAnimationId() == this.mob.blockAnimation.id()) && target != null && this.mob.distanceToSqr(target) > 144;
        }

        @Override
        public void tick() {
            super.tick();
            if (this.mob.getWarpTimeElapsed() > WARP_LENGTH) {
                this.mob.setWarpStart(this.mob.tickCount);
                this.mob.setAnimationState(this.mob.blockAnimation);
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.FLYING_SPEED, 0.12D).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ENTITY_INTERACTION_RANGE, 10.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.5D).add(Attributes.MAX_HEALTH, 100D).add(Attributes.FOLLOW_RANGE, 35.0D);
    }
    @Override
    protected float getWaterSlowDown() { return 0.9F; }
    @Override
    public boolean canBreatheUnderwater() { return true; }

    @Override
    public void tick() {
        super.tick();
        this.displacementOld = this.displacement;
        this.displacement = this.position().subtract(this.oldPosition());
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            MoveControl moveControl = this.getMoveControl();
            BlockPos blockBelow = getBlockBelow(this);
            if (this.getY() - blockBelow.getY() > 1.5 && (this.getTarget() == null || this.getTarget().getY() < this.getY())) {
                if (this.getDeltaMovement().y > -0.04)
                    this.setDeltaMovement(this.getDeltaMovement().add(0D, -0.02D, 0D));
            } else if (this.getY() - blockBelow.getY() < 1) {
                moveControl.setWantedPosition(this.getX(), blockBelow.getY() + 1, this.getZ(), 1);
            }

            this.setDeltaMovement(this.getDeltaMovement().with(Direction.Axis.Y, Math.min(0.02F, this.getDeltaMovement().y())));

            if (this.getAnimationId() == this.swingAnimation.id() && this.getAnimationTimeElapsed() <= 8) {
                Set<Class> allies = Set.of(ChthonianKnight.class, Worms.class);
                List<Entity> inRange = this.level().getEntities(this, this.getBoundingBox().inflate(0.75F), entity -> { return entity instanceof LivingEntity && !allies.contains(entity.getClass()) && !this.damagedEntities.contains(entity.getUUID()); });
                inRange.listIterator().forEachRemaining(entity -> {
                    this.doHurtTarget((ServerLevel) this.level(), entity);
                    this.damagedEntities.add(entity.getUUID());
                });
            }

            if (this.getAnimationId() == this.readySwingAnimation.id() && this.getAnimationTimeElapsed() > 40) {
                if (this.getTarget() != null && this.isWithinMeleeAttackRange(this.getTarget())) {
                    this.doHurtTarget((ServerLevel) this.level(), this.getTarget());
                } else {
                    this.performSwing();
                }
            } else if (this.getAnimationId() == this.blockAnimation.id() && this.getAnimationTimeElapsed() > 80) {
                this.setAnimationState(this.lowerGuardAnimation);
                this.isRiposte = false;
                this.attackCooldown = 40;
            }
            this.attackCooldown -= this.attackCooldown > 0 ? 1 : 0;

            if (this.getWarpTimeElapsed() == 0) {
                this.level().playSound((Player) null, this.blockPosition(), URSounds.ELDRITCH_STEP.value(), SoundSource.HOSTILE, 0.5F, 1F);
                this.level().playSound((Player) null, this.getTarget().blockPosition(), URSounds.ELDRITCH_STEP.value(), SoundSource.HOSTILE, 0.5F, 1F);
                ((ServerLevel) this.level()).sendParticles(URParticleTypes.ELDRITCH_CLOUD.get(), this.getX(), this.getY() + 1, this.getZ(), 1, 0, 0, 0, 0);
                this.doWarpEffects(10);
            } else if (this.getWarpTimeElapsed() == WARP_LENGTH) {
                if (this.getTarget() != null) {
                    warpTowards(this.getTarget());
                } else { this.discard(); }

                this.setAnimationState(this.lowerGuardAnimation);
                ((ServerLevel) this.level()).sendParticles(URParticleTypes.ELDRITCH_CLOUD.get(), this.getX(), this.getY() + 1, this.getZ(), 1, 0, 0, 0, 0);
                this.doWarpEffects();
            } else if (this.getWarpTimeElapsed() < WARP_LENGTH || this.getWarpTimeElapsed() < WARP_LENGTH * 2) {
                this.doWarpEffects(10);
            }
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (this.getAnimationId() == this.readySwingAnimation.id() && !this.animationPlaying()) {
            this.performSwing();
            return super.doHurtTarget(level, target);
        } else if (this.getAnimationId() == this.swingAnimation.id()) {
            return super.doHurtTarget(level, target);
        } else if (this.isRiposte) {
            this.performRiposte();
            return super.doHurtTarget(level, target);
        }
        return false;
    }

    private void performSwing() {
        if (this.getTarget() != null) { this.lookAt(EntityAnchorArgument.Anchor.EYES, this.getTarget().getEyePosition()); }
        this.moveRelative((float) (this.getAttributeValue(Attributes.FLYING_SPEED) * 6F), new Vec3(0, 0, 5));
        this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1F, 0.5F);
        this.setAnimationState(this.swingAnimation);
        this.damagedEntities.clear();
        this.attackCooldown = 2;
    }
    private void performRiposte() {
        this.moveRelative((float) (this.getAttributeValue(Attributes.FLYING_SPEED) * 6F), new Vec3(0, 0, 5));
        ((ServerLevel) this.level()).sendParticles(ParticleTypes.CRIT, this.getX(), this.getY() + 1.9, this.getZ(), 5, 0.25, 0.25, 0.25, 0);
        this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1F, 1F);
        this.setAnimationState(this.riposteAnimation);
        this.attackCooldown = 80;
        this.isRiposte = false;
    }

    private void doWarpEffects() {
        this.doWarpEffects(5);
    }
    private void doWarpEffects(int particleCount) {
        this.setDeltaMovement(0, 0, 0);
        ((ServerLevel) this.level()).sendParticles(URParticleTypes.ELDRITCH_TENDRIL.get(), this.getX(), this.getY() + 1, this.getZ(), particleCount, 0, 0, 0, 0);
    }

    private void warpTowards(Entity entity) {
        Vec3 pos = entity.position().add(entity.getLookAngle().normalize().scale(4));
        this.teleportTo(pos.x, pos.y, pos.z);
    }

    @Override
    public boolean onGround() {
        return false;
    }
    @Override
    public boolean causeFallDamage(double fallDistance, float damageModifier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        if (this.getAnimationId() == this.blockAnimation.id() && !this.animationPlaying() && this.getWarpTimeElapsed() > WARP_LENGTH) {
            this.level().playSound((Player) null, this, SoundEvents.BELL_BLOCK, SoundSource.HOSTILE, 1F, 2F);
            if (source.getEntity() != null && source.getEntity() instanceof LivingEntity) {
                this.isRiposte = true;
                this.doHurtTarget((ServerLevel) this.level(), source.getEntity());
            } else {
                this.performRiposte();
            }
            return false;
        }
        return super.hurtServer(level, source, damage);
    }

    @Override
    public void onDamageTaken(DamageContainer damageContainer) {
        super.onDamageTaken(damageContainer);
        if (damageContainer.getSource().getWeaponItem() == null) { return; }
        Weapon weaponData = damageContainer.getSource().getWeaponItem().get(DataComponents.WEAPON);
        if (weaponData != null && weaponData.disableBlockingForSeconds() > 0) {
            this.entityData.set(HELMET_DAMAGE, this.getHelmetDamage() + 1);
            if (this.getHelmetDamage() == HELMET_HEALTH) {
                this.getAttribute(Attributes.ARMOR).setBaseValue(0);
                ((ServerLevel) this.level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, URItems.ORICHALCUM_SCRAP.get()), this.getX(), this.getY() + 2.3, this.getZ(), 20, 0.25, 0.25, 0.25, 0);
                this.level().playSound((Player) null, this, SoundEvents.SHIELD_BREAK, SoundSource.HOSTILE, 1.5F, 1F);
            } else if (this.hasHelmet()) {
                ((ServerLevel) this.level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, URItems.ORICHALCUM_SCRAP.get()), this.getX(), this.getY() + 2.3, this.getZ(), 2, 0.25, 0.25, 0.25, 0);
                this.level().playSound((Player) null, this, SoundEvents.SHIELD_BREAK, SoundSource.HOSTILE, 1F, 1.25F);
            }
        }
        if (!this.hasHelmet()) {
            ((ServerLevel) this.level()).sendParticles(URParticleTypes.WORMS.get(), this.getX(), this.getY() + 2.3, this.getZ(), 2, 0.25, 0.25, 0.25, 0);
            ((ServerLevel) this.level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, URItems.CRUOR_BALL.get()), this.getX(), this.getY() + 0.5, this.getZ(), 2, 0.5, 0.25, 0.5, 0);
        }
    }

    public void setupAnimationStates() {
        if (this.animationPlaying()) {
            if (this.getAnimationId() == this.readySwingAnimation.id()) {
                this.readySwingAnimation.animationState().startIfStopped(this.tickCount);
                this.defaultAnimation.animationState().stop();
                this.swingAnimation.animationState().stop();
                this.blockAnimation.animationState().stop();
                this.riposteAnimation.animationState().stop();
                this.lowerGuardAnimation.animationState().stop();
            } else if (this.getAnimationId() == this.swingAnimation.id()) {
                this.swingAnimation.animationState().startIfStopped(this.tickCount);
                this.defaultAnimation.animationState().stop();
                this.readySwingAnimation.animationState().stop();
                this.blockAnimation.animationState().stop();
                this.riposteAnimation.animationState().stop();
                this.lowerGuardAnimation.animationState().stop();
            } else if (this.getAnimationId() == this.blockAnimation.id()) {
                this.blockAnimation.animationState().startIfStopped(this.tickCount);
                this.defaultAnimation.animationState().stop();
                this.readySwingAnimation.animationState().stop();
                this.swingAnimation.animationState().stop();
                this.riposteAnimation.animationState().stop();
                this.lowerGuardAnimation.animationState().stop();
            } else if (this.getAnimationId() == this.riposteAnimation.id()) {
                this.riposteAnimation.animationState().startIfStopped(this.tickCount);
                this.defaultAnimation.animationState().stop();
                this.readySwingAnimation.animationState().stop();
                this.swingAnimation.animationState().stop();
                this.blockAnimation.animationState().stop();
                this.lowerGuardAnimation.animationState().stop();
            } else if (this.getAnimationId() == this.lowerGuardAnimation.id()) {
                this.lowerGuardAnimation.animationState().startIfStopped(this.tickCount);
                this.defaultAnimation.animationState().stop();
                this.readySwingAnimation.animationState().stop();
                this.swingAnimation.animationState().stop();
                this.blockAnimation.animationState().stop();
                this.riposteAnimation.animationState().stop();
            }
        } else {
            this.swingAnimation.animationState().stop();
            this.riposteAnimation.animationState().stop();
            this.lowerGuardAnimation.animationState().stop();

            if (this.isIdle()) {
                this.defaultAnimation.animationState().startIfStopped(this.tickCount);
            } else {
                this.defaultAnimation.animationState().stop();
            }
        }
    }

    public void setAnimationState(Animation animation) {
        this.entityData.set(ANIMATION_START, this.tickCount);
        this.entityData.set(ANIMATION_LENGTH, animation.length());
        this.entityData.set(ANIMATION_ID, animation.id());
    }
    @Override
    public int getTickCount() { return this.tickCount; }
    @Override
    public int getAnimationId() {
        return this.entityData.get(ANIMATION_ID);
    }
    @Override
    public int getAnimationLength() { return this.entityData.get(ANIMATION_LENGTH); }
    @Override
    public int getAnimationStart() { return this.entityData.get(ANIMATION_START); }
    public boolean isIdle() {
        return !(animationPlaying() && (this.getAnimationId() == this.swingAnimation.id() || this.getAnimationId() == this.riposteAnimation.id() || this.getAnimationId() == this.lowerGuardAnimation.id())) && !(this.getAnimationId() == this.readySwingAnimation.id() || this.getAnimationId() == this.blockAnimation.id());
    }

    public Vec3 getDisplacement() { return this.displacement; }
    public Vec3 getDisplacementOld() { return this.displacementOld; }

    public int getAttackCooldown() { return this.attackCooldown; }
    public int getHelmetDamage() { return entityData.get(HELMET_DAMAGE); }
    public boolean hasHelmet() { return this.getHelmetDamage() < ChthonianKnight.HELMET_HEALTH; }
    public void setWarpStart(int tick) { this.warpStart = tick; }
    public int getWarpTimeElapsed() { return this.tickCount - this.warpStart; }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
        SpawnGroupData toReturn = super.finalizeSpawn(level, difficulty, spawnReason, groupData);
        this.setLeftHanded(false);
        this.setItemSlot(EquipmentSlot.MAINHAND, URItems.ORICHALCUM_GREATSWORD.get().getDefaultInstance());
        this.getAttribute(Attributes.ARMOR).setBaseValue(8);
        return toReturn;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(HELMET_DAMAGE, 0);
        entityData.define(ANIMATION_START, this.tickCount);
        entityData.define(ANIMATION_LENGTH, 0);
        entityData.define(ANIMATION_ID, 0);
    }
    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("HelmetDamage", this.entityData.get(HELMET_DAMAGE));
    }
    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.entityData.set(HELMET_DAMAGE, input.getIntOr("HelmetDamage", 0));
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) { return this.hasHelmet() ? SoundEvents.COPPER_GOLEM_OXIDIZED_HURT : SoundEvents.SULFUR_CUBE_HURT; }
    @Override
    protected @Nullable SoundEvent getDeathSound() { return this.hasHelmet() ? SoundEvents.COPPER_GOLEM_OXIDIZED_DEATH : SoundEvents.SULFUR_CUBE_SMALL_DEATH; }
}
