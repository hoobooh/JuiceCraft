package com.usagin.juicecraft.enemies;

import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.Init.sounds.UniversalSoundInit;
import com.usagin.juicecraft.ai.goals.harbinger.HarbingerMeleeAttackGoal;
import com.usagin.juicecraft.friends.Alte;
import com.usagin.juicecraft.network.CircleParticlePacketHandler;
import com.usagin.juicecraft.network.ToClientCircleParticlePacket;
import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.VillagePools;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.core.particles.ParticleTypes.SWEEP_ATTACK;

public class Harbinger extends Monster {

    public Harbinger(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward=50;
        this.setPersistenceRequired();
    }
    public boolean isPersistenceRequired() {
        return true;
    }
    public boolean canDisableShield() {
        return true;
    }
    public static AttributeSupplier.Builder getHarbingerAttributes() {
        return Mob.createMobAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 0.9).add(Attributes.MAX_HEALTH, 300).add(Attributes.ARMOR, 20).add(Attributes.MOVEMENT_SPEED, 0.15).add(Attributes.ATTACK_DAMAGE, 10).add(Attributes.ATTACK_KNOCKBACK,5);
    }

    public AnimationState attackAnimState = new AnimationState();
    public AnimationState idleAnimState = new AnimationState();
    public AnimationState walkAnimState = new AnimationState();
    public AnimationState counterAnimState = new AnimationState();
    public AnimationState otherAnimState = new AnimationState();
    boolean queuehostile = false;
    int previouscount = 0;
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
    public void playSound(SoundEvent pSound) {
        if (!this.isSilent()) {
            this.playSound(pSound, 1.5F, 1.0F);
        }

    }
    public boolean isAbsorbingLife(){
        return this.getSyncInt(LIFECOUNTER)>0;
    }
    public boolean flagForWake = false;
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            int n = this.getSyncInt(ATTACKCOUNTER);
            int t = this.getSyncInt(ATTACKTYPE);
            this.walkAnimState.animateWhen(this.walkAnimation.speed() >= 0.1F,this.tickCount);
            this.attackAnimState.animateWhen(n > 0 && t!=4, this.tickCount);
            this.idleAnimState.animateWhen(this.walkAnimation.speed() < 0.1F,this.tickCount);
            this.otherAnimState.animateWhen(this.getSyncInt(ANIMCOUNTER) > 0, this.tickCount);
            this.counterAnimState.animateWhen(n > 0 && t==4, this.tickCount);
        } else {
            if(this.tickCount%20==0 && this.getHealth() < this.getMaxHealth() && !this.isDeadOrDying()){
                this.setHealth(Mth.clamp(this.getHealth()+1,0,this.getMaxHealth()));
            }
            this.previouscount=this.getSyncInt(ANIMCOUNTER);
            this.decrementCounters();
            this.checkAndPerformAttack();
            if(this.getSyncBoolean(PEACEFUL)){
                if((this.getTarget() instanceof Player || this.flagForWake) && this.getSyncInt(ANIMCOUNTER) <= 0 && this.previouscount==0){
                    this.setSyncInt(ANIMCOUNTER,50);
                    this.setSyncInt(ANIMTYPE,0);
                    this.playSound(UniversalSoundInit.HARBINGER_INTRO.get());
                    this.queuehostile=true;
                }
                if(this.getSyncInt(ANIMCOUNTER) == 0 && this.previouscount == 1){
                    this.setSyncBoolean(PEACEFUL,false);
                }
            }else if(!this.getSyncBoolean(SWORD)){
                if(this.getHealth() / this.getMaxHealth() < 0.5F){
                    this.setSyncBoolean(SWORD,true);
                    this.playSound(UniversalSoundInit.HARBINGER_UNSHEATHE.get());
                    this.setSyncInt(ANIMCOUNTER,50);
                    this.setSyncInt(ANIMTYPE,1);
                }
            }

        }
    }
    public void registerGoals(){
        this.goalSelector.addGoal(5, new HarbingerMeleeAttackGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true));
    }
    protected AABB getAttackBoundingBox() {
        Entity entity = this.getVehicle();
        AABB aabb;
        if (entity != null) {
            AABB aabb1 = entity.getBoundingBox();
            AABB aabb2 = this.getBoundingBox();
            aabb = new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
        } else {
            aabb = this.getBoundingBox();
        }

        return aabb.inflate(2, 0.0D, 2);
    }
    public void decrementCounters() {
        for (EntityDataAccessor<Integer> acc : LIST) {
            var n = this.getSyncInt(acc);
            if (n > 0) {
                this.setSyncInt(acc, n - 1);
            }
        }
    }
    public <T extends ParticleOptions> void spawnParticlesInUpFacingCircle(Entity entity, float radius, T type) {
        CircleParticlePacketHandler.sendToClient(new ToClientCircleParticlePacket(entity.getId(), radius, type), this);
    }
    public void checkAndPerformAttack() {
        int n = this.getSyncInt(ATTACKCOUNTER);
        int type = this.getSyncInt(ATTACKTYPE);
        if (n > 0) {

            //SHIELD
            //1: push, 15
            //2: slam, 13
            //3: swing, 9
            if (!this.isUsingSword()) {
                if (type == 1) {
                    if (n == 15) {
                        this.doHurtTarget(40, 5);
                        this.playSound(UniversalSoundInit.HEAVY_ATTACK.get());
                    }
                } else if (type == 2) {
                    if (n == 13) {
                        this.playSound(UniversalSoundInit.HARBINGER_SLAM.get());
                        this.spawnParticlesInUpFacingCircle(this,1.5F, ParticleTypes.CRIT);
                        this.doHurtTarget(180, 4);
                    }
                } else if (type==3){
                    if (n == 9) {
                        this.playSound(UniversalSoundInit.HARBINGER_SLASH.get());
                        this.doHurtTarget(50, 5);
                    }
                }   else{
                    if(n==17){
                        this.playSound(UniversalSoundInit.HARBINGER_SLAM.get());
                        this.doHurtTarget(60,6);
                    }
                }
            }
            //SWORD
            //1: swing, 27 + 18
            //2: uppercut, 30, 19
            //3: slam, 30 + 14
            else {
                if (type == 1) {
                    if (n == 27 || n == 18) {
                        this.doHurtTarget(50, 5);
                        this.playSound(UniversalSoundInit.HARBINGER_SLASH.get());
                    }
                } else if (type == 2) {
                    if (n == 30 || n == 19) {
                        if(n==30){
                            this.spawnParticlesInUpFacingCircle(this,1.5F, ParticleTypes.CRIT);
                            this.playSound(UniversalSoundInit.HARBINGER_SLAM.get());
                        }else{
                            this.playSound(UniversalSoundInit.HARBINGER_SLASH.get());
                        }
                        this.doHurtTarget(40, 6);

                    }
                } else if (type==3){
                    if (n == 30 || n == 14) {
                        if(n==30){
                            this.spawnParticlesInUpFacingCircle(this,1.5F, ParticleTypes.CRIT);
                            this.spawnParticlesInUpFacingCircle(this,1.5F, ParticleTypes.FLAME);
                            this.playSound(UniversalSoundInit.HARBINGER_SLAM.get());
                        }else{
                            this.playSound(UniversalSoundInit.HARBINGER_SLASH.get());
                        }
                        this.doHurtTarget(90, 5);
                    }
                }else{
                    if(n==15){
                        this.playSound(UniversalSoundInit.HARBINGER_SLASH.get());
                        this.doHurtTarget(90,7);
                        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3, true, Level.ExplosionInteraction.NONE);
                    }
                }
            }

        }
    }
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isInvulnerableTo(pSource)) {
            return false;
        }
        if(this.getRandom().nextInt(0,10) <3 && (this.getSyncInt(ATTACKCOUNTER) > 20 || this.getSyncInt(ATTACKCOUNTER) ==0)&& this.getSyncInt(ATTACKTYPE)!=4 && !this.getSyncBoolean(PEACEFUL)
        && this.getSyncInt(ANIMCOUNTER) <= 0){
            this.setSyncInt(ATTACKCOUNTER,40);
            this.setSyncInt(ATTACKTYPE,4);
            this.playSound(UniversalSoundInit.COUNTER_BLOCK.get());
            return false;
        }else{
            boolean flag = super.hurt(pSource,pAmount);
            if(flag){
                this.flagForWake=true;
            }
            return flag;
        }
    }

    public void swing() {
        if (this.getSyncInt(ATTACKCOUNTER) <= 0 && this.getSyncInt(ANIMCOUNTER) <= 0) {
            int n = this.getRandom().nextInt(1, 4);
            if (this.isUsingSword()) {
                if (n == 1) {
                    this.setSyncInt(ATTACKCOUNTER, 40);
                    this.setSyncInt(ATTACKTYPE,1);
                } else if (n == 2) {
                    this.setSyncInt(ATTACKCOUNTER, 50);
                    this.setSyncInt(ATTACKTYPE,2);
                } else {
                    this.setSyncInt(ATTACKCOUNTER, 50);
                    this.setSyncInt(ATTACKTYPE,3);
                }
            } else {
                if (n == 1) {
                    this.setSyncInt(ATTACKCOUNTER, 30);
                    this.setSyncInt(ATTACKTYPE,1);
                } else if (n == 2) {
                    this.setSyncInt(ATTACKCOUNTER, 30);
                    this.setSyncInt(ATTACKTYPE,2);
                } else {
                    this.setSyncInt(ATTACKCOUNTER, 25);
                    this.setSyncInt(ATTACKTYPE,3);
                }
            }
        }
    }

    public void doHurtTarget(double maxFov, double range) {
        range = range / 2;
        AABB hitTracer = new AABB(this.getX() - range, this.getY(), this.getZ() - range, this.getX() + range, this.getY() + 5, this.getZ() + range);
        List<Entity> entityList = this.level().getEntities(this, hitTracer);
        if (this.getTarget() != null) {
            this.lookAt(this.getTarget(), 360, 360);
        }
        double angle = Math.atan2(this.getLookAngle().z, this.getLookAngle().x);
        angle = Math.toDegrees(angle);
        for (Entity e : entityList) {
                double entityAngle = -Math.atan2(e.position().z - this.position().z, e.position().x - this.position().x);
                entityAngle = Math.toDegrees(entityAngle);
                if (Math.abs(Math.abs(angle) - Math.abs(entityAngle)) < maxFov) {
                    this.doHurtTarget(e);
                }

        }
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (pEntity.level() instanceof ServerLevel t) {
            t.sendParticles(SWEEP_ATTACK, pEntity.getX(), pEntity.getY() + 1, pEntity.getZ(), 1, 0.2, 0.2, 0.2, 0.3);
        }
        boolean flag = false;
        if (this.distanceTo(pEntity) < 10) {
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
        return flag;
    }

    public boolean isUsingSword() {
        return this.getSyncBoolean(SWORD);
    }

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

    public boolean shouldResetRightArm() {
        return (this.getSyncInt(ANIMTYPE) == 1 && this.getSyncInt(ANIMCOUNTER) > 0 ) || this.counterAnimState.isStarted() || this.attackAnimState.isStarted();
    }

    public boolean shouldLockHead() {
        return this.getSyncInt(ANIMTYPE) == 0 && this.getSyncInt(ANIMCOUNTER) > 0;
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
    public static EntityDataAccessor<Boolean> PEACEFUL = SynchedEntityData.defineId(Harbinger.class, EntityDataSerializers.BOOLEAN);
    public static EntityDataAccessor<Boolean> SWORD = SynchedEntityData.defineId(Harbinger.class, EntityDataSerializers.BOOLEAN);
    public static EntityDataAccessor<Integer> ATTACKCOUNTER = SynchedEntityData.defineId(Harbinger.class, EntityDataSerializers.INT);
    public static EntityDataAccessor<Integer> ANIMCOUNTER = SynchedEntityData.defineId(Harbinger.class, EntityDataSerializers.INT);
    public static EntityDataAccessor<Integer> ATTACKTYPE = SynchedEntityData.defineId(Harbinger.class, EntityDataSerializers.INT);
    public static EntityDataAccessor<Integer> ANIMTYPE = SynchedEntityData.defineId(Harbinger.class, EntityDataSerializers.INT);
    public static final List<EntityDataAccessor<Integer>> LIST = Arrays.asList(ATTACKCOUNTER, ANIMCOUNTER,LIFECOUNTER);
}
