package com.usagin.juicecraft.ai.goals.alte;

import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Alte;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
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

    @Override
    public boolean canUse() {
        this.target = this.alte.getTarget();
        if (this.target == null) {
            return false;
        }
        return (this.alte.getSkillEnabled()[5] && !this.alte.isUsingHyper() && this.alte.canDoThings() && !this.alte.isActivatorCharging() && this.alte.getPose() != Pose.SLEEPING && !this.alte.areAnimationsBusy()) || this.alte.isUsingHyper();
    }

    @Override
    public boolean canContinueToUse() {
        return (this.alte.inventory.getItem(0).getDamageValue() > 10 && this.alte.canDoThings()) || this.alte.getSyncInt(ALTE_HYPERWINDUPCOUNTER) > 0 || this.alte.getSyncInt(ALTE_HYPERRELAXCOUNTER) > 0;
    }

    @Override
    public void start() {
        this.alte.getFriendNav().setShouldMove(false);
        this.alte.setInvulnerable(true);
        if (!this.alte.isUsingHyper()) {
            this.alte.setSyncInt(ALTE_HYPERSTARTCOUNTER, 120);
            this.alte.playSound(ALTE_HYPERSTART.get());
        }
    }

    @Override
    public void stop() {
        this.alte.setInvulnerable(false);
        this.alte.getFriendNav().setShouldMove(true);
        this.alte.setSyncInt(ALTE_HYPERENDCOUNTER, 60);
        this.alte.setAlteUsingHyper(false);
        this.alte.playSound(ALTE_HYPEREND.get());
    }

    public boolean hadTarget = false;

    @Override
    public void tick() {
        this.target = this.alte.getTarget();
        this.alte.inventory.getItem(0).setDamageValue(this.alte.inventory.getItem(0).getDamageValue() - this.getHyperCost() - 1);

        if (this.alte.isUsingHyper()) {
            if (this.target != null) {
                this.alte.lookAt(this.target, 30, 30);
                if (this.alte.shooting) {
                    //spawn bullets
                } else {
                    if (this.alte.getSyncInt(ALTE_HYPERWINDUPCOUNTER) <= 0 && this.alte.getSyncInt(ALTE_HYPERRELAXCOUNTER) <= 0) {
                        this.alte.setSyncInt(ALTE_HYPERSTARTCOUNTER, 20);
                        this.alte.playSound(ALTE_HYPER_WINDUP.get());
                    } else if (this.alte.getSyncInt(ALTE_HYPERWINDUPCOUNTER) == 1) {
                        this.alte.shooting = true;
                    }
                }
                this.hadTarget = true;
            } else {
                if (this.alte.shooting) {
                    this.alte.shooting = false;
                    if (this.alte.getSyncInt(ALTE_HYPERRELAXCOUNTER) <= 0) {
                        this.alte.setSyncInt(ALTE_HYPERRELAXCOUNTER, 60);
                        this.alte.playSound(ALTE_HYPER_RELAX.get());
                    }
                }
            }
        }

        int n = this.alte.getSyncInt(ALTE_HYPERSTARTCOUNTER);
        if (n > 0) {
            if (n == 1) {
                this.alte.setAlteUsingHyper(true);
            } else {
                if (n == 24) {
                    AABB knockback = this.alte.getBoundingBox().inflate(4);
                    List<Entity> list = this.alte.level().getEntities(this.alte, knockback);
                    for (Entity entity : list) {
                        if (entity instanceof LivingEntity ent) {
                            if (EnemyEvaluator.shouldDoHurtTarget(this.alte, ent)) {
                                ent.knockback(3, Mth.sin(this.alte.getYRot() * ((float) Math.PI / 180F)), (-Mth.cos(this.alte.getYRot() * ((float) Math.PI / 180F))));
                                ent.hurt(this.alte.damageSources().mobAttack(this.alte), 0.1F);
                                ent.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,3));
                                if (this.alte.level() instanceof ServerLevel level) {
                                    this.alte.spawnParticlesInRandomSpreadAtEntity(ent,3,0.5F,0,level, ALTE_LIGHTNING_PARTICLE.get());
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

    public float getHyperMod() {
        return this.alte.getSkillLevels()[5] + 1;
    }

    public int getHyperCost() {
        float x = this.getHyperMod();
        return (int) (11 - (x * 10) / (x + 10) * 4);
    }
}
