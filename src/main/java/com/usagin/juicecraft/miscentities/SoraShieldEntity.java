package com.usagin.juicecraft.miscentities;

import com.usagin.juicecraft.ai.goals.sora.SoraShieldGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Collections;

public class SoraShieldEntity extends LivingEntity {
    public LivingEntity host;
    public SoraShieldEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setInvulnerable(true);
        this.setNoGravity(true);
        this.noPhysics=true;
    }
    public boolean canCollideWith(Entity pEntity) {
        return false;
    }


    @Override
    public void tick(){
        if(this.host!=null && this.getVehicle()==null){
        this.startRiding(this.host);
        }
        super.tick();
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.host= (LivingEntity) this.level().getEntity(pCompound.getInt("juicecraft.sora.shield.host"));
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

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        if(this.host!=null){
        pCompound.putInt("juiceraft.sora.shield.host",this.host.getId());}
    }
}
