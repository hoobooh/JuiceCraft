package com.usagin.juicecraft.miscentities;

import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
    public static AttributeSupplier.Builder getChargeAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1).add(Attributes.MOVEMENT_SPEED, 0).add(Attributes.ATTACK_DAMAGE, 0);
    }

    public Sora sora;
    public int soraid;
    public int lifetime = -100;

    public SoraChargeEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setInvulnerable(true);
        this.setNoGravity(true);
        this.noPhysics = true;
        this.setDiscardFriction(true);
        this.setCustomNameVisible(false);
    }
    public boolean shouldShowName() {
        return false;
    }


    public boolean canCollideWith(Entity pEntity) {
        return pEntity.canBeCollidedWith() && !this.isPassengerOfSameVehicle(pEntity);
    }
    public void hurtAllTargets(){
        AABB box = this.getBoundingBox().inflate((30F-this.lifetime)/5);
        List<Entity> list = this.level().getEntities(this,box);
        for(Entity e: list){
            if(e instanceof LivingEntity liv){
                if(!EnemyEvaluator.shouldDoHurtTarget(this.sora,liv)){
                    continue;
                }
            }
            int a = this.sora.getSkillLevels()[5];
            float n = -4/(0.15F*a+1) + 4;
            e.hurt(this.sora.damageSources().mobAttack(this.sora), 20 * n);
        }
        box = this.getBoundingBox().inflate((30F-this.lifetime)*2);
        list = this.level().getEntities(this,box);
        for(Entity e: list){
            if(e instanceof LivingEntity liv){
                if(!EnemyEvaluator.shouldDoHurtTarget(this.sora,liv)){
                    continue;
                }
            }
            e.addDeltaMovement(this.position().subtract(e.position()).multiply(0.05,0.05,0.05));
        }
    }
    @Override
    public void tick() {
        if(this.lifetime==14){
            //this.playSound(SoraSoundInit.SORA_CHARGE_ROAR.get());
        }
        if (this.lifetime != -100) {
            this.lifetime--;
        }
        if (this.lifetime == 0) {
            this.remove(RemovalReason.DISCARDED);
        }
        //this.setDeltaMovement(Vec3.ZERO);
        if(this.sora != null){
            this.hurtAllTargets();
        }
        super.tick();


    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

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
