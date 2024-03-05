package com.usagin.juicecraft.miscentities;

import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.Init.sounds.SoraSoundInit;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.ai.goals.sora.SoraShieldGoal;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.Collections;
import java.util.List;

public class SoraShieldEntity extends LivingEntity {
    public LivingEntity host;
    public double damagetaken = 0;
    public int lifetime = -100;
    public SoraShieldEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setInvulnerable(true);
        this.setNoGravity(true);
        this.noPhysics=true;

    }
    public boolean canCollideWith(Entity pEntity) {
        return pEntity.canBeCollidedWith() && !this.isPassengerOfSameVehicle(pEntity);
    }

    public void releaseEnergy(Sora sora){
        sora.spawnParticlesInUpFacingCircle(this,10, ParticleInit.SORA_ENERGY_PARTICLE.get());
        this.playSound(SoraSoundInit.SHIELD_DETONATE.get());
        AABB box = this.getBoundingBox().inflate(5);
        List<Entity> list = this.level().getEntities(this,box);
        for(Entity e: list){
            if(e instanceof LivingEntity entity){
                if(!EnemyEvaluator.shouldDoHurtTarget(sora,entity)){
                    continue;
                }
            }
            e.hurt(sora.damageSources().explosion(this,sora), (float) (this.damagetaken*sora.getSkillLevels()[3]*3)/(2+sora.getSkillLevels()[3]));
        }
        this.remove(RemovalReason.DISCARDED);
    }


    @Override
    public void tick(){
        if(this.host!=null && this.getVehicle()==null){
        this.startRiding(this.host);
        }
        if(this.lifetime!=-100){
            this.lifetime--;
        }
        if(this.lifetime==-10){
            this.remove(RemovalReason.DISCARDED);
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
        this.lifetime=pCompound.getInt("juicecraft.sora.shield.lifetime");
        this.damagetaken=pCompound.getDouble("juicecraft.sora.shield.damagetaken");
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
        pCompound.putInt("juiceraft.sora.shield.host",this.host.getId());
        pCompound.putInt("juicecraft.sora.shield.lifetime",this.lifetime);
        pCompound.putDouble("juicecraft.sora.shield.damagetaken",this.damagetaken);
        }
    }
}
