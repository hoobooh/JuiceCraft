package com.usagin.juicecraft.ai.goals.alte;

import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Alte;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.client.renderer.entity.GuardianRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.phys.AABB;

import java.util.List;

import static com.usagin.juicecraft.Init.sounds.AlteSoundInit.ALTE_SPARK;
import static com.usagin.juicecraft.Init.sounds.UniversalSoundInit.ELECTRIC_STATIC;
import static com.usagin.juicecraft.Init.sounds.UniversalSoundInit.LASER_BLAST;
import static com.usagin.juicecraft.friends.Alte.*;
import static net.minecraft.core.particles.ParticleTypes.SWEEP_ATTACK;

public class AlteSparkGoal extends Goal {
    protected final Alte alte;
    protected LivingEntity target;

    public AlteSparkGoal(Alte alte) {
        this.alte = alte;
    }

    @Override
    public boolean canUse() {
        this.target = this.alte.getTarget();
        if (this.target == null) {
            return false;
        }
        return this.alte.canDoThings() && this.alte.getSkillEnabled()[1] && this.alte.sparkcooldown <= 0 && this.alte.distanceTo(this.target) < 4;
    }
    public boolean requiresUpdateEveryTick() {
        return true;
    }
    @Override
    public void start() {
        this.alte.playVoice(ALTE_SPARK.get());
        this.alte.lookAt(this.target, 360, 360);
        this.alte.playSound(ELECTRIC_STATIC.get());
        this.alte.setAlteLookAngle(ALTE_SPARKANGLEX, (float) Math.atan2(this.alte.getLookAngle().y, Math.sqrt(this.alte.getLookAngle().z * this.alte.getLookAngle().z + this.alte.getLookAngle().x * this.alte.getLookAngle().x)));

        this.alte.setAlteLookAngle(ALTE_SPARKANGLEY, (float) Math.atan2(this.alte.getLookAngle().z, this.alte.getLookAngle().x));


        this.alte.getFriendNav().setShouldMove(false);
        this.alte.sparkcooldown = 3600 - (int) (1800 * (1 + (float) this.alte.getSkillLevels()[1]) / (35 + (float) this.alte.getSkillLevels()[1]));
        this.alte.setAlteAnimCounter(ALTE_SPARKCOUNTER, 30);
    }

    @Override
    public boolean canContinueToUse() {
        return this.alte.canDoThings() && this.alte.getSkillEnabled()[1] && this.alte.getAlteAnimCounter(ALTE_SPARKCOUNTER) > 0;
    }

