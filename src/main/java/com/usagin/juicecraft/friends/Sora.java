package com.usagin.juicecraft.friends;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.Init.sounds.UniversalSoundInit;
import com.usagin.juicecraft.ai.awareness.FriendDefense;
import com.usagin.juicecraft.ai.goals.sora.SoraBoisterousGoal;
import com.usagin.juicecraft.ai.goals.sora.SoraHyperGoal;
import com.usagin.juicecraft.ai.goals.sora.SoraShieldGoal;
import com.usagin.juicecraft.ai.goals.sora.SoraSlashThroughGoal;
import com.usagin.juicecraft.data.dialogue.AbstractDialogueManager;
import com.usagin.juicecraft.data.dialogue.SoraDialogueManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.slf4j.Logger;

import static com.usagin.juicecraft.Init.sounds.SoraSoundInit.*;
import static com.usagin.juicecraft.Init.sounds.UniversalSoundInit.COUNTER_BLOCK;


public class Sora extends OldWarFriend {
    private static final Logger LOGGER = LogUtils.getLogger();

    public Sora(EntityType<? extends FakeWolf> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.counters.add(SLASHTHROUGHCOUNTER);
        this.counters.add(CHARGECOUNTER);
    }
    public boolean shouldAfterImage(){
        int n = this.getSyncInt(SLASHTHROUGHCOUNTER);
        return (n <= 15 && n >= 6) || this.isBoisterous();
    }
    public AnimationState slashThroughAnimState = new AnimationState(), chargeAnimState = new AnimationState();
    public int shieldduration;
    public int shieldcooldown;
    public boolean usingshield = false;
    private int buffer=5;

