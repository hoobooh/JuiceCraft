package com.usagin.juicecraft.miscentities;

import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.Init.sounds.SoraSoundInit;
import com.usagin.juicecraft.Init.sounds.UniversalSoundInit;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SoraChargeEntity extends LivingEntity {
    public static AttributeSupplier.Builder getShieldAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1).add(Attributes.MOVEMENT_SPEED, 0).add(Attributes.ATTACK_DAMAGE, 0);
    }

    public Sora sora;
    public int soraid;
    public double damagetaken = 0;
    public int lifetime = -100;

    public SoraChargeEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setInvulnerable(true);
        this.setNoGravity(true);
        this.noPhysics = true;
        this.aiStep();
    }

    public void aiStep() {

    }
    public boolean canCollideWith(Entity pEntity) {
        return pEntity.canBeCollidedWith() && !this.isPassengerOfSameVehicle(pEntity);
    }

    protected float ridingOffset(Entity pEntity) {
        return -2F;
    }
    @Override
    public void tick() {
        if(this.tickCount%63==1){
            this.playSound(SoraSoundInit.SORA_SHIELD_HUM.get(),0.7F,1);
        }
        if(!this.level().isClientSide()){
            this.getEntityData().set(id,this.soraid);
        }else{
            this.soraid =this.getEntityData().get(id);
        }
        if (this.lifetime != -100) {
            this.lifetime--;
        }
        if (this.lifetime == 0) {
            this.remove(RemovalReason.DISCARDED);
        }
        this.setDeltaMovement(Vec3.ZERO);

        super.tick();


    }
    public @NotNull Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(id,0);
    }
    public static EntityDataAccessor<Integer> id = SynchedEntityData.defineId(SoraChargeEntity.class,EntityDataSerializers.INT);

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        UUID id = pCompound.getUUID("juicecraft.sora.charge.sora");
        for(Entity e: this.level().getEntities(this,this.getBoundingBox().inflate(2))){
            if(e instanceof Sora entity && e.getUUID().compareTo(id)==0){
                this.sora =entity;
                this.soraid =this.sora.getId();
            }
        }
        this.lifetime = pCompound.getInt("juicecraft.sora.charge.lifetime");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.sora != null) {
            pCompound.putUUID("juicecraft.sora.charge.sora", this.sora.getUUID());
            pCompound.putInt("juicecraft.sora.charge.lifetime", this.lifetime);
        }
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.singleton(ItemStack.EMPTY);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {

    }


}
