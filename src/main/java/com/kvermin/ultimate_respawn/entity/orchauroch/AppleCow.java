package com.kvermin.ultimate_respawn.entity.orchauroch;

import com.kvermin.ultimate_respawn.entity.beetle.Beetle;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.jetbrains.annotations.Nullable;

public class AppleCow extends Cow {
    public final AnimationState shakeAnimationState = new AnimationState();
    public static final EntityDataAccessor<Integer> GROWTH_STAGE = SynchedEntityData.defineId(AppleCow.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ANIMATION_START = SynchedEntityData.defineId(AppleCow.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ANIMATION_LENGTH = SynchedEntityData.defineId(AppleCow.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ANIMATION_ID = SynchedEntityData.defineId(AppleCow.class, EntityDataSerializers.INT);

    public static final int SHAKE_ANIMATION_ID = 1;

    public AppleCow(EntityType<? extends Cow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.2).add(Attributes.TEMPT_RANGE, 10.0);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        } else {
            if (this.tickCount % 1200 == 0 && this.random.nextInt(5) == 0) {
                if (this.getGrowthStage() < 3 && this.level().getRawBrightness(this.blockPosition(), 0) >= 9) {
                    this.setGrowthStage(this.getGrowthStage() + 1);
                } else if (this.getGrowthStage() == 3) {
                    this.level().playSound((Player)null, this, SoundEvents.AZALEA_LEAVES_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    ((ServerLevel) this.level()).sendParticles(ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, -9399763), this.getX(), this.getY() + 2.75, this.getZ(), 5, 0.25, 0.25, 0.25, 1);
                    this.setAnimationState(SHAKE_ANIMATION_ID);
                    this.dropApples(ItemStack.EMPTY);
                }
            }
        }
    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getItem() == Items.SHEARS) {
            if (!this.level().isClientSide() && this.getGrowthStage() == 3) {
                this.level().playSound((Entity) null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.dropApples(itemstack);

                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        } else if (itemstack.getItem() == Items.BONE_MEAL && this.getGrowthStage() < 3) {
            if (!this.level().isClientSide()) {
                this.level().playSound((Player) null, this, SoundEvents.BONE_MEAL_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                ((ServerLevel) this.level()).sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + 2.75, this.getZ(), 20, 0.35, 0.35, 0.35, 1);
                this.setGrowthStage(Math.min(this.getGrowthStage() + this.random.nextInt(2) + 1, 3));
                itemstack.shrink(1);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }


    public void dropApples(ItemStack itemstack) {
        this.setGrowthStage(0);

        this.dropFromShearingLootTable((ServerLevel) this.level(), BuiltInLootTables.SHEAR_SHEEP, itemstack, (l, drop) -> {
            for(int i = 0; i < drop.getCount(); ++i) {
                ItemEntity entity = this.spawnAtLocation(l, drop.copyWithCount(1), 1.0F);
                if (entity != null) {
                    entity.setDeltaMovement(entity.getDeltaMovement().add((double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (double)(this.random.nextFloat() * 0.05F), (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
                }
            }

        });
    }

    public int getGrowthStage() {
        return this.entityData.get(GROWTH_STAGE);
    }
    public void setGrowthStage(int value) { this.entityData.set(GROWTH_STAGE, value); }

    private void setupAnimationStates() {
        if (this.tickCount - this.entityData.get(ANIMATION_START) < this.entityData.get(ANIMATION_LENGTH)) {
            switch (this.entityData.get(ANIMATION_ID)) {
                case SHAKE_ANIMATION_ID:
                    this.shakeAnimationState.startIfStopped(this.tickCount);
                    break;
            }
        } else {
                this.shakeAnimationState.stop();
            }
    }

    private void setAnimationState(int id) {
        this.entityData.set(ANIMATION_START, this.tickCount);
        if (id == SHAKE_ANIMATION_ID) {
            this.entityData.set(ANIMATION_LENGTH, 30);
            this.entityData.set(ANIMATION_ID, SHAKE_ANIMATION_ID);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @org.jspecify.annotations.Nullable SpawnGroupData groupData) {
        this.entityData.set(GROWTH_STAGE, this.random.nextInt(4));
        return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(ANIMATION_START, this.tickCount);
        entityData.define(ANIMATION_LENGTH, 0);
        entityData.define(ANIMATION_ID, 0);
        entityData.define(GROWTH_STAGE, 0);
    }
    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("GrowthStage", this.entityData.get(GROWTH_STAGE));
    }
    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.entityData.set(GROWTH_STAGE, input.getIntOr("GrowthStage", 0));
    }
}