    @Override
    public void tick() {
        int n = this.alte.getAlteAnimCounter(ALTE_SPARKCOUNTER);
        if (n >= 5 && n <= 15) {
            if(n==15){
                this.alte.playSound(LASER_BLAST.get());
            }
            if (n % 4 == 0) {
                this.hurtAllTargets();
                if(this.alte.level() instanceof ServerLevel level){

                    this.spawnParticlesInSphere(10,0.5F,2,level, ParticleInit.ALTE_ENERGY_PARTICLE.get(),0);
                    this.spawnParticlesInRandomSpread(10,1,2,level, ParticleInit.ALTE_LIGHTNING_PARTICLE.get());
                }
            }
        }
    }
    public<T extends ParticleOptions> void spawnParticlesInRandomSpread(int count, float radius,float distance, ServerLevel sLevel, T type){
        float posX = (float) this.alte.getX();
        float posY = (float) this.alte.getY() + 1.2F;
        float posZ = (float) this.alte.getZ();
        float lookAngleY=this.alte.getAlteLookAngle(ALTE_SPARKANGLEY);
        float lookAngleX=this.alte.getAlteLookAngle(ALTE_SPARKANGLEX);

        float targetX = posX + distance * (float) Math.cos(lookAngleY);
        float targetZ = posZ + distance * (float) Math.sin(lookAngleY);
        float targetY = posY + distance * (float) Math.sin(lookAngleX);

        sLevel.sendParticles(type,targetX,targetY,targetZ,count,radius,radius,radius,1);
    }
    public<T extends ParticleOptions> void spawnParticlesInSphere(int count, float radius,float distance, ServerLevel sLevel, T type, float yOffset){
        if(count<1){
            return;
        }
        float posX = (float) this.alte.getX();
        float posY = (float) this.alte.getY() + 1.2F;
        float posZ = (float) this.alte.getZ();
        float lookAngleY=this.alte.getAlteLookAngle(ALTE_SPARKANGLEY);
        float lookAngleX=this.alte.getAlteLookAngle(ALTE_SPARKANGLEX);

        float targetX = posX + distance * (float) Math.cos(lookAngleY);
        float targetZ = posZ + distance * (float) Math.sin(lookAngleY);
        float targetY = posY + distance * (float) Math.sin(lookAngleX);



        for(int i = 0; i < count; i++){
            float x = (float) (Math.sin(i))/2*radius;
            float z = (float) (Math.cos(i))/2*radius;
            if(this.alte.getRandom().nextBoolean()){
                x=-x;
                z=-z;
            }
            sLevel.sendParticles(type,targetX + x,targetY + yOffset,targetZ + z,1,0,0,0,0.5);

        }

        this.spawnParticlesInSphere((int)(count*0.8), radius*0.8F,distance, sLevel, type,yOffset+0.3F);
        this.spawnParticlesInSphere((int)(count*0.8), radius*0.8F,distance, sLevel, type,yOffset-0.3F);

    }

    public void hurtAllTargets() {
        AABB hitbox = this.alte.getBoundingBox().inflate(4);
        List<Entity> entityList = this.alte.level().getEntities(this.alte, hitbox);
        this.alte.lookAt(this.target, 60, 60);

        double angle = Math.atan2(this.alte.getLookAngle().z, this.alte.getLookAngle().x);
        angle = Math.toDegrees(angle);
        double maxFov = 80;
        for (Entity e : entityList) {
            if (e instanceof LivingEntity ent) {
                if (EnemyEvaluator.shouldDoHurtTarget(this.alte, ent)) {
                    double entityAngle = -Math.atan2(e.position().z - this.alte.position().z, e.position().x - this.alte.position().x);
                    entityAngle = Math.toDegrees(entityAngle);
                    if (Math.abs(Math.abs(angle) - Math.abs(entityAngle)) < maxFov) {
                        this.doHurtTarget(e);
                    }
                }
            } else {
                double entityAngle = -Math.atan2(e.position().z - this.alte.position().z, e.position().x - this.alte.position().x);
                entityAngle = Math.toDegrees(entityAngle);
                if (Math.abs(Math.abs(angle) - Math.abs(entityAngle)) < maxFov) {
                    this.doHurtTarget(e);
                }
            }
        }
    }

    public void doHurtTarget(Entity pEntity) {
        boolean flag;
        if (pEntity != null) {
            if (this.alte.distanceTo(pEntity) < 8) {
                float f = 4 * (1 + (float) this.alte.getSkillLevels()[1]) / (20 + (float) this.alte.getSkillLevels()[1]) * (float) this.alte.getAttributeValue(Attributes.ATTACK_DAMAGE) * (Mth.clamp((5 * this.alte.getCombatMod() / 10) + this.alte.getRandom().nextInt(1, 7), 1, 6) + 3) / 6;
                flag = pEntity.hurt(this.alte.damageSources().mobAttack(this.alte), f);
                if (flag) {
                    this.alte.setLastHurtMob(pEntity);
                    if (pEntity instanceof LivingEntity entity) {
                        int mod = 1 + (int) (4 * (1 + (float) this.alte.getSkillLevels()[1]) / (100 + (float) this.alte.getSkillLevels()[1]));
                        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * mod, mod), this.alte);
                    }

                }
            }
        }
    }

    @Override
    public void stop() {
        this.target = null;
        this.alte.getFriendNav().setShouldMove(true);
    }
}
