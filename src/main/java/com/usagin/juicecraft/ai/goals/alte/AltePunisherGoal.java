package com.usagin.juicecraft.ai.goals.alte;

import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Alte;
import com.usagin.juicecraft.friends.Friend;
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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class AltePunisherGoal extends Goal {
    protected final Alte alte;
    protected LivingEntity target;

    public AltePunisherGoal(Alte alte) {
        this.alte = alte;
    }

    @Override
    public boolean canUse() {
        this.target = this.alte.getTarget();
        Item item = this.alte.getFriendWeapon().getItem();
        boolean flag = item instanceof BowItem || item instanceof SnowballItem || item instanceof CrossbowItem;
        return this.alte.getSkillEnabled()[3] && this.alte.getSkillEnabled()[2] && !this.alte.isUsingHyper() && this.alte.canDoThings() && this.alte.punishercooldown <= 0 && this.alte.getPose() != Pose.SLEEPING && this.target != null && !this.alte.areAnimationsBusy() && !flag;
    }

    protected LivingEntity findPriorityTarget() {
        AABB box = this.alte.getBoundingBox().inflate(15);
        List<Entity> list = this.alte.level().getEntities(this.alte, box);
        LivingEntity finalTarget = this.target;
        for (Entity e : list) {
            if (e instanceof LivingEntity ent) {
                if (EnemyEvaluator.shouldDoHurtTarget(this.alte, ent)) {
                    if (this.alte.distanceTo(finalTarget) < this.alte.distanceTo(ent)) {
                        if (this.alte.level().clip(new ClipContext(this.alte.position(), ent.position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.alte)).getType() == HitResult.Type.MISS) {
                            finalTarget = ent;
                        }
                    }
                }
            }
        }
        return finalTarget;
    }

    @Override
    public boolean canContinueToUse() {
        Item item = this.alte.getFriendWeapon().getItem();
        boolean flag = item instanceof BowItem || item instanceof SnowballItem || item instanceof CrossbowItem;
        return this.alte.canDoThings() && this.alte.getAlteSyncInt(Alte.ALTE_PUNISHERCOUNTER) > 0 && !flag;
    }

    @Override
    public void start() {
        this.alte.setAlteSyncInt(Alte.ALTE_PUNISHERCOUNTER, 65);
        this.target = this.findPriorityTarget();
        this.alte.setTarget(this.target);
        this.alte.setAggressive(true);
        this.alte.setInvulnerable(true);
    }

    @Override
    public void stop() {
        this.alte.setAggressive(false);
        this.alte.setInvulnerable(false);
    }

    @Override
    public void tick() {
        int n = this.alte.getAlteSyncInt(Alte.ALTE_PUNISHERCOUNTER);
        if (this.alte.level() instanceof ServerLevel level) {
            if (n == 42) { //unsheathe effects
                this.alte.spawnParticlesInSphereAtEntity(this.alte, 5, 2, 0, level, ParticleInit.ALTE_ENERGY_PARTICLE.get(), 0);
            } else if (n <= 30 && n >= 26) { //main charge
                this.hurtAllTargets((n-30F)/-5 + 1);
                this.alte.spawnParticlesInSphereAtEntity(this.alte, 3, 0.5F, 0, level, ParticleInit.ALTE_ENERGY_PARTICLE.get(), 0);
            } else if (n <= 26 && n >= 20) { //recovery
                if (n % 2 == 0) {
                    this.hurtAllTargets(0.5F);
                    this.alte.spawnParticlesInRandomSpreadAtEntity(this.alte, 5, 0.5F, 0, level, ParticleInit.ALTE_LIGHTNING_PARTICLE.get());
                }
            }
        }
    }

    public void hurtAllTargets(float knockbackmod) {
        AABB hitbox = this.alte.getBoundingBox().inflate(3);
        List<Entity> entityList = this.alte.level().getEntities(this.alte, hitbox);
        this.alte.lookAt(this.target, 60, 60);

        double angle = Math.atan2(this.alte.getLookAngle().z, this.alte.getLookAngle().x);
        angle = Math.toDegrees(angle);
        double maxFov = 90;
        for (Entity e : entityList) {
            if (e instanceof LivingEntity ent) {
                if (EnemyEvaluator.shouldDoHurtTarget(this.alte, ent)) {
                    double entityAngle = -Math.atan2(e.position().z - this.alte.position().z, e.position().x - this.alte.position().x);
                    entityAngle = Math.toDegrees(entityAngle);
                    if (Math.abs(Math.abs(angle) - Math.abs(entityAngle)) < maxFov) {
                        this.doHurtTarget(e, knockbackmod);
                    }
                }
            } else {
                double entityAngle = -Math.atan2(e.position().z - this.alte.position().z, e.position().x - this.alte.position().x);
                entityAngle = Math.toDegrees(entityAngle);
                if (Math.abs(Math.abs(angle) - Math.abs(entityAngle)) < maxFov) {
                    this.doHurtTarget(e, knockbackmod);
                }
            }
        }
    }

    public void doHurtTarget(Entity pEntity, float knockbackmod) {
        boolean flag;
        if (pEntity != null) {
            if (this.alte.distanceTo(pEntity) < 8) {
                float f = (0.020F * this.alte.getSkillLevels()[3] + 1) * (float) this.alte.getAttributeValue(Attributes.ATTACK_DAMAGE) * (Mth.clamp((5 * this.alte.getCombatMod() / 10) + this.alte.getRandom().nextInt(1, 7), 1, 6) + 3) / 6;
                flag = pEntity.hurt(this.alte.damageSources().mobAttack(this.alte), f);
                if (flag) {
                    this.alte.setLastHurtMob(pEntity);
                    if (pEntity instanceof LivingEntity entity) {
                        int mod = 1 + (int) (4 * (1 + (float) this.alte.getSkillLevels()[3]) / (100 + (float) this.alte.getSkillLevels()[3]));
                        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * mod, mod), this.alte);
                    }

                }
            }
        }
    }
}
