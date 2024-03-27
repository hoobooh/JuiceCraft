package com.usagin.juicecraft.ai.goals.alte;

import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.Init.sounds.AlteSoundInit;
import com.usagin.juicecraft.Init.sounds.UniversalSoundInit;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class AltePunisherGoal extends Goal {
    protected final Alte alte;
    protected LivingEntity target;
    float lookanglex;
    float lookangley;

    public AltePunisherGoal(Alte alte) {
        this.alte = alte;
    }

    @Override
    public boolean canUse() {
        this.target = this.alte.getTarget();
        Item item = this.alte.getFriendWeapon().getItem();
        boolean flag = item instanceof BowItem || item instanceof SnowballItem || item instanceof CrossbowItem;
        if (this.target == null) {
            return false;
        }
        return this.alte.aggressiontimer > 60 && this.alte.getSkillEnabled()[3] && this.alte.getSkillEnabled()[2] && !this.alte.isUsingHyper() && this.alte.canDoThings() && this.alte.punishercooldown <= 0 && this.alte.getPose() != Pose.SLEEPING && !this.alte.areAnimationsBusy() && !flag && this.alte.distanceTo(this.target) < 20;
    }

    @Override
    public boolean canContinueToUse() {
        Item item = this.alte.getFriendWeapon().getItem();
        boolean flag = item instanceof BowItem || item instanceof SnowballItem || item instanceof CrossbowItem;
        return this.alte.canDoThings() && this.alte.getSyncInt(Alte.ALTE_PUNISHERCOUNTER) > 0 && !flag;
    }

    @Override
    public void start() {
        this.alte.playVoice(AlteSoundInit.ALTE_PUNISHER_VOICE.get());
        this.alte.getFriendNav().setShouldMove(false);
        this.alte.playSound(AlteSoundInit.ALTE_PUNISHER.get());
        this.alte.punishercooldown = 600;
        this.alte.setSyncInt(Alte.ALTE_PUNISHERCOUNTER, 65);
        this.target = this.findPriorityTarget();
        this.alte.setTarget(this.target);
        this.alte.setAggressive(true);
        this.alte.setInvulnerable(true);
        this.alte.lookAt(this.target, 360, 360);
        this.lookanglex = (float) Math.atan2(this.alte.getLookAngle().y, Math.sqrt(this.alte.getLookAngle().z * this.alte.getLookAngle().z + this.alte.getLookAngle().x * this.alte.getLookAngle().x));
        this.lookangley = (float) Math.atan2(this.alte.getLookAngle().z, this.alte.getLookAngle().x);
    }

    protected LivingEntity findPriorityTarget() {
        AABB box = this.alte.getBoundingBox().inflate(15);
        List<Entity> list = this.alte.level().getEntities(this.alte, box);
        LivingEntity finalTarget = this.target;
        for (Entity e : list) {
            if (e instanceof LivingEntity ent) {
                if (EnemyEvaluator.shouldDoHurtTarget(this.alte, ent)) {
                    if (this.alte.distanceTo(finalTarget) < this.alte.distanceTo(ent) && this.alte.distanceTo(ent) < 20) {
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
    public void stop() {
        this.alte.getFriendNav().setShouldMove(true);
        this.alte.setAggressive(false);
        this.alte.setInvulnerable(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        int n = this.alte.getSyncInt(Alte.ALTE_PUNISHERCOUNTER);
        if (this.alte.level() instanceof ServerLevel level) {


            if (n == 42) { //unsheathe effects
                this.alte.spawnParticlesInSphereAtEntity(this.alte, 5, 2, 0, level, ParticleInit.ALTE_ENERGY_PARTICLE.get(), 0);
            } else if (n <= 30 && n >= 26) { //main charge
                this.alte.setDiscardFriction(true);
                if(n > 29){
                    this.moveTowardsTarget(2);
                }
                this.hurtAllTargets((n - 30F) / -8 + 1);
                this.alte.spawnParticlesInSphereAtEntity(this.alte, 3, 0.5F, 0, level, ParticleInit.ALTE_ENERGY_PARTICLE.get(), 0);
            } else if (n <= 26 && n >= 20) { //recovery
                this.alte.setDiscardFriction(false);
                this.moveTowardsTarget(0.2F);
                if (n % 2 == 0) {
                    this.hurtAllTargets(0.5F);
                    this.alte.spawnParticlesInRandomSpreadAtEntity(this.alte, 5, 0.5F, 0, level, ParticleInit.ALTE_LIGHTNING_PARTICLE.get());
                }
            }
        }
    }

    public void moveTowardsTarget(float speed) {
        float targetX = speed * (float) Math.cos(this.lookangley);
        float targetZ = speed * (float) Math.sin(this.lookangley);
        float targetY = speed * (float) Math.sin(this.lookanglex);
        this.alte.setDeltaMovement(this.alte.getDeltaMovement().add(targetX, targetY, targetZ));
    }

    public void hurtAllTargets(float knockbackmod) {
        AABB hitbox = this.alte.getBoundingBox().inflate(1);
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
                float f = (0.040F * this.alte.getSkillLevels()[3] + 1) * (float) this.alte.getAttributeValue(Attributes.ATTACK_DAMAGE) * (Mth.clamp((5 * this.alte.getCombatMod() / 10) + this.alte.getRandom().nextInt(1, 7), 1, 6) + 3) / 6;
                if (pEntity.equals(this.target)) {
                    f *= 2;
                }

                float f1 = knockbackmod * (float) this.alte.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * (0.020F * this.alte.getSkillLevels()[3] + 1);
                if (pEntity instanceof LivingEntity) {
                    f += EnchantmentHelper.getDamageBonus(this.alte.getFriendWeapon(), ((LivingEntity) pEntity).getMobType());
                    f1 += (float) EnchantmentHelper.getKnockbackBonus(this.alte);
                }
                flag = pEntity.hurt(this.alte.damageSources().explosion(this.alte, this.alte), f);
                if (flag) {
                    pEntity.playSound(UniversalSoundInit.CRITICAL_HIT.get(), 0.1F, 1);
                    this.alte.setLastHurtMob(pEntity);
                    if (pEntity instanceof LivingEntity entity) {
                        entity.knockback(f1 * 0.5F, Mth.sin(this.alte.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.alte.getYRot() * ((float) Math.PI / 180F)));
                        int mod = 1 + (int) (4 * (1 + (float) this.alte.getSkillLevels()[3]) / (100 + (float) this.alte.getSkillLevels()[3]));
                        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * mod, mod), this.alte);
                    }

                }
            }
        }
    }
}
