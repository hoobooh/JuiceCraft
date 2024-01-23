package com.usagin.juicecraft.friends;

import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.ai.awareness.SkillManager;
import com.usagin.juicecraft.ai.goals.FriendMeleeAttackGoal;
import com.usagin.juicecraft.ai.goals.alte.AlteSparkGoal;
import com.usagin.juicecraft.data.dialogue.AbstractDialogueManager;
import com.usagin.juicecraft.data.dialogue.alte.AlteDialogueManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.usagin.juicecraft.Init.sounds.AlteSoundInit.*;
import static com.usagin.juicecraft.Init.sounds.SoraSoundInit.*;
import static com.usagin.juicecraft.Init.sounds.SoraSoundInit.SORA_HURT4;

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
        return false;
    }
    public boolean isAttackLockout(){
        return this.isUsingHyper() || this.getAlteAnimCounter(ALTE_SPARKCOUNTER)>0;
    }

    public void tick(){
        super.tick();
        if(!this.level().isClientSide()){
            if(this.sparkcooldown>0){
                this.sparkcooldown--;
            }
            for (EntityDataAccessor<Integer> counter : counters) {
                this.decrementAlteAnimCounter(counter);
            }
        }else{
            this.sparkAnimState.animateWhen(this.getAlteAnimCounter(ALTE_SPARKCOUNTER)>0,this.tickCount);
        }

    }
    public int sparkcooldown;
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("juicecraft.alte.sparkcooldown",this.sparkcooldown);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.sparkcooldown=pCompound.getInt("juicecraft.alte.sparkcooldown");
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
    public int getAlteAnimCounter(EntityDataAccessor<Integer> accessor){
        return this.getEntityData().get(accessor);
    }
    public void setAlteAnimCounter(EntityDataAccessor<Integer> accessor, int n){
        this.getEntityData().set(accessor, n);
    }
    public void decrementAlteAnimCounter(EntityDataAccessor<Integer> accessor){
        int temp = this.getAlteAnimCounter(accessor);
        if(temp>0){
            this.setAlteAnimCounter(accessor, temp-1);
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
    }
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
        return this.getAlteAnimCounter(ALTE_SPARKCOUNTER)<=0;
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
        if(this.sleeping() && this.animateSleep() && !this.getInSittingPose()){
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
    }
}
