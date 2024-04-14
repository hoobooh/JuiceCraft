package com.usagin.juicecraft.friends;

import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.Init.sounds.UniversalSoundInit;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.ai.goals.alte.AlteHyperGoal;
import com.usagin.juicecraft.ai.goals.alte.AltePunisherGoal;
import com.usagin.juicecraft.ai.goals.alte.AlteShockRodGoal;
import com.usagin.juicecraft.ai.goals.alte.AlteSparkGoal;
import com.usagin.juicecraft.data.dialogue.AbstractDialogueManager;
import com.usagin.juicecraft.data.dialogue.alte.AlteDialogueManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import static com.usagin.juicecraft.Init.ParticleInit.ALTE_LIGHTNING_PARTICLE;
import static com.usagin.juicecraft.Init.ParticleInit.ALTE_SELFDESTRUCT_PARTICLE;
import static com.usagin.juicecraft.Init.sounds.AlteSoundInit.*;

public class Alte extends OldWarFriend {
    public static final EntityDataAccessor<Boolean> ALTE_SHOOTING = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> ALTE_USINGHYPER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> ALTE_RODCOOLDOWN = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> ALTE_SPARKANGLEY = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> ALTE_SPARKANGLEX = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Integer> ALTE_SPARKCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ALTE_RODSUMMONCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ALTE_RODSHEATHCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ALTE_PUNISHERCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ALTE_HYPERSTARTCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ALTE_HYPERWINDUPCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ALTE_HYPERRELAXCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ALTE_HYPERENDCOUNTER = SynchedEntityData.defineId(Alte.class, EntityDataSerializers.INT);
    public final AnimationState sparkAnimState = new AnimationState();
    public final AnimationState rodSummonAnimState = new AnimationState();
    public final AnimationState rodSheathAnimState = new AnimationState();
    public final AnimationState punisherAnimState = new AnimationState();
    public final AnimationState hyperStartAnimState = new AnimationState();
    public final AnimationState hyperEndAnimState = new AnimationState();
    public final AnimationState hyperIdleAnimState = new AnimationState();
    public final AnimationState hyperShootAnimState = new AnimationState();
    public final AnimationState hyperRelaxAnimState = new AnimationState();
    public final AnimationState hyperWindupAnimState = new AnimationState();
    public boolean usinghyper = false;
    public int selfdestructcooldown = 0;
    public int selfdestructtimer = 40;
    public boolean primed = false;
    public int sparkcooldown = 0;
    public int rodcooldown = 12000;
    public int punishercooldown = 0;
    public int hypermeter = 24000;

    public Alte(EntityType<? extends FakeWolf> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.counters.add(ALTE_SPARKCOUNTER);
        this.counters.add(ALTE_RODSUMMONCOUNTER);
        this.counters.add(ALTE_RODSHEATHCOUNTER);
        this.counters.add(ALTE_PUNISHERCOUNTER);
        this.counters.add(ALTE_HYPERENDCOUNTER);
        this.counters.add(ALTE_HYPERSTARTCOUNTER);
        this.counters.add(ALTE_HYPERWINDUPCOUNTER);
        this.counters.add(ALTE_HYPERRELAXCOUNTER);
    }

