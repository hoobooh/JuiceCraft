package com.usagin.juicecraft.enemies;

import com.usagin.juicecraft.Init.sounds.UniversalSoundInit;
import com.usagin.juicecraft.ai.goals.harbinger.HarbingerMeleeAttackGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Seagull extends Animal implements FlyingAnimal {
    public Seagull(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public void registerGoals(){
        this.goalSelector.addGoal(5, new HarbingerMeleeAttackGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }
    protected SoundEvent getDeathSound() {
        return UniversalSoundInit.HARBINGER_DEATH.get();
    }
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return UniversalSoundInit.HARBINGER_HIT.get();
    }
    protected SoundEvent getAmbientSound() {
        return SoundEvents.EMPTY;
    }
    protected SoundEvent getStepSound() {
        return UniversalSoundInit.HARBINGER_STEP.get();
    }
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(this.getStepSound(), 1F, 1.0F);
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(PEACEFUL, true);
        this.getEntityData().define(SWORD, true);
        this.getEntityData().define(ATTACKCOUNTER, 0);
        this.getEntityData().define(ANIMCOUNTER, 0);
        this.getEntityData().define(ATTACKTYPE, 0);
        this.getEntityData().define(ANIMTYPE, 0);
        this.getEntityData().define(LIFECOUNTER, 0);
    }
    public static EntityDataAccessor<Integer> LIFECOUNTER = SynchedEntityData.defineId(Harbinger.class, EntityDataSerializers.INT);
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if(this.queuehostile){
            pCompound.putBoolean("juicecraft.harbinger.peaceful",false);
        }else{
            pCompound.putBoolean("juicecraft.harbinger.peaceful",this.getSyncBoolean(PEACEFUL));
        }
        pCompound.putBoolean("juicecraft.harbinger.sword", this.getSyncBoolean(SWORD));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if(pCompound.contains("juicecraft.harbinger.peaceful")){
            this.setSyncBoolean(PEACEFUL, pCompound.getBoolean("juicecraft.harbinger.peaceful"));
        }
        this.setSyncBoolean(SWORD, pCompound.getBoolean("juicecraft.harbinger.sword"));
    }

    public int getSyncInt(EntityDataAccessor<Integer> accessor) {
        return this.getEntityData().get(accessor);
    }

    public void setSyncInt(EntityDataAccessor<Integer> accessor, int n) {
        this.getEntityData().set(accessor, n);
    }

    public void setSyncBoolean(EntityDataAccessor<Boolean> accessor, boolean n) {
        this.getEntityData().set(accessor, n);
    }

    public boolean getSyncBoolean(EntityDataAccessor<Boolean> accessor) {
        return this.getEntityData().get(accessor);
    }

}
