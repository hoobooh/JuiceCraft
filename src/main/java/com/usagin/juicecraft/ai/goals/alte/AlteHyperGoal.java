package com.usagin.juicecraft.ai.goals.alte;

import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Alte;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.phys.AABB;

import java.util.List;

import static com.usagin.juicecraft.Init.ParticleInit.ALTE_ENERGY_PARTICLE;
import static com.usagin.juicecraft.Init.ParticleInit.ALTE_LIGHTNING_PARTICLE;
import static com.usagin.juicecraft.friends.Alte.*;

public class AlteHyperGoal extends Goal {
    private final Alte alte;
    protected LivingEntity target;

    public AlteHyperGoal(Alte alte) {
        this.alte = alte;
    }
    public boolean requiresUpdateEveryTick(){
        return true;
    }
    @Override
    public boolean canUse() {
        if(this.alte.isUsingHyper()){
            return true;
        }
        this.target = this.alte.getTarget();
        if (this.target == null) {
            return false;
        }
        //LOGGER.info(this.alte.getSyncInt(ALTE_HYPERSTARTCOUNTER) + " START " + this.alte.getSyncInt(ALTE_HYPERENDCOUNTER) +"");
        return (this.alte.getSkillEnabled()[5] && !this.alte.isUsingHyper() && this.alte.canDoThings() && this.alte.hypermeter>=24000 && this.alte.getPose() != Pose.SLEEPING && !this.alte.areAnimationsBusy());
    }

    @Override
    public boolean canContinueToUse() {
        return (this.alte.hypermeter > 10 && this.alte.canDoThings()) ||
                this.alte.getSyncInt(ALTE_HYPERWINDUPCOUNTER) > 0 || this.alte.getSyncInt(ALTE_HYPERRELAXCOUNTER) > 0;
    }

    @Override
    public void start() {
        this.alte.getFriendNav().setShouldMove(false);
        this.alte.setInvulnerable(true);
        if (!this.alte.isUsingHyper()) {
            this.alte.setSyncInt(ALTE_HYPERSTARTCOUNTER, 120);
            //this.alte.playSound(ALTE_HYPERSTART.get());
        }
    }

    @Override
    public void stop() {
        this.alte.setInvulnerable(false);
        this.alte.setAggressive(false);
        this.alte.getFriendNav().setShouldMove(true);
        //this.alte.setSyncInt(ALTE_HYPERENDCOUNTER, 60);
        this.alte.setAlteUsingHyper(false);
        //this.alte.playSound(ALTE_HYPEREND.get());
    }

    public boolean hadTarget = false;
    public void shootMiniguns(){
        Snowball snowball = new Snowball(this.alte.level(), this.alte);
        snowball.setNoGravity(true);
        float targetradius=2;
        float lookAngleX=(float) Math.atan2(this.alte.getLookAngle().y, Math.sqrt(this.alte.getLookAngle().z * this.alte.getLookAngle().z + this.alte.getLookAngle().x * this.alte.getLookAngle().x));
        float lookAngleY=(float) Math.atan2(this.alte.getLookAngle().z, this.alte.getLookAngle().x);
        float targetX = (float) (this.alte.getX() + targetradius * (float) Math.cos(lookAngleY));
        float targetZ = (float) (this.alte.getZ() + targetradius * (float) Math.sin(lookAngleY));
        float targetY = (float) (this.alte.getEyeY() + targetradius * (float) Math.sin(lookAngleX));
        double d1 = targetX - this.alte.getX();
        double d2 = targetY - snowball.getY();
        double d3 = targetZ - this.alte.getZ();
        //double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double)0.2F;
        snowball.shoot(d1, d2, d3, 1.6F, 12.0F);
        this.alte.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 1);
        this.alte.level().addFreshEntity(snowball);
    }
    public void shootPanels(){

    }
    public boolean markforstart=false;
    @Override
    public void tick() {
        this.target = this.alte.getTarget();
        this.alte.hypermeter-=this.getHyperCost()+1;

        if (this.alte.getSyncBoolean(ALTE_USINGHYPER)) {
            if(this.markforstart){this.markforstart=false;}else{
            if (this.target != null && this.alte.hypermeter > 70) {
                this.alte.lookAt(this.target, 30, 30);
                if (this.alte.getSyncBoolean(ALTE_SHOOTING)) {
                    this.alte.setAggressive(true);
                    this.shootMiniguns();
                    this.shootPanels();
                } else {
                    this.alte.setAggressive(false);
                    if (this.alte.getSyncInt(ALTE_HYPERWINDUPCOUNTER) <= 0 && this.alte.getSyncInt(ALTE_HYPERRELAXCOUNTER) <= 0) {
                        this.alte.setSyncInt(ALTE_HYPERWINDUPCOUNTER, 20);
                        //this.alte.playSound(ALTE_HYPER_WINDUP.get());
                    } else if (this.alte.getSyncInt(ALTE_HYPERWINDUPCOUNTER) == 1) {
                        this.alte.setSyncBoolean(ALTE_SHOOTING,true);
                    }
                }
                this.hadTarget = true;
            } else {
                if(this.alte.hypermeter<70 && this.alte.getSyncInt(ALTE_HYPERENDCOUNTER) <=0){
                    this.alte.setSyncInt(ALTE_HYPERENDCOUNTER, 60);
                }
                else if (this.alte.getSyncBoolean(ALTE_SHOOTING)) {
                    this.alte.setAggressive(false);
                    this.alte.setSyncBoolean(ALTE_SHOOTING,false);
                    if (this.alte.getSyncInt(ALTE_HYPERRELAXCOUNTER) <= 0) {
                        this.alte.setSyncInt(ALTE_HYPERRELAXCOUNTER, 20);
                        //this.alte.playSound(ALTE_HYPER_RELAX.get());
                    }
                }
            }}
        }
        else {
            int n = this.alte.getSyncInt(ALTE_HYPERSTARTCOUNTER);
            if (n > 0) {
                if (n == 1) {
                    this.alte.setAlteUsingHyper(true);
                    this.markforstart=true;
                } else {
                    if (n == 24) {
                        AABB knockback = this.alte.getBoundingBox().inflate(4);
                        List<Entity> list = this.alte.level().getEntities(this.alte, knockback);
                        for (Entity entity : list) {
                            if (entity instanceof LivingEntity ent) {
                                if (EnemyEvaluator.shouldDoHurtTarget(this.alte, ent)) {
                                    ent.knockback(3, Mth.sin(this.alte.getYRot() * ((float) Math.PI / 180F)), (-Mth.cos(this.alte.getYRot() * ((float) Math.PI / 180F))));
                                    ent.hurt(this.alte.damageSources().mobAttack(this.alte), 0.1F);
                                    ent.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 3));
                                    if (this.alte.level() instanceof ServerLevel level) {
                                        this.alte.spawnParticlesInRandomSpreadAtEntity(ent, 3, 0.5F, 0, level, ALTE_LIGHTNING_PARTICLE.get());
                                    }
                                }
                            }
                        }
                        if (this.alte.level() instanceof ServerLevel level) {
                            this.alte.spawnParticlesInSphereAtEntity(this.alte, 5, 1, 0, level, ALTE_ENERGY_PARTICLE.get(), 0);
                        }
                    }
                }
            }
        }
    }

    public float getHyperMod() {
        return this.alte.getSkillLevels()[5] + 1;
    }

    public int getHyperCost() {
        float x = this.getHyperMod();
        return (int) ((11 - (x * 10) / (x + 10)) * 4);
    }
}
