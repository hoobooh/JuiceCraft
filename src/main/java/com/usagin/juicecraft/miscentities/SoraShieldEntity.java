package com.usagin.juicecraft.miscentities;

import com.google.common.collect.ImmutableList;
import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.Init.sounds.SoraSoundInit;
import com.usagin.juicecraft.Init.sounds.UniversalSoundInit;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Sora;
import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SoraShieldEntity extends LivingEntity {
    public static AttributeSupplier.Builder getShieldAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1).add(Attributes.MOVEMENT_SPEED, 0).add(Attributes.ATTACK_DAMAGE, 0);
    }

    public LivingEntity host;
    public int hostid;
    public double damagetaken = 0;
    public int lifetime = -100;

    public SoraShieldEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setInvulnerable(true);
        this.setNoGravity(true);
        this.noPhysics = true;
        this.aiStep();
        this.setCustomNameVisible(false);
    }

    public void aiStep() {

    }
    public boolean canCollideWith(Entity pEntity) {
        return pEntity.canBeCollidedWith() && !this.isPassengerOfSameVehicle(pEntity);
    }

    public void releaseEnergy(Sora sora) {
        if(this.level() instanceof ServerLevel level){
            sora.spawnParticlesInSphereAtEntity(this,10,4,0,level,ParticleInit.SORA_ENERGY_PARTICLE.get(),0);
            this.playSound(UniversalSoundInit.FRIEND_DEATH.get());
            AABB box = this.getBoundingBox().inflate(5);
            List<Entity> list = this.level().getEntities(this, box);
            for (Entity e : list) {
                if (e instanceof LivingEntity entity) {
                    if (!EnemyEvaluator.shouldDoHurtTarget(sora, entity)) {
                        continue;
                    }
                }
                e.hurt(sora.damageSources().explosion(this, sora), (float) (this.damagetaken/5 * sora.getSkillLevels()[3] * 3) / (2 + sora.getSkillLevels()[3]));
            }
        }
        this.remove(RemovalReason.DISCARDED);
    }

    protected float ridingOffset(Entity pEntity) {
        return -2F;
    }
    @Override
    public void tick() {

        /*if(this.sora!=null && this.getVehicle()==null){
            AlteLightningParticle.LOGGER.info(this.startRiding(this.sora) +"");
        //this.startRiding(this.sora);
        }*/
        if(this.tickCount%63==1){
            this.playSound(SoraSoundInit.SORA_SHIELD_HUM.get(),0.7F,1);
        }
        if(!this.level().isClientSide()){
            if(this.host==null){
                List<Entity> list = this.level().getEntities(null,this.getBoundingBox().inflate(5));
                for(Entity e: list){
                    if(e instanceof LivingEntity entity && e.getUUID().compareTo(this.uuid)==0){
                        this.host=entity;
                        this.hostid=this.host.getId();
                    }
                }
            }
            this.getEntityData().set(id,this.hostid);
        }else{
            this.hostid=this.getEntityData().get(id);
        }
        if (this.lifetime != -100) {
            this.lifetime--;
        }
        if (this.lifetime == -10) {
            this.remove(RemovalReason.DISCARDED);
        }
        this.setDeltaMovement(Vec3.ZERO);

        super.tick();

        if(this.host==null){
            this.host= (LivingEntity) this.level().getEntity(this.hostid);
        }

        if(this.host!=null){
            this.setPos(this.host.position());
        }

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
    public static EntityDataAccessor<Integer> id = SynchedEntityData.defineId(SoraShieldEntity.class,EntityDataSerializers.INT);

    UUID uuid;

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.uuid = pCompound.getUUID("juicecraft.sora.shield.sora");
        this.lifetime = pCompound.getInt("juicecraft.sora.shield.lifetime");
        this.damagetaken = pCompound.getDouble("juicecraft.sora.shield.damagetaken");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.host != null) {
            pCompound.putUUID("juicecraft.sora.shield.sora", this.host.getUUID());
            pCompound.putInt("juicecraft.sora.shield.lifetime", this.lifetime);
            pCompound.putDouble("juicecraft.sora.shield.damagetaken", this.damagetaken);
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