    public void tick(){
        super.tick();
        if(this.level().isClientSide()){
            this.chargeAnimState.animateWhen(this.getSyncInt(CHARGECOUNTER)>0,this.tickCount);
            this.slashThroughAnimState.animateWhen(this.getSyncInt(SLASHTHROUGHCOUNTER)>0,this.tickCount);

        }else{
            if(this.getAttackCounter()==1 && this.isBoisterous()){
                if(buffer>0){
                    buffer--;
                    this.setAttackCounter(2);
                }
                else{
                    buffer=5;
                }
            }
            if(this.boisterouscooldown>0){
                this.boisterouscooldown--;
            }
            if(this.slashthroughcooldown>0){
                this.slashthroughcooldown--;
            }
            if(this.chargecooldown>0){
                this.chargecooldown--;
            }
            if(this.shieldcooldown>0){
                this.shieldcooldown--;
            }
        }
    }
    @Override
    public void swing(InteractionHand pHand, boolean pUpdateSelf) {
        if(this.getSyncBoolean(BOISTEROUS) && this.getAttackCounter() <= 0 && !this.getIsDying() && !this.isAttackLockedOut()){
            this.doBoisterousAttack();
        }else{
        super.swing(pHand, pUpdateSelf);}
    }
    public void setAttackCounter(int time) {
        if(this.isBoisterous()){
            this.attackCounter=time;
            this.setSyncInt(Friend.FRIEND_ATTACKCOUNTER,time);
        }else{
            super.setAttackCounter(time);
        }

    }
    public boolean shouldShowWeapon() {
        return !this.isUsingHyper();
    }
    public void doBoisterousAttack(){
        int rand = this.random.nextInt(3);
        if (rand == 0) {
            this.setAttackCounter(70);
            this.setAttackType(28);
        } else if (rand == 1) {
            this.setAttackCounter(85);
            this.setAttackType(25);
        } else {
            this.setAttackCounter(75);
            this.setAttackType(22);
        }
    }
    public boolean isCounter(){
        return this.getAttackType()==55;
    }
    public int getCombatModifier() {
        int n = 0;
        if(this.isBoisterous())
        {
            n=this.getSkillLevels()[4]*5;
        }
        return n+super.getCombatModifier();
    }
    public boolean forceShowWeapon(){
        return this.isBoisterous();
    }
    void doReleaseStart() {
        if(this.isBoisterous()){
            int n = this.getAttackCounter();
            if(this.getAttackType()==28){
                //65
                //55
                //40
                //23
                if(n==(int) (62 )){
                    this.doHurtTargetDetailed(25,2,1.5F, UniversalSoundInit.HEAVY_ATTACK.get());
                }else if(n==(int) (50 )){
                    this.doHurtTargetDetailed(45,1.5,1.2F,UniversalSoundInit.MEDIUM_ATTACK.get());
                }else if(n==(int) (35 )){
                    this.doHurtTargetDetailed(35,1.5,1.7F,UniversalSoundInit.MEDIUM_ATTACK.get());
                }else if(n==(int) (19 )){
                    this.doHurtTargetDetailed(120,3,4, UniversalSoundInit.HARBINGER_SLASH.get());
                }
            }else if(this.getAttackType()==25){
                //75
                //60
                //33
                if(n==(int) (75 )){
                    this.doHurtTargetDetailed(45,1.4,1.4F,UniversalSoundInit.MEDIUM_ATTACK.get());
                }else if(n==(int) (60 )){
                    this.doHurtTargetDetailed(35,1.5,1.7F,UniversalSoundInit.MEDIUM_ATTACK.get());
                }else if(n==(int) (28 )){
                    this.doHurtTargetDetailed(65,5,6, UniversalSoundInit.HARBINGER_SLAM.get());
                }
            }else if(this.getAttackType()==22){
                //60
                //45
                //36
                //27,23,21,17
                if(n==(int) (54 )){
                    this.doHurtTargetDetailed(45,1.5F,1.2F, UniversalSoundInit.MEDIUM_ATTACK.get());
                }else if(n==(int) (41 )){
                    this.doHurtTargetDetailed(45,1.5F,1.2F, UniversalSoundInit.MEDIUM_ATTACK.get());
                }else if(n==(int) (32)){
                    this.doHurtTargetDetailed(35,2F,1.5F, UniversalSoundInit.HEAVY_ATTACK.get());
                }else if(n==(int) (24 )||
                        n== (int) (20 ) ||
                        n==(int) (18 ) ||
                        n==(int) (14 )){
                    this.doHurtTargetDetailed(40,1.3F,0.9F, UniversalSoundInit.LIGHT_ATTACK.get());
                }

            }else if(this.getAttackType()==55){
                //7
                if(n==(int) (5 )){
                    this.doHurtTargetDetailed(30,2,2.5F, UniversalSoundInit.COUNTER_ATTACK.get());
                }
            }
        }else{
        super.doReleaseStart();}
    }
    public int getCounterTiming() {
        if(this.isBoisterous()){
            return (int) (8 );
        }else{
        return super.getCounterTiming();}
    }
    public void tryCounter(LivingAttackEvent event) {
        if(this.isBoisterous()){
            if(this.getRandom().nextBoolean()){
                event.setCanceled(true);
                this.playTimedVoice(this.getEvade());
                return;
            }
            if (event.getSource().getEntity() != null && !this.isDying && !this.isAttackLockedOut()) {
                if (this.getAttackType() == 55 && this.getAttackCounter() > this.getCounterTiming()) {
                    event.setCanceled(true);
                } else if (FriendDefense.shouldDefendAgainst(this)) {
                    this.setAttackCounter(20);
                    this.setAttackType(55);
                    this.setSyncBoolean(FLAGFORRESET,true);
                    this.playTimedVoice(this.getEvade());
                    this.playSound(COUNTER_BLOCK.get());
                    event.setCanceled(true);
                }
            }


        }else{
            super.tryCounter(event);
        }
    }
    public boolean isFearless(){
        return this.isBoisterous();
    }
    public boolean isBoisterous(){
        return this.getSyncBoolean(BOISTEROUS);
    }
    public boolean isAttackLockedOut() {
        return this.isUsingHyper() || this.areAnimationsBusy();
    }
    public boolean lockLookAround() {
        return this.areAnimationsBusy() && super.lockLookAround();
    }
    public int boisterouscooldown, boisterousduration, chargecooldown;
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("juicecraft.sora.slashthroughcooldown",this.slashthroughcooldown);
        pCompound.putInt("juicecraft.sora.shieldduration",this.shieldduration);
        pCompound.putInt("juicecraft.sora.shieldcooldown", this.shieldcooldown);
        pCompound.putBoolean("juicecraft.sora.usingshield", this.usingshield);
        pCompound.putBoolean("juicecraft.sora.boisterous", this.getSyncBoolean(BOISTEROUS));
        pCompound.putInt("juicecraft.sora.boisterouscooldown", this.boisterouscooldown);
        pCompound.putInt("juicecraft.sora.boisterousduration", this.boisterousduration);
        pCompound.putInt("juicecraft.sora.chargecooldown", this.chargecooldown);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.slashthroughcooldown=pCompound.getInt("juicecraft.sora.slashthroughcooldown");
        this.shieldduration = pCompound.getInt("juicecraft.sora.shieldduration");
        this.shieldcooldown = pCompound.getInt("juicecraft.sora.shieldcooldown");
        this.usingshield=pCompound.getBoolean("juicecraft.sora.usingshield");
        this.boisterouscooldown=pCompound.getInt("juicecraft.sora.boisterouscooldown");
        this.boisterousduration=pCompound.getInt("juicecraft.sora.boisterousduration");
        this.setSyncBoolean(BOISTEROUS, pCompound.getBoolean("juicecraft.sora.boisterous"));
        this.chargecooldown=pCompound.getInt("juicecraft.sora.chargecooldown");
    }
    public static AttributeSupplier.Builder getSoraAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 30).add(Attributes.MOVEMENT_SPEED, 0.35).add(Attributes.ATTACK_DAMAGE, 4);
    }
    public boolean shouldMoveLegs() {
        return !this.isUsingHyper() && this.getSyncInt(SLASHTHROUGHCOUNTER) <= 0;
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(SLASHTHROUGHCOUNTER,0);
        this.getEntityData().define(BOISTEROUS,false);
        this.getEntityData().define(CHARGECOUNTER,0);
    }
    public int slashthroughcooldown;
    public static final EntityDataAccessor<Integer> SLASHTHROUGHCOUNTER = SynchedEntityData.defineId(Sora.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> CHARGECOUNTER = SynchedEntityData.defineId(Sora.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> BOISTEROUS = SynchedEntityData.defineId(Sora.class, EntityDataSerializers.BOOLEAN);

    public boolean isUsingHyper(){
        return this.chargeAnimState.isStarted();
    }
    @Override
    int[] getSkillInfo() {
        return new int[]{1, 1, 2, 3, 4, 5};
    }

    @Override
    void setInventoryRows() {
        this.invRows = 5;
    }

    @Override
    void setArmorableModular() {
        this.isArmorable = true;
        this.isModular = false;
    }

    @Override
    void setName() {
        this.name = "Sora";
    }

    @Override
    void setAggression() {
        this.aggression = 50;
    }

    @Override
    void setCaptureDifficulty() {
        this.captureDifficulty = 10;
    }

    @Override
    void setRecoveryDifficulty() {
        this.recoveryDifficulty = 5;
    }

    @Override
    public SoundEvent getDeath() {
        return this.getHurt(100000);
    }

    @Override
    public SoundEvent getOnKill() {
        int a = this.random.nextInt(3);
        return switch (a) {
            case 0 -> SORA_WIN1.get();
            case 1 -> SORA_WIN2.get();
            default -> SORA_WIN3.get();
        };
    }

    @Override
    public SoundEvent getLaugh() {
        return SORA_PAT1.get();
    }

    @Override
    public SoundEvent getAngry() {
        return SORA_ANGRY.get();
    }

    @Override
    public SoundEvent getFlee() {
        return this.getHurt(1000000);
    }

    @Override
    public SoundEvent getIdle() {
        if (this.sleepy() && this.animateSleep() && !this.getInSittingPose()) {
            return null;
        }
        if (this.isAggressive()) {
            return getBattle();
        }
        if (this.getHealth() < this.getMaxHealth() / 2) {
            return getInjured();
        }
        int a = this.random.nextInt(10);
        if (a == 5 && !this.level().isDay()) {
            return SORA_IDLE_NIGHT1.get();
        }
        if (a == 4 && !this.level().isDay()) {
            return SORA_IDLE_NIGHT2.get();
        }
        a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_IDLE2.get();

            case 1 -> SORA_IDLE3.get();

            case 2 -> SORA_IDLE4.get();

            default -> SORA_IDLE1.get();
        };
    }

    @Override
    public SoundEvent getInjured() {
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_INJURED2.get();
            case 1 -> SORA_INJURED3.get();
            case 2 -> SORA_INJURED4.get();
            default -> SORA_INJURED1.get();
        };
    }

    @Override
    public SoundEvent getInteract() {
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_INTERACT2.get();
            case 1 -> SORA_INTERACT3.get();
            case 2 -> SORA_INTERACT4.get();
            default -> SORA_INTERACT1.get();
        };
    }

    @Override
    public SoundEvent getPat() {
        int a = this.random.nextInt(3);
        return switch (a) {
            case 0 -> SORA_PAT1.get();
            case 1 -> SORA_PAT2.get();
            default -> SORA_PAT3.get();
        };
    }

    @Override
    public SoundEvent getHurt(float dmg) {
        if (dmg > this.getHealth() * 0.2) {
            int a = this.random.nextInt(3);
            return switch (a) {
                case 0 -> SORA_GREATLYHURT1.get();
                case 1 -> SORA_GREATLYHURT2.get();
                default -> SORA_GREATLYHURT3.get();
            };
        }
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_HURT1.get();
            case 1 -> SORA_HURT2.get();
            case 2 -> SORA_HURT3.get();
            default -> SORA_HURT4.get();
        };
    }

    @Override
    public SoundEvent getAttack() {
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_ATTACK1.get();
            case 1 -> SORA_ATTACK2.get();
            case 2 -> SORA_ATTACK3.get();
            default -> SORA_ATTACK4.get();
        };
    }

    @Override
    public SoundEvent getEvade() {
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_EVADE1.get();
            case 1 -> SORA_EVADE2.get();
            case 2 -> SORA_EVADE3.get();
            default -> SORA_EVADE4.get();
        };
    }

    @Override
    public SoundEvent getBattle() {
        int a = this.random.nextInt(8);
        return switch (a) {
            case 0 -> SORA_BATTLE1.get();
            case 1 -> SORA_BATTLE2.get();
            case 2 -> SORA_BATTLE3.get();
            case 3 -> SORA_BATTLE4.get();
            case 4 -> SORA_BATTLE5.get();
            case 5 -> SORA_BATTLE6.get();
            case 6 -> SORA_BATTLE7.get();
            default -> SORA_BATTLE8.get();
        };
    }

    @Override
    public SoundEvent getHyperEquip() {
        return SORA_HYPEREQUIP.get();
    }

    @Override
    public SoundEvent getHyperUse() {
        return SORA_HYPERUSE.get();
    }

    @Override
    public SoundEvent getRecovery() {
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_RECOVERY1.get();
            case 1 -> SORA_RECOVERY2.get();
            case 2 -> SORA_RECOVERY3.get();
            default -> SORA_RECOVERY4.get();
        };
    }

    @Override
    public SoundEvent getOnHeal() {
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_HEAL2.get();
            case 1 -> SORA_HEAL3.get();
            case 2 -> SORA_HEAL4.get();
            default -> SORA_HEAL1.get();
        };
    }

    @Override
    public SoundEvent getRecoveryFail() {
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_RECOVERYFAILED1.get();
            case 1 -> SORA_RECOVERYFAILED2.get();
            case 2 -> SORA_RECOVERYFAILED3.get();
            default -> SORA_RECOVERYFAILED4.get();
        };
    }

    @Override
    public SoundEvent getWarning() {
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_WARNING1.get();
            case 1 -> SORA_WARNING2.get();
            case 2 -> SORA_WARNING3.get();
            default -> SORA_WARNING4.get();
        };
    }

    @Override
    public SoundEvent getEquip() {
        int a = this.random.nextInt(5);
        return switch (a) {
            case 0 -> SORA_EQUIP1.get();
            case 1 -> SORA_EQUIP2.get();
            case 2 -> SORA_EQUIP3.get();
            case 3 -> SORA_EQUIP4.get();
            default -> SORA_EQUIP5.get();
        };
    }

    @Override
    public SoundEvent getModuleEquip() {
        return SORA_MODULEEQUIP.get();
    }

    @Override
    public AbstractDialogueManager getDialogueManager() {
        return new SoraDialogueManager();
    }


    @Override
    void registerAdditionalGoals() {

        this.goalSelector.addGoal(1, new SoraBoisterousGoal(this));
        this.goalSelector.addGoal(5, new SoraSlashThroughGoal(this));
        this.goalSelector.addGoal(5, new SoraShieldGoal(this));
        this.goalSelector.addGoal(2, new SoraHyperGoal(this));
    }


}
