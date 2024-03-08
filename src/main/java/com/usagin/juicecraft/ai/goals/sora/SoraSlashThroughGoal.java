package com.usagin.juicecraft.ai.goals.sora;

import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.Init.sounds.UniversalSoundInit;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Sora;
import com.usagin.juicecraft.particles.AlteLightningParticle;
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

public class SoraSlashThroughGoal extends Goal {
    protected final Sora sora;
    protected LivingEntity target;
    float lookanglex;
    float lookangley;

    public SoraSlashThroughGoal(Sora sora) {
        this.sora = sora;
    }

    @Override
    public boolean canUse() {
        this.target = this.sora.getTarget();
        Item item = this.sora.getFriendWeapon().getItem();
        boolean flag = item instanceof BowItem || item instanceof SnowballItem || item instanceof CrossbowItem || this.sora.getAttackCounter() > 0;
        if (this.target == null) {
            return false;
        }
        return this.sora.getSkillEnabled()[1] && !this.sora.isUsingHyper() && this.sora.canDoThings() && this.sora.slashthroughcooldown <= 0 && this.sora.getPose() != Pose.SLEEPING && !this.sora.areAnimationsBusy() && !flag && this.sora.distanceTo(this.target) < 10;
    }

    @Override
    public boolean canContinueToUse() {
        Item item = this.sora.getFriendWeapon().getItem();
        boolean flag = item instanceof BowItem || item instanceof SnowballItem || item instanceof CrossbowItem;
        return this.sora.canDoThings() && this.sora.getSyncInt(Sora.SLASHTHROUGHCOUNTER) > 0 && !flag;
    }

    @Override
    public void start() {
        this.sora.getFriendNav().setShouldMove(false);
        this.sora.slashthroughcooldown = 400;
        this.sora.setSyncInt(Sora.SLASHTHROUGHCOUNTER, 25);
        this.target = this.findPriorityTarget();
        this.sora.setTarget(this.target);
        this.sora.setAggressive(true);
        this.sora.setInvulnerable(true);
        this.sora.lookAt(this.target, 360, 360);
        this.lookanglex = (float) Math.atan2(this.sora.getLookAngle().y, Math.sqrt(this.sora.getLookAngle().z * this.sora.getLookAngle().z + this.sora.getLookAngle().x * this.sora.getLookAngle().x));
        this.lookangley = (float) Math.atan2(this.sora.getLookAngle().z, this.sora.getLookAngle().x);
    }

    protected LivingEntity findPriorityTarget() {
        AABB box = this.sora.getBoundingBox().inflate(15);
        List<Entity> list = this.sora.level().getEntities(this.sora, box);
        LivingEntity finalTarget = this.target;
        for (Entity e : list) {
            if (e instanceof LivingEntity ent) {
                if (EnemyEvaluator.shouldDoHurtTarget(this.sora, ent)) {
                    if (this.sora.distanceTo(finalTarget) < this.sora.distanceTo(ent) && this.sora.distanceTo(ent) < 20) {
                        if (this.sora.level().clip(new ClipContext(this.sora.position(), ent.position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.sora)).getType() == HitResult.Type.MISS) {
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
        this.sora.getFriendNav().setShouldMove(true);
        this.sora.setAggressive(false);
        this.sora.setInvulnerable(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        int n = this.sora.getSyncInt(Sora.SLASHTHROUGHCOUNTER);
        if (this.sora.level() instanceof ServerLevel level) {
            if (n <= 15 && n >= 12) { //main charge
                this.sora.setDiscardFriction(true);
                if(n >14){
                    this.sora.playSound(UniversalSoundInit.COUNTER_ATTACK.get(), 0.5F, 1);
                    this.moveTowardsTarget(1.5F);
                }
                this.hurtAllTargets((n - 30F) / -8 + 1);
            } else if (n <12) { //recovery
                this.sora.setDiscardFriction(false);
                this.moveTowardsTarget(0.1F);
            }
        }
    }

    public void moveTowardsTarget(float speed) {
        float targetX = speed * (float) Math.cos(this.lookangley);
        float targetZ = speed * (float) Math.sin(this.lookangley);
        float targetY = speed * (float) Math.sin(this.lookanglex);
        this.sora.setDeltaMovement(this.sora.getDeltaMovement().add(targetX, targetY, targetZ));
    }

    public void hurtAllTargets(float knockbackmod) {
        AABB hitbox = this.sora.getBoundingBox().inflate(1);
        List<Entity> entityList = this.sora.level().getEntities(this.sora, hitbox);
        this.sora.lookAt(this.target, 60, 60);

        double angle = Math.atan2(this.sora.getLookAngle().z, this.sora.getLookAngle().x);
        angle = Math.toDegrees(angle);
        double maxFov = 90;
        for (Entity e : entityList) {
            if (e instanceof LivingEntity ent) {
                if (EnemyEvaluator.shouldDoHurtTarget(this.sora, ent)) {
                    double entityAngle = -Math.atan2(e.position().z - this.sora.position().z, e.position().x - this.sora.position().x);
                    entityAngle = Math.toDegrees(entityAngle);
                    if (Math.abs(Math.abs(angle) - Math.abs(entityAngle)) < maxFov) {
                        this.doHurtTarget(e, knockbackmod);
                    }
                }
            } else {
                double entityAngle = -Math.atan2(e.position().z - this.sora.position().z, e.position().x - this.sora.position().x);
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
            if (this.sora.distanceTo(pEntity) < 8) {
                float f = (0.020F * this.sora.getSkillLevels()[1] + 1) * (float) this.sora.getAttributeValue(Attributes.ATTACK_DAMAGE) * (Mth.clamp((5 * this.sora.getCombatMod() / 10) + this.sora.getRandom().nextInt(1, 7), 1, 6) + 3) / 6;
                if (pEntity.equals(this.target)) {
                    f *= 2;
                }

                float f1 = knockbackmod * (float) this.sora.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * (0.020F * this.sora.getSkillLevels()[1] + 1);
                if (pEntity instanceof LivingEntity) {
                    f += EnchantmentHelper.getDamageBonus(this.sora.getFriendWeapon(), ((LivingEntity) pEntity).getMobType());
                    f1 += (float) EnchantmentHelper.getKnockbackBonus(this.sora);
                }
                flag = pEntity.hurt(this.sora.damageSources().mobAttack(this.sora), f);
                if (flag) {
                    this.sora.setLastHurtMob(pEntity);
                    if (pEntity instanceof LivingEntity entity) {
                        entity.knockback(f1 * 0.5F, Mth.sin(this.sora.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.sora.getYRot() * ((float) Math.PI / 180F)));
                        int mod = 1 + (int) (4 * (1 + (float) this.sora.getSkillLevels()[1]) / (100 + (float) this.sora.getSkillLevels()[1]));
                        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * mod, mod), this.sora);
                    }

                }
            }
        }
    }
}
