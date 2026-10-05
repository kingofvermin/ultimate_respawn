package com.kvermin.ultimate_respawn.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class FlyableMob extends PathfinderMob {
    private boolean hasFlyingNavigation;
    public static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(FlyableMob.class, EntityDataSerializers.BOOLEAN);

    protected FlyableMob(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setNavigation(false);
    }

    public static class RandomStrollOrFlyingGoal extends WaterAvoidingRandomStrollGoal {
        protected final FlyableMob mob;

        public RandomStrollOrFlyingGoal(FlyableMob pMob, double pSpeedModifier) {
            super(pMob, pSpeedModifier);
            this.mob = pMob;
        }

        @Nullable
        protected Vec3 getPosition() {
            if (mob.isFlying()) {
                // Copied from WaterAvoidingRandomFlyingGoal
                Vec3 vec3 = this.mob.getViewVector(0.0F);
                Vec3 vec31 = HoverRandomPos.getPos(this.mob, 8, 7, vec3.x, vec3.z, ((float) Math.PI / 2F), 3, 1);
                vec31 = vec31 != null ? vec31 : AirAndWaterRandomPos.getPos(this.mob, 8, 4, -2, vec3.x, vec3.z, ((float) Math.PI / 2F));
                vec31.add(0, Mth.clamp(vec31.y - getBlockBelow(vec31, this.mob.level(), this.mob).getY(), this.mob.minHeight(), this.mob.maxHeight()), 0);
                return vec31;
            } else {
                return super.getPosition();
            }
        }
    }

    public static class ClampHeightGoal extends Goal {
        protected final FlyableMob mob;

        public ClampHeightGoal(FlyableMob pMob) {
            this.mob = pMob;
        }

        @Override
        public boolean canUse() {
            return mob.isFlying();
        }

        @Override
        public void tick() {
            super.tick();
        }
    }

    public static BlockPos getBlockBelow(FlyableMob pMob) {
        return getBlockBelow(pMob.position(), pMob.level(), pMob);
    }
    public static BlockPos getBlockBelow(Vec3 posAbove, Level level) {
        return getBlockBelow(posAbove, level, null);
    }
    public static BlockPos getBlockBelow(Vec3 posAbove, Level level, Entity entity) {
        Vec3 vec3 = new Vec3(posAbove.x(), level.getMinY(), posAbove.z());
        return level.clip(new ClipContext(posAbove, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, entity)).getBlockPos();
    }

    public int minHeight() {
        return 0;
    }
    public int maxHeight() {
        return 255;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(FLYING, false);
    }
    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("Flying", this.entityData.get(FLYING));
    }
    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.entityData.set(FLYING, input.getBooleanOr("Flying", false));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isFlying() && !this.hasFlyingNavigation) {
            setNavigation(true);
        } else if (!this.isFlying() && this.hasFlyingNavigation) {
            setNavigation(false);
        }
        this.setNoGravity(this.isFlying());
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageModifier, DamageSource damageSource) {
        if (this.isFlying()) {
            return false;
        }
        return super.causeFallDamage(fallDistance, damageModifier, damageSource);
    }

    public boolean isFlying() { return this.entityData.get(FLYING); }
    public void setFlying(boolean pBoolean) {
        this.entityData.set(FLYING, pBoolean);
    }

    private void setNavigation(boolean isFlying) {
        if (isFlying) {
            this.moveControl = new FlyingMoveControl(this, 20, true);
            this.navigation = new FlyingPathNavigation(this, level());
            this.hasFlyingNavigation = true;
        } else {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.hasFlyingNavigation = false;
        }
    }
}
