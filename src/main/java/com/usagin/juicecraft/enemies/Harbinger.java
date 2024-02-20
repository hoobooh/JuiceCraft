package com.usagin.juicecraft.enemies;

import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.ai.awareness.FriendDefense;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.core.particles.ParticleTypes.SWEEP_ATTACK;

public class Harbinger extends Monster {

    protected Harbinger(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    AnimationState attackAnimState = new AnimationState();
    AnimationState walkAnimState = new AnimationState();
    AnimationState otherAnimState = new AnimationState();
    @Override
    public void tick(){
        super.tick();
        if(this.level().isClientSide()){
            this.attackAnimState.animateWhen(this.getSyncInt(ATTACKCOUNTER)>0,this.tickCount);
            this.walkAnimState.animateWhen(this.walkAnimation.isMoving(),this.tickCount);
            this.otherAnimState.animateWhen(this.getSyncInt(ANIMCOUNTER)>0,this.tickCount);
        }else{
            this.decrementCounters();
        }
    }
    public void decrementCounters(){
        for(EntityDataAccessor<Integer> acc: LIST){
            var n=this.getSyncInt(acc);
            if(n>0){
                this.setSyncInt(acc, n-1);
            }
        }
    }
    public void checkAndPerformAttack(){
        int n = this.getSyncInt(ATTACKCOUNTER);
        int type = this.getSyncInt(ATTACKTYPE);
        if(n>0){

            //SHIELD
            //1: push, 15
            //2: slam, 13
            //3: swing, 9
            if(!this.isUsingSword()){
                if(type==1){
                    if(n==15){
                        this.doHurtTarget(20,2);
                    }
                } else if (type == 2){
                    if(n==13){
                        this.doHurtTarget(360,1);
                    }
                }else{
                    if(n==9){
                        this.doHurtTarget(30,3);
                    }
                }
            }
            //SWORD
            //1: swing, 27 + 18
            //2: uppercut, 30, 19
            //3: slam, 30 + 14
            else{
                if(type==1){
                    if(n==27 || n==18){
                        this.doHurtTarget(30,3);
                    }
                } else if (type == 2){
                    if(n==30 || n==19){
                        this.doHurtTarget(30,4);
                    }
                }else{
                    if(n==30 || n==14){
                        this.doHurtTarget(90,3);
                    }
                }
            }

        }
    }
    public void doHurtTarget(double maxFov, double range) {
        range=range/2;
        AABB hitTracer = new AABB(this.getX() - range, this.getY(), this.getZ() - range, this.getX() + range, this.getY() + 5, this.getZ() + range);
        List<Entity> entityList = this.level().getEntities(this, hitTracer);
        if (this.getTarget() != null) {
            this.lookAt(this.getTarget(), 360, 360);
        }
        double angle = Math.atan2(this.getLookAngle().z, this.getLookAngle().x);
        angle = Math.toDegrees(angle);
        for (Entity e : entityList) {
            if (e instanceof LivingEntity ent) {
                double entityAngle = -Math.atan2(e.position().z - this.position().z, e.position().x - this.position().x);
                entityAngle = Math.toDegrees(entityAngle);
                if (Math.abs(Math.abs(angle) - Math.abs(entityAngle)) < maxFov) {
                    this.doHurtTarget(e);
                }
            } else {
                double entityAngle = -Math.atan2(e.position().z - this.position().z, e.position().x - this.position().x);
                entityAngle = Math.toDegrees(entityAngle);
                if (Math.abs(Math.abs(angle) - Math.abs(entityAngle)) < maxFov) {
                    this.doHurtTarget(e);
                }
            }
        }
    }
    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (pEntity.level() instanceof ServerLevel t) {
            t.sendParticles(SWEEP_ATTACK, pEntity.getX(), pEntity.getY() + 1, pEntity.getZ(), 1, 0.2, 0.2, 0.2, 0.3);
        }
        boolean flag = false;
        if (pEntity != null) {
            if (this.distanceTo(pEntity) < 3) {
                float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * this.getSyncInt(ATTACKTYPE);
                float f1 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
                if (pEntity instanceof LivingEntity) {
                    f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity) pEntity).getMobType());
                    f1 += (float) EnchantmentHelper.getKnockbackBonus(this);
                }
                flag = pEntity.hurt(this.damageSources().mobAttack(this), f);
                if (flag) {
                    if (f1 > 0.0F && pEntity instanceof LivingEntity) {
                        ((LivingEntity) pEntity).knockback(f1 * 0.5F, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180F)));
                        this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
                    }

                    this.doEnchantDamageEffects(this, pEntity);
                    this.setLastHurtMob(pEntity);
                }
            }
        }
        return flag;
    }
    public boolean isUsingSword(){
        return this.getSyncBoolean(SWORD);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("juicecraft.harbinger.peaceful",this.getSyncBoolean(PEACEFUL));
        pCompound.putBoolean("juicecraft.harbinger.sword",this.getSyncBoolean(SWORD));
    }
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setSyncBoolean(PEACEFUL,pCompound.getBoolean("juicecraft.harbinger.peaceful"));
        this.setSyncBoolean(SWORD,pCompound.getBoolean("juicecraft.harbinger.sword"));
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
    public boolean shouldResetRightArm(){
        return this.getSyncInt(ANIMTYPE) == 1 && this.getSyncInt(ANIMCOUNTER) > 0;
    }
    public boolean shouldLockHead(){
        return this.getSyncInt(ANIMTYPE) == 0 && this.getSyncInt(ANIMCOUNTER) > 0;
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(PEACEFUL,true);
        this.getEntityData().define(SWORD,true);
        this.getEntityData().define(ATTACKCOUNTER,0);
        this.getEntityData().define(ANIMCOUNTER,0);
        this.getEntityData().define(ATTACKTYPE,0);
        this.getEntityData().define(ANIMTYPE,0);
    }
    public static EntityDataAccessor<Boolean> PEACEFUL = SynchedEntityData.defineId(Harbinger.class, EntityDataSerializers.BOOLEAN);
    public static EntityDataAccessor<Boolean> SWORD = SynchedEntityData.defineId(Harbinger.class, EntityDataSerializers.BOOLEAN);
    public static EntityDataAccessor<Integer> ATTACKCOUNTER = SynchedEntityData.defineId(Harbinger.class, EntityDataSerializers.INT);
    public static EntityDataAccessor<Integer> ANIMCOUNTER = SynchedEntityData.defineId(Harbinger.class, EntityDataSerializers.INT);
    public static EntityDataAccessor<Integer> ATTACKTYPE = SynchedEntityData.defineId(Harbinger.class, EntityDataSerializers.INT);
    public static EntityDataAccessor<Integer> ANIMTYPE = SynchedEntityData.defineId(Harbinger.class, EntityDataSerializers.INT);
    public static final List<EntityDataAccessor<Integer>> LIST = Arrays.asList(ATTACKCOUNTER,ANIMCOUNTER);
}