    public static AttributeSupplier.Builder getAlteAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 25).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.ATTACK_DAMAGE, 3).add(ForgeMod.SWIM_SPEED.get(), 3);
    }

    public float getAlteLookAngle(EntityDataAccessor<Float> accessor) {
        return this.getEntityData().get(accessor);
    }

    public void setAlteLookAngle(EntityDataAccessor<Float> accessor, float f) {
        this.getEntityData().set(accessor, f);
    }

    @Override
    int[] getSkillInfo() {
        return new int[]{1, 2, 3, 3, 4, 5};
    }

    public boolean hasShellWeapon() {
        return this.isUsingShockRod();
    }

    public boolean isUsingShockRod() {
        int n = this.getSyncInt(ALTE_RODCOOLDOWN);
        return ((n < this.getRodDuration() && n > 1) || (this.getSyncInt(ALTE_RODSHEATHCOUNTER) > 0) || (this.getSyncInt(ALTE_PUNISHERCOUNTER) > 0 && this.getSyncInt(ALTE_PUNISHERCOUNTER) < 60)) && !this.isUsingHyper();
    }

    public int getRodDuration() {
        return 400 + (int) (this.getRodMod() * 100);
    }

    public float getRodMod() {
        return (float) this.getSkillLevels()[2] / 10 + 1;
    }

    @Override
    public SoundEvent getHitSound() {
        if (this.isUsingShockRod()) {
            return UniversalSoundInit.CRITICAL_HIT.get();

        } else {
            return super.getHitSound();
        }
    }

    public float getGenericDamageMod() {
        if (this.isUsingShockRod()) {
            return this.getSkillLevels()[2] * 0.05F + 1;
        }
        return 1F;
    }

    public boolean shouldMoveLegs() {
        return !this.isUsingHyper() && this.getSyncInt(ALTE_PUNISHERCOUNTER) <= 0;
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (this.getAttackCounter() > 0 && this.isUsingShockRod()) {
            if (!this.level().isClientSide()) {
                this.playSound(UniversalSoundInit.ELECTRIC_STATIC.get(), 0.1F, 0.6F);
                this.spawnParticlesInSphereAtEntity(pEntity, 4, 2, 2, (ServerLevel) this.level(), ParticleInit.ALTE_ENERGY_PARTICLE.get(), 0);
                this.spawnParticlesInRandomSpreadAtEntity(pEntity, 4, 2, 2, (ServerLevel) this.level(), ParticleInit.ALTE_LIGHTNING_PARTICLE.get());
            }
            if (pEntity instanceof LivingEntity entity) {
                entity.setSecondsOnFire(entity.getRemainingFireTicks() + 10);
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, (int) this.getGenericDamageMod() + 1));
            }
        }
        return super.doHurtTarget(pEntity);
    }

    @Override
    void setInventoryRows() {
        this.invRows = 4;
    }

    @Override
    void setArmorableModular() {
        this.isArmorable = true;
        this.isModular = false;
    }

    @Override
    void setName() {
        this.name = "Alte";
    }

    @Override
    void setAggression() {
        this.aggression = 60;
    }

    @Override
    void setCaptureDifficulty() {
        this.captureDifficulty = 10;
    }

    @Override
    void setRecoveryDifficulty() {
        this.recoveryDifficulty = 5;
    }

    public void doDeathEvent() {
        if (this.selfdestructcooldown <= 0 && this.getSkillEnabled()[4] && this.hasActivator()) {
            this.doSelfDestructStart();
        } else {
            super.doDeathEvent();
        }
    }

    void doSelfDestructStart() {
        this.primed = true;
        this.playSound(ALTE_SELFDESTRUCT.get(), 1.5F, 1);
        if (this.level() instanceof ServerLevel level) {
            level.sendParticles(ALTE_SELFDESTRUCT_PARTICLE.get(), this.getX(), this.getEyeY(), this.getZ(), 1, 0, 0, 0, 1);
        }
    }

    @Override
    public SoundEvent getDeath() {
        return ALTE_DEATH.get();
    }

    @Override
    public SoundEvent getOnKill() {
        int a = this.random.nextInt(4);
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
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_FLEE_0.get();
            case 1 -> ALTE_FLEE_1.get();
            case 2 -> ALTE_FLEE_2.get();
            default -> ALTE_FLEE_3.get();
        };
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
        if (EnemyEvaluator.evaluateAreaDanger(this) > this.getFriendExperience() / 2) {
            return getWarning();
        }
        int a = this.random.nextInt(5);
        if (a == 3 && !this.level().isDay()) {
            return ALTE_IDLE_NIGHT.get();
        }
        a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_IDLE_0.get();

            case 1 -> ALTE_IDLE_1.get();

            case 2 -> ALTE_IDLE_2.get();

            default -> ALTE_IDLE_3.get();
        };
    }

    @Override
    public SoundEvent getInjured() {
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_INJURED_0.get();
            case 1 -> ALTE_INJURED_1.get();
            case 2 -> ALTE_INJURED_2.get();
            default -> ALTE_INJURED_3.get();
        };
    }

    @Override
    public SoundEvent getInteract() {
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_INTERACT_0.get();
            case 1 -> ALTE_INTERACT_1.get();
            case 2 -> ALTE_INTERACT_2.get();
            default -> ALTE_INTERACT_3.get();
        };
    }

    @Override
    public SoundEvent getPat() {
        int a = this.random.nextInt(3);
        return switch (a) {
            case 0 -> ALTE_PAT_0.get();
            case 1 -> ALTE_PAT_1.get();
            default -> ALTE_PAT_2.get();
        };
    }

    @Override
    public SoundEvent getHurt(float dmg) {
        if (dmg > this.getHealth() * 0.2) {
            return this.getFlee();
        }
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_HURT_0.get();
            case 1 -> ALTE_HURT_1.get();
            case 2 -> ALTE_HURT_2.get();
            default -> ALTE_HURT_3.get();
        };
    }

    @Override
    public SoundEvent getAttack() {
        int a = this.random.nextInt(5);
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
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_EVADE_0.get();
            case 1 -> ALTE_EVADE_1.get();
            case 2 -> ALTE_EVADE_2.get();
            default -> ALTE_EVADE_3.get();
        };
    }

    @Override
    public SoundEvent getBattle() {
        int a = this.random.nextInt(8);
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
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_RECOVERY_0.get();
            case 1 -> ALTE_RECOVERY_1.get();
            case 2 -> ALTE_RECOVERY_2.get();
            default -> ALTE_RECOVERY_3.get();
        };
    }

    @Override
    public SoundEvent getOnHeal() {
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_HEALING_0.get();
            case 1 -> ALTE_HEALING_1.get();
            case 2 -> ALTE_HEALING_2.get();
            default -> ALTE_HEALING_3.get();
        };
    }

    @Override
    public SoundEvent getRecoveryFail() {
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_RECOVERY_FAILED_0.get();
            case 1 -> ALTE_RECOVERY_FAILED_1.get();
            case 2 -> ALTE_RECOVERY_FAILED_2.get();
            default -> ALTE_RECOVERY_FAILED_3.get();
        };
    }

    @Override
    public SoundEvent getWarning() {
        int a = this.random.nextInt(4);
        return switch (a) {
            case 0 -> ALTE_WARNING_0.get();
            case 1 -> ALTE_WARNING_1.get();
            default -> ALTE_WARNING_2.get();
        };
    }

    @Override
    public SoundEvent getEquip() {
        int a = this.random.nextInt(4);
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

    public boolean shouldMoveLeftArm() {
        return this.getSyncInt(ALTE_RODSHEATHCOUNTER) <= 0 && super.shouldMoveLeftArm();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        pCompound.putInt("juicecraft.alte.hypermeter", this.hypermeter);
        pCompound.putInt("juicecraft.alte.sparkcooldown", this.sparkcooldown);
        pCompound.putInt("juicecraft.alte.rodcooldown", this.getSyncInt(ALTE_RODCOOLDOWN));
        pCompound.putInt("juicecraft.alte.punishercooldown", this.punishercooldown);
        pCompound.putInt("juicecraft.alte.selfdestructcooldown", this.selfdestructcooldown);
        pCompound.putBoolean("juicecraft.alte.usinghyper", this.getSyncBoolean(ALTE_USINGHYPER));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("juicecraft.alte.rodcooldown")) {
            this.setAlteRodCooldown(pCompound.getInt("juicecraft.alte.rodcooldown"));
        }
        this.hypermeter = pCompound.getInt("juicecraft.alte.hypermeter");
        this.sparkcooldown = pCompound.getInt("juicecraft.alte.sparkcooldown");
        this.punishercooldown = pCompound.getInt("juicecraft.alte.punishercooldown");
        this.selfdestructcooldown = pCompound.getInt("juicecraft.alte.selfdestructcooldown");
        this.setAlteUsingHyper(pCompound.getBoolean("juicecraft.alte.usinghyper"));
    }

    public void setAlteUsingHyper(boolean b) {
        this.usinghyper = b;
        this.setSyncBoolean(ALTE_USINGHYPER, b);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ALTE_SPARKCOUNTER, 0);
        this.entityData.define(ALTE_RODSUMMONCOUNTER, 0);
        this.entityData.define(ALTE_RODSHEATHCOUNTER, 0);
        this.entityData.define(ALTE_PUNISHERCOUNTER, 0);
        this.entityData.define(ALTE_HYPERSTARTCOUNTER, 0);
        this.entityData.define(ALTE_HYPERENDCOUNTER, 0);
        this.entityData.define(ALTE_HYPERWINDUPCOUNTER, 0);
        this.entityData.define(ALTE_SPARKANGLEX, 0F);
        this.entityData.define(ALTE_SPARKANGLEY, 0F);
        this.entityData.define(ALTE_RODCOOLDOWN, 12000);
        this.entityData.define(ALTE_USINGHYPER, false);
        this.entityData.define(ALTE_HYPERRELAXCOUNTER, 0);
        this.entityData.define(ALTE_SHOOTING, false);

    }

    @Override
    void registerAdditionalGoals() {
        this.goalSelector.addGoal(5, new AlteSparkGoal(this));
        this.goalSelector.addGoal(5, new AlteShockRodGoal(this));
        this.goalSelector.addGoal(5, new AltePunisherGoal(this));
        this.goalSelector.addGoal(5, new AlteHyperGoal(this));
    }

    public void setFriendInSittingPose(boolean sit) {
        if (!this.isUsingHyper()) {
            super.setFriendInSittingPose(sit);
        }
    }

    public boolean lockLookAround() {
        return this.areAnimationsBusy() && super.lockLookAround() && this.getSyncBoolean(ALTE_SHOOTING);
    }

    public boolean shouldShowWeapon() {
        return !this.isUsingHyper();
    }

    public boolean isUsingHyper() {
        return this.getSyncBoolean(ALTE_USINGHYPER) || (this.getSyncInt(ALTE_HYPERSTARTCOUNTER) > 0 && this.getSyncInt(ALTE_HYPERSTARTCOUNTER) < 110) || this.getSyncInt(ALTE_HYPERENDCOUNTER) > 0;
    }

    public boolean isAttackLockedOut() {
        return this.isUsingHyper() || this.areAnimationsBusy();
    }


    public boolean additionalInspectConditions() {
        return this.getSyncInt(ALTE_SPARKCOUNTER) <= 0;
    }

    public void tick() {
        super.tick();
        //LOGGER.info(this.getAlteSyncInt(ALTE_RODCOOLDOWN) +"");
        if (!this.level().isClientSide()) {
            if (this.selfdestructcooldown > 0) {
                this.selfdestructcooldown--;
            }
            if (this.sparkcooldown > 0) {
                this.sparkcooldown--;
            }
            if (this.getSyncInt(ALTE_RODCOOLDOWN) < 12000) {
                this.setAlteRodCooldown(this.getSyncInt(ALTE_RODCOOLDOWN) + 1);
            }
            if (this.primed) {
                if (this.selfdestructtimer <= 0) {
                    this.doSelfDestruct();
                } else {
                    if (this.tickCount % 3 == 0) {
                        this.spawnParticlesInRandomSpreadAtEntity(this, 3, 0.5F, 0, (ServerLevel) this.level(), ALTE_LIGHTNING_PARTICLE.get());
                    }
                    this.selfdestructtimer--;
                }
            }
            if (hypermeter < 24000) {
                hypermeter++;
            }
        } else {
            //LOGGER.info(this.getAlteSyncInt(ALTE_RODSHEATHCOUNTER) +"");
            this.sparkAnimState.animateWhen(this.getSyncInt(ALTE_SPARKCOUNTER) > 0, this.tickCount);
            this.rodSummonAnimState.animateWhen(this.getSyncInt(ALTE_RODSUMMONCOUNTER) > 0, this.tickCount);
            this.rodSheathAnimState.animateWhen(this.getSyncInt(ALTE_RODSHEATHCOUNTER) > 0, this.tickCount);
            this.punisherAnimState.animateWhen(this.getSyncInt(ALTE_PUNISHERCOUNTER) > 0, this.tickCount);
            this.hyperStartAnimState.animateWhen(this.getSyncInt(ALTE_HYPERSTARTCOUNTER) > 0, this.tickCount);
            this.hyperEndAnimState.animateWhen(this.getSyncInt(ALTE_HYPERENDCOUNTER) > 0, this.tickCount);
            this.hyperIdleAnimState.animateWhen(!this.isAggressive() && this.getSyncBoolean(ALTE_USINGHYPER) && !this.areAnimationsBusy(), this.tickCount);
            this.hyperWindupAnimState.animateWhen(this.getSyncInt(ALTE_HYPERWINDUPCOUNTER) > 0, this.tickCount);
            this.hyperRelaxAnimState.animateWhen(this.getSyncInt(ALTE_HYPERRELAXCOUNTER) > 0, this.tickCount);
            this.hyperShootAnimState.animateWhen(this.getSyncBoolean(ALTE_SHOOTING), this.tickCount);
        }

    }

    public void setAlteRodCooldown(int n) {
        this.rodcooldown = n;
        this.setSyncInt(ALTE_RODCOOLDOWN, n);
    }


    void doSelfDestruct() {
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 10 + this.getSkillLevels()[4], true, Level.ExplosionInteraction.MOB);
        this.selfdestructtimer = 40;
        this.primed = false;
        this.selfdestructcooldown = 24000;
        this.appendEventLog(Component.translatable("juicecraft.menu." + this.getFriendName().toLowerCase() + ".eventlog.closecall").getString());
        this.setHealth(this.getMaxHealth() / 2);
        this.deathCounter = 7 - recoveryDifficulty;
        this.getEntityData().set(FRIEND_ISDYING, false);
        this.isDying = false;
        this.playVoice(this.getRecovery());
        this.setInvulnerable(false);
        this.getFriendNav().setShouldMove(true);
    }
}
