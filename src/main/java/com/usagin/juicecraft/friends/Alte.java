package com.usagin.juicecraft.friends;

import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.Init.sounds.AlteSoundInit;
import com.usagin.juicecraft.Init.sounds.UniversalSoundInit;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.ai.goals.alte.AlteShockRodGoal;
import com.usagin.juicecraft.ai.goals.alte.AlteSparkGoal;
import com.usagin.juicecraft.data.dialogue.AbstractDialogueManager;
import com.usagin.juicecraft.data.dialogue.alte.AlteDialogueManager;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import java.util.Arrays;
import java.util.List;

import static com.usagin.juicecraft.Init.sounds.AlteSoundInit.*;

public class Alte extends OldWarFriend{
    public Alte(EntityType<? extends FakeWolf> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public static AttributeSupplier.Builder getAlteAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 25).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.ATTACK_DAMAGE, 3).add(ForgeMod.SWIM_SPEED.get(),3);
    }
    public boolean isUsingHyper(){
        return false;
    }
    public boolean isUsingShockRod(){
        int n = this.getAlteSyncInt(ALTE_RODCOOLDOWN);
        return (n < this.getRodDuration() && n > 1) || (this.getAlteSyncInt(ALTE_RODSHEATHCOUNTER)>0);
    }
    public boolean hasShellWeapon(){
        return this.isUsingShockRod();
    }
    public boolean isAttackLockout(){
        return this.isUsingHyper() || this.areAnimationsBusy();
    }
    @Override
    public SoundEvent getHitSound(){
        if(this.isUsingShockRod()){
            return UniversalSoundInit.CRITICAL_HIT.get();

        }else{
            return super.getHitSound();
        }
    }
    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if(this.getAttackCounter()>0 && this.isUsingShockRod()){
            if(!this.level().isClientSide()){
                this.playSound(UniversalSoundInit.ELECTRIC_STATIC.get(), 0.1F, 0.6F);
                this.spawnParticlesInSphereAtEntity(pEntity,4,2,2,(ServerLevel) this.level(),ParticleInit.ALTE_ENERGY_PARTICLE.get(),0);
                this.spawnParticlesInRandomSpreadAtEntity(pEntity,4,2,2,(ServerLevel) this.level(),ParticleInit.ALTE_LIGHTNING_PARTICLE.get());
            }
        }
        return super.doHurtTarget(pEntity);
    }
    public<T extends ParticleOptions> void spawnParticlesInRandomSpreadAtEntity(Entity entity, int count, float radius,float distance, ServerLevel sLevel, T type){
        float targetX = (float) entity.getX();
        float targetZ = (float) entity.getZ();
        float targetY = (float) entity.getEyeY();

        sLevel.sendParticles(type,targetX,targetY,targetZ,count,radius,radius,radius,1);
    }
    public<T extends ParticleOptions> void spawnParticlesInSphereAtEntity(Entity entity, int count, float radius, float distance, ServerLevel sLevel, T type, float yOffset){
        if(count<1){
            return;
        }
        float targetX = (float) entity.getX();
        float targetZ = (float) entity.getZ();
        float targetY = (float) entity.getEyeY();

        for(int i = 0; i < count; i++){
            float x = (float) (Math.sin(i))/2*radius;
            float z = (float) (Math.cos(i))/2*radius;
            if(this.getRandom().nextBoolean()){
                x=-x;
                z=-z;
            }
            sLevel.sendParticles(type,targetX + x,targetY + yOffset,targetZ + z,1,0,0,0,0.5);

        }

        this.spawnParticlesInSphereAtEntity(entity, (int)(count*0.8), radius*0.8F,distance, sLevel, type,yOffset+0.3F);
        this.spawnParticlesInSphereAtEntity(entity, (int)(count*0.8), radius*0.8F,distance, sLevel, type,yOffset-0.3F);

    }

    public void tick(){
        super.tick();
        //LOGGER.info(this.getAlteSyncInt(ALTE_RODCOOLDOWN) +"");
        if(!this.level().isClientSide()){
            if(this.sparkcooldown>0){
                this.sparkcooldown--;
            }
            if(this.getAlteSyncInt(ALTE_RODCOOLDOWN) < 12000){
                this.setAlteRodCooldown(this.getAlteSyncInt(ALTE_RODCOOLDOWN)+1);
            }
            for (EntityDataAccessor<Integer> counter : counters) {
                this.decrementAlteAnimCounter(counter);
            }
        }else{
            //LOGGER.info(this.getAlteSyncInt(ALTE_RODSHEATHCOUNTER) +"");
            this.sparkAnimState.animateWhen(this.getAlteSyncInt(ALTE_SPARKCOUNTER)>0,this.tickCount);
            this.rodSummonAnimState.animateWhen(this.getAlteSyncInt(ALTE_RODSUMMONCOUNTER)>0,this.tickCount);
            this.rodSheathAnimState.animateWhen(this.getAlteSyncInt(ALTE_RODSHEATHCOUNTER)>0,this.tickCount);
        }

    }
    public boolean areAnimationsBusy(){
        for(EntityDataAccessor<Integer> access: counters){
            if(this.getAlteSyncInt(access)>0){
                return true;
            }

        }
        return false;
    }
    public float getRodMod(){
        return (float)this.getSkillLevels()[2]/10 + 1;
    }
    public int getRodDuration(){
        return 400 + (int) (this.getRodMod()*100);
    }
    public int sparkcooldown=0;
    public int rodcooldown=12000;

    public int punishercooldown=0;
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        pCompound.putInt("juicecraft.alte.sparkcooldown",this.sparkcooldown);
        pCompound.putInt("juicecraft.alte.rodcooldown",this.getAlteSyncInt(ALTE_RODCOOLDOWN));
        pCompound.putInt("juicecraft.alte.punishercooldown",this.punishercooldown);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if(pCompound.contains("juicecraft.alte.rodcooldown")){
            this.setAlteRodCooldown(pCompound.getInt("juicecraft.alte.rodcooldown"));
        }
        this.sparkcooldown=pCompound.getInt("juicecraft.alte.sparkcooldown");
        this.punishercooldown=pCompound.getInt("juicecraft.alte.punishercooldown");
    }
    public final AnimationState sparkAnimState = new AnimationState();
    public final AnimationState rodSummonAnimState = new AnimationState();
    public final AnimationState rodSheathAnimState = new AnimationState();
    public final AnimationState punisherAnimState = new AnimationState();
    public final AnimationState hyperStartAnimState = new AnimationState();
    public final AnimationState hyperEndAnimState = new AnimationState();
    public final AnimationState hyperIdleAnimState = new AnimationState();
    public final AnimationState hyperShootAnimState = new AnimationState();
    public final AnimationState hyperWindupAnimState = new AnimationState();
    public int getAlteSyncInt(EntityDataAccessor<Integer> accessor){
        return this.getEntityData().get(accessor);
    }
    public void setAlteRodCooldown(int n){
        this.rodcooldown=n;
        this.setAlteSyncInt(ALTE_RODCOOLDOWN,n);
    }
    public void setAlteSyncInt(EntityDataAccessor<Integer> accessor, int n){
        this.getEntityData().set(accessor, n);
    }
    public void decrementAlteAnimCounter(EntityDataAccessor<Integer> accessor){
        int temp = this.getAlteSyncInt(accessor);
        if(temp>0){
            this.setAlteSyncInt(accessor, temp-1);
        }

    }
    public float getAlteLookAngle(EntityDataAccessor<Float> accessor){
        return this.getEntityData().get(accessor);
    }
    public void setAlteLookAngle(EntityDataAccessor<Float> accessor, float f){
        this.getEntityData().set(accessor, f);
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ALTE_SPARKCOUNTER,0);
        this.entityData.define(ALTE_RODSUMMONCOUNTER,0);
        this.entityData.define(ALTE_RODSHEATHCOUNTER,0);
        this.entityData.define(ALTE_PUNISHERCOUNTER,0);
        this.entityData.define(ALTE_HYPERSTARTCOUNTER,0);
        this.entityData.define(ALTE_HYPERENDCOUNTER,0);
        this.entityData.define(ALTE_HYPERWINDUPCOUNTER,0);
        this.entityData.define(ALTE_SPARKANGLEX, 0F);
        this.entityData.define(ALTE_SPARKANGLEY, 0F);
        this.entityData.define(ALTE_RODCOOLDOWN,12000);
    }
    public static final EntityDataAccessor<Integer> ALTE_RODCOOLDOWN = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> ALTE_SPARKANGLEY = SynchedEntityData.defineId(Alte.class,EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> ALTE_SPARKANGLEX = SynchedEntityData.defineId(Alte.class,EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Integer> ALTE_SPARKCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ALTE_RODSUMMONCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ALTE_RODSHEATHCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ALTE_PUNISHERCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ALTE_HYPERSTARTCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ALTE_HYPERWINDUPCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ALTE_HYPERENDCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final List<EntityDataAccessor<Integer>> counters = Arrays.asList(ALTE_SPARKCOUNTER, ALTE_RODSUMMONCOUNTER, ALTE_RODSHEATHCOUNTER, ALTE_PUNISHERCOUNTER, ALTE_HYPERENDCOUNTER, ALTE_HYPERSTARTCOUNTER, ALTE_HYPERWINDUPCOUNTER);
    public boolean additionalInspectConditions(){
        return this.getAlteSyncInt(ALTE_SPARKCOUNTER)<=0;
    }
    public boolean lockLookAround(){
        return this.getAlteSyncInt(ALTE_SPARKCOUNTER) <=0 && super.lockLookAround();
    }
    @Override
    int[] getSkillInfo() {
        return new int[]{1,2,3,3,4,5};
    }

    @Override
    void setInventoryRows() {
        this.invRows=4;
    }

    @Override
    void setArmorableModular() {
        this.isArmorable=true;
        this.isModular=false;
    }

    @Override
    void setName() {
        this.name="Alte";
    }

    @Override
    void setAggression() {
        this.aggression=60;
    }

    @Override
    void setCaptureDifficulty() {
        this.captureDifficulty=10;
    }

    @Override
    void setRecoveryDifficulty() {
        this.recoveryDifficulty=5;
    }

    @Override
    public SoundEvent getDeath() {
        return ALTE_DEATH.get();
    }

    @Override
    public SoundEvent getOnKill() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_WIN_0.get();
            case 1 -> ALTE_WIN_1.get();
            case 2 -> ALTE_WIN_2.get();
            default -> ALTE_WIN_3.get();
        };
    }

    @Override
    public SoundEvent getLaugh() {
        return ALTE_LAUGH.get();
    }

    @Override
    public SoundEvent getAngry() {
        return ALTE_ANGRY.get();
    }

    @Override
    public SoundEvent getFlee() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_FLEE_0.get();
            case 1 -> ALTE_FLEE_1.get();
            case 2 -> ALTE_FLEE_2.get();
            default -> ALTE_FLEE_3.get();
        };
    }

    @Override
    public SoundEvent getIdle() {
        if(this.sleepy() && this.animateSleep() && !this.getInSittingPose()){
            return null;
        }
        if(this.isAggressive()){
            return getBattle();
        }
        if(this.getHealth()<this.getMaxHealth()/2){
            return getInjured();
        }
        if(EnemyEvaluator.evaluateAreaDanger(this) > this.getFriendExperience() / 2){
            return getWarning();
        }
        int a=this.random.nextInt(5);
        if(a==3&&!this.level().isDay()){
            return ALTE_IDLE_NIGHT.get();
        }
        a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_IDLE_0.get();

            case 1 -> ALTE_IDLE_1.get();

            case 2 -> ALTE_IDLE_2.get();

            default -> ALTE_IDLE_3.get();
        };
    }

    @Override
    public SoundEvent getInjured() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_INJURED_0.get();
            case 1 -> ALTE_INJURED_1.get();
            case 2 -> ALTE_INJURED_2.get();
            default -> ALTE_INJURED_3.get();
        };
    }

    @Override
    public SoundEvent getInteract() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_INTERACT_0.get();
            case 1 -> ALTE_INTERACT_1.get();
            case 2 -> ALTE_INTERACT_2.get();
            default -> ALTE_INTERACT_3.get();
        };
    }

    @Override
    public SoundEvent getPat() {
        int a=this.random.nextInt(3);
        return switch (a) {
            case 0 -> ALTE_PAT_0.get();
            case 1 -> ALTE_PAT_1.get();
            default -> ALTE_PAT_2.get();
        };
    }

    @Override
    public SoundEvent getHurt(float dmg) {
        if(dmg>this.getHealth()*0.2)
        {
            return this.getFlee();
        }
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_HURT_0.get();
            case 1 -> ALTE_HURT_1.get();
            case 2 -> ALTE_HURT_2.get();
            default -> ALTE_HURT_3.get();
        };
    }

    @Override
    public SoundEvent getAttack() {
        int a=this.random.nextInt(5);
        return switch (a) {
            case 0 -> ALTE_ATTACK_0.get();
            case 1 -> ALTE_ATTACK_1.get();
            case 2 -> ALTE_ATTACK_2.get();
            case 3 -> ALTE_ATTACK_3.get();
            default -> ALTE_ATTACK_4.get();
        };
    }

    @Override
    public SoundEvent getEvade() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_EVADE_0.get();
            case 1 -> ALTE_EVADE_1.get();
            case 2 -> ALTE_EVADE_2.get();
            default -> ALTE_EVADE_3.get();
        };
    }

    @Override
    public SoundEvent getBattle() {
        int a=this.random.nextInt(8);
        return switch (a) {
            case 0 -> ALTE_BATTLE_0.get();
            case 1 -> ALTE_BATTLE_1.get();
            case 2 -> ALTE_BATTLE_2.get();
            case 3 -> ALTE_BATTLE_3.get();
            case 4 -> ALTE_BATTLE_4.get();
            case 5 -> ALTE_BATTLE_5.get();
            case 6 -> ALTE_BATTLE_6.get();
            default -> ALTE_BATTLE_7.get();
        };
    }

    @Override
    public SoundEvent getHyperEquip() {
        return ALTE_HYPER_EQUIP.get();
    }

    @Override
    public SoundEvent getHyperUse() {
        return ALTE_HYPER_USE.get();
    }

    @Override
    public SoundEvent getRecovery() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_RECOVERY_0.get();
            case 1 -> ALTE_RECOVERY_1.get();
            case 2 -> ALTE_RECOVERY_2.get();
            default -> ALTE_RECOVERY_3.get();
        };
    }

    @Override
    public SoundEvent getOnHeal() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_HEALING_0.get();
            case 1 -> ALTE_HEALING_1.get();
            case 2 -> ALTE_HEALING_2.get();
            default -> ALTE_HEALING_3.get();
        };
    }

    @Override
    public SoundEvent getRecoveryFail() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_RECOVERY_FAILED_0.get();
            case 1 -> ALTE_RECOVERY_FAILED_1.get();
            case 2 -> ALTE_RECOVERY_FAILED_2.get();
            default -> ALTE_RECOVERY_FAILED_3.get();
        };
    }

    @Override
    public SoundEvent getWarning() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_WARNING_0.get();
            case 1 -> ALTE_WARNING_1.get();
            default -> ALTE_WARNING_2.get();
        };
    }

    @Override
    public SoundEvent getEquip() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_EQUIP_0.get();
            case 1 -> ALTE_EQUIP_1.get();
            case 2 -> ALTE_EQUIP_2.get();
            default -> ALTE_EQUIP_3.get();
        };
    }

    @Override
    public SoundEvent getModuleEquip() {
        return ALTE_MODULE_EQUIP.get();
    }

    @Override
    public AbstractDialogueManager getDialogueManager() {
        return new AlteDialogueManager();
    }

    @Override
    void registerAdditionalGoals() {
        this.goalSelector.addGoal(5, new AlteSparkGoal(this));
        this.goalSelector.addGoal(5, new AlteShockRodGoal(this));
    }
}
