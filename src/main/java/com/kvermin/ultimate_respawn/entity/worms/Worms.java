package com.kvermin.ultimate_respawn.entity.worms;

import com.kvermin.ultimate_respawn.item.URItems;
import com.kvermin.ultimate_respawn.particle.URParticleTypes;
import com.kvermin.ultimate_respawn.sound.URSounds;
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
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import org.jspecify.annotations.Nullable;

public class Worms extends Mob {
    public static final EntityDataAccessor<Integer> WRITHING_START = SynchedEntityData.defineId(Worms.class, EntityDataSerializers.INT);

    public Worms(EntityType<? extends Mob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20D).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.ATTACK_KNOCKBACK, 1.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.35D).add(Attributes.MOVEMENT_SPEED, 0.04D);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void onDamageTaken(DamageContainer damageContainer) {
        super.onDamageTaken(damageContainer);
        ((ServerLevel) this.level()).sendParticles(URParticleTypes.WORMS.get(), this.getX(), this.getY() + 0.25, this.getZ(), 5, 0.5, 0.25, 0.5, 1);
        ((ServerLevel) this.level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, URItems.CRUOR_BALL.get()), this.getX(), this.getY() + 0.5, this.getZ(), 2, 0.5, 0.25, 0.5, 0);
        this.entityData.set(WRITHING_START, this.tickCount);
    }

    @Override
    protected void triggerOnDeathMobEffects(ServerLevel level, RemovalReason reason) {
        super.triggerOnDeathMobEffects(level, reason);
        ((ServerLevel) this.level()).sendParticles(URParticleTypes.WORMS.get(), this.getX(), this.getY() + 0.5, this.getZ(), 50, 0.5, 0.25, 0.5, 2);
        ((ServerLevel) this.level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, URItems.CRUOR_BALL.get()), this.getX(), this.getY() + 0.5, this.getZ(), 50, 0.75, 0.25, 0.75, 0);
        this.level().playSound((Player) null, this.blockPosition(), URSounds.WORMS_BURST.value(), SoundSource.HOSTILE, 0.25F, 1F);
    }

    @Override
    public void knockback(double power, double xd, double zd, DamageSource source, float damage, boolean comesFromEffect) {
        super.knockback(power, xd, zd, source, damage, comesFromEffect);

        LivingKnockBackEvent event = CommonHooks.onLivingKnockBack(this, (float) power, xd, zd);
        if (!event.isCanceled() && event.getStrength() * (1 - this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)) > 0) {
            Vec3 deltaMovement = this.getDeltaMovement();
            this.setDeltaMovement(
                    deltaMovement.x,
                    this.onGround() ? 0 : deltaMovement.y,
                    deltaMovement.z
            );
        }
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
        SpawnGroupData toReturn = super.finalizeSpawn(level, difficulty, spawnReason, groupData);
        this.setRot(90 * this.random.nextInt(4), 0);
        return toReturn;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(WRITHING_START, -60);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) { return SoundEvents.SULFUR_CUBE_HURT; }
    @Override
    protected @Nullable SoundEvent getDeathSound() { return SoundEvents.SULFUR_CUBE_SMALL_DEATH; }

    public float getWrithingStartTick() {
        return this.entityData.get(WRITHING_START);
    }
}
