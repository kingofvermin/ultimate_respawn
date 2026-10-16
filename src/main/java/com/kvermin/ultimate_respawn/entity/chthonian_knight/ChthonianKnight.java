package com.kvermin.ultimate_respawn.entity.chthonian_knight;

import com.kvermin.ultimate_respawn.entity.util.FlyableMob;
import com.kvermin.ultimate_respawn.item.URItems;
import com.kvermin.ultimate_respawn.particle.URParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
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
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jspecify.annotations.Nullable;

import static com.kvermin.ultimate_respawn.entity.util.URMobUtilities.getBlockBelow;

public class ChthonianKnight extends Monster {
    public static final int HELMET_HEALTH = 3;
    private static final EntityDataAccessor<Integer> HELMET_DAMAGE = SynchedEntityData.defineId(ChthonianKnight.class, EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> ANIMATION_START = SynchedEntityData.defineId(ChthonianKnight.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ANIMATION_LENGTH = SynchedEntityData.defineId(ChthonianKnight.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ANIMATION_ID = SynchedEntityData.defineId(ChthonianKnight.class, EntityDataSerializers.INT);

    public final AnimationState readySwingAnimationState = new AnimationState();
    public static final int READY_SWING_ANIMATION_ID = 1;
    public final AnimationState swingAnimationState = new AnimationState();
    public static final int SWING_ANIMATION_ID = 2;

    public ChthonianKnight(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.navigation = new FlyingPathNavigation(this, level());
        this.setNoGravity(true);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 3.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.FLYING_SPEED, 0.12D).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ENTITY_INTERACTION_RANGE, 10.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.5D).add(Attributes.MAX_HEALTH, 100D).add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        MoveControl moveControl = this.getMoveControl();
        BlockPos blockBelow = getBlockBelow(this);
        if (this.getY() - blockBelow.getY() > 1.5) {
            double existingMomentum = this.getDeltaMovement().y;

            if (existingMomentum > -0.04) this.setDeltaMovement(this.getDeltaMovement().add(0D, -0.02D, 0D));
        } else if (this.getY() - blockBelow.getY() < 1) {
            moveControl.setWantedPosition(this.getX(), blockBelow.getY() + 1, this.getZ(), 1);
        }

        this.setYya(Math.min(0.02F, this.yya));
        this.setDeltaMovement(this.getDeltaMovement().with(Direction.Axis.Y, Math.min(0.02F, this.getDeltaMovement().y())));

        if (this.getAnimationId() == READY_SWING_ANIMATION_ID && this.getAnimationTimeElapsed() > 90) {
            if (this.getTarget() != null && this.isWithinMeleeAttackRange(this.getTarget())) this.doHurtTarget((ServerLevel) this.level(), this.getTarget());
            else this.performSwing();
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (this.getAnimationId() == READY_SWING_ANIMATION_ID && !this.animationPlaying()) {
            this.performSwing();
            return super.doHurtTarget(level, target);
        } else {
            this.setAnimationState(READY_SWING_ANIMATION_ID);
            return false;
        }
    }

    private void performSwing() {
        this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1F, 0.5F);
        this.setAnimationState(SWING_ANIMATION_ID);
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageModifier, DamageSource damageSource) {
        return false;
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

    private void setupAnimationStates() {
        if (this.animationPlaying()) {
            switch (this.entityData.get(ANIMATION_ID)) {
                case READY_SWING_ANIMATION_ID:
                    this.readySwingAnimationState.startIfStopped(this.tickCount);
                    this.swingAnimationState.stop();
                    break;
                case SWING_ANIMATION_ID:
                    this.swingAnimationState.startIfStopped(this.tickCount);
                    this.readySwingAnimationState.stop();
                    break;
            }
        } else {
            this.swingAnimationState.stop();
        }
    }

    private void setAnimationState(int id) {
        this.entityData.set(ANIMATION_START, this.tickCount);
        if (id == READY_SWING_ANIMATION_ID) {
            this.entityData.set(ANIMATION_LENGTH, 10);
            this.entityData.set(ANIMATION_ID, READY_SWING_ANIMATION_ID);
        } else if (id == SWING_ANIMATION_ID) {
            this.entityData.set(ANIMATION_LENGTH, 15);
            this.entityData.set(ANIMATION_ID, SWING_ANIMATION_ID);
        }
    }
    public int getAnimationId() {
        return this.entityData.get(ANIMATION_ID);
    }
    public int getAnimationTimeElapsed() {
        return this.tickCount - this.entityData.get(ANIMATION_START);
    }
    public boolean animationPlaying() {
        return getAnimationTimeElapsed() < this.entityData.get(ANIMATION_LENGTH);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
        this.setLeftHanded(false);
        this.setItemSlot(EquipmentSlot.MAINHAND, URItems.ORICHALCUM_GREATSWORD.get().getDefaultInstance());
        this.getAttribute(Attributes.ARMOR).setBaseValue(8);
        return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
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

    public int getHelmetDamage() { return entityData.get(HELMET_DAMAGE); }
    public boolean hasHelmet() { return this.getHelmetDamage() < ChthonianKnight.HELMET_HEALTH; }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) { return this.hasHelmet() ? SoundEvents.COPPER_GOLEM_OXIDIZED_HURT : SoundEvents.SULFUR_CUBE_HURT; }
    @Override
    protected @Nullable SoundEvent getDeathSound() { return this.hasHelmet() ? SoundEvents.COPPER_GOLEM_OXIDIZED_DEATH : SoundEvents.SULFUR_CUBE_SMALL_DEATH; }
}
