package com.usagin.juicecraft.ai.goals.alte;

import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.Init.sounds.AlteSoundInit;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Alte;
import com.usagin.juicecraft.projectiles.AlteMinigunProjectile;
import com.usagin.juicecraft.projectiles.AltePanelProjectile;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;

import java.util.List;

import static com.usagin.juicecraft.Init.ParticleInit.ALTE_ENERGY_PARTICLE;
import static com.usagin.juicecraft.Init.ParticleInit.ALTE_LIGHTNING_PARTICLE;
import static com.usagin.juicecraft.Init.sounds.AlteSoundInit.*;
import static com.usagin.juicecraft.friends.Alte.*;

public class AlteHyperGoal extends Goal {
    private final Alte alte;
    public boolean hadTarget = false;
    public boolean markforstart = false;
    protected LivingEntity target;
    int panelcooldown = 0;

    public AlteHyperGoal(Alte alte) {
        this.alte = alte;
    }

    @Override
    public boolean canUse() {
        if (this.alte.isUsingHyper()) {
            return true;
        }
        this.target = this.alte.getTarget();
        if (this.target == null || !this.alte.getCombatSettings().getHyperCondition(this.alte)) {
            return false;
        }
        return this.alte.aggressiontimer > 80 && (this.alte.getSkillEnabled()[5] && !this.alte.isUsingHyper() && this.alte.canDoThings() && this.alte.hypermeter >= 24000 && this.alte.getPose() != Pose.SLEEPING && !this.alte.areAnimationsBusy());
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
            this.alte.playSound(ALTE_HYPERSTART.get());
        }
    }

    @Override
    public void stop() {
        this.alte.setInvulnerable(false);
        this.alte.setAggressive(false);
        this.alte.getFriendNav().setShouldMove(true);
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.alte.getSyncInt(ALTE_HYPERENDCOUNTER) == 1) {
            this.alte.setAlteUsingHyper(false);
        }
        this.alte.synchronizeLookAngle();
        this.target = this.alte.getTarget();
        this.alte.hypermeter -= this.getHyperCost() + 1;
        if (this.alte.getSyncBoolean(ALTE_USINGHYPER)) {
            if (this.markforstart) {
                this.markforstart = false;
            } else {
                if (this.target != null && this.alte.hypermeter > 70) {
                    this.alte.getLookControl().setLookAt(this.target);
                    this.alte.lookAt(this.target, 10, 10);
                    this.alte.alignBodyWithHeadAngle();
                    if (this.alte.getSyncBoolean(ALTE_SHOOTING)) {
                        this.alte.setAggressive(true);
                        this.shootMiniguns();
                        this.shootPanels();
                        this.panelcooldown++;
                    } else {
                        this.panelcooldown = 0;
                        this.alte.setAggressive(false);
                        if (this.alte.getSyncInt(ALTE_HYPERWINDUPCOUNTER) <= 0 && this.alte.getSyncInt(ALTE_HYPERRELAXCOUNTER) <= 0) {
                            this.alte.setSyncInt(ALTE_HYPERWINDUPCOUNTER, 20);
                            this.alte.playSound(ALTE_HYPERWINDUP.get());
                        } else if (this.alte.getSyncInt(ALTE_HYPERWINDUPCOUNTER) == 1) {
                            this.alte.setSyncBoolean(ALTE_SHOOTING, true);
                        }
                    }
                    this.hadTarget = true;
                } else {
                    if (this.alte.hypermeter < 70 && this.alte.getSyncInt(ALTE_HYPERENDCOUNTER) <= 0) {
                        this.alte.setSyncInt(ALTE_HYPERENDCOUNTER, 60);
                        this.alte.playSound(ALTE_HYPEREND.get());
                    } else if (this.alte.getSyncBoolean(ALTE_SHOOTING)) {
                        this.alte.setAggressive(false);
                        this.alte.setSyncBoolean(ALTE_SHOOTING, false);
                        if (this.alte.getSyncInt(ALTE_HYPERRELAXCOUNTER) <= 0) {
                            this.alte.setSyncInt(ALTE_HYPERRELAXCOUNTER, 20);
                            this.alte.playSound(ALTE_HYPERRELAX.get());
                        }
                    }
                }
            }
        } else {
            int n = this.alte.getSyncInt(ALTE_HYPERSTARTCOUNTER);
            if (n > 0) {
                if (n == 1) {
                    this.alte.setAlteUsingHyper(true);
                    this.markforstart = true;
                } else {
                    if (n == 96) {
                        this.shockwave();
                    }
                }
            }
        }
    }

    public int getHyperCost() {
        float x = this.getHyperMod();
        return (int) ((11 - (x * 10) / (x + 10)) * 4);
    }

    public void shootMiniguns() {
        this.alte.playSound(ALTE_HYPERMINIGUNSPIN.get());
        if (this.alte.tickCount % 2 == 0) {
            this.alte.playSound(AlteSoundInit.ALTE_MINIGUN.get());
        }

        float targetradius = 1.5F;
        float lookAngleX = (float) Math.atan2(this.alte.getLookAngle().y, Math.sqrt(this.alte.getLookAngle().z * this.alte.getLookAngle().z + this.alte.getLookAngle().x * this.alte.getLookAngle().x));
        float lookAngleY = (float) Math.atan2(this.alte.getLookAngle().z, this.alte.getLookAngle().x);
        float mini1dist = 0.7F;
        float originX1 = (float) (this.alte.getX() + mini1dist * (float) Math.cos(lookAngleY + Math.PI / 2));
        float originZ1 = (float) (this.alte.getZ() + mini1dist * (float) Math.sin(lookAngleY + Math.PI / 2));
        float originY1 = (float) (this.alte.getEyeY() - 0.55F + mini1dist * (float) Math.sin(lookAngleX));

        originX1 = originX1 + targetradius * (float) Math.cos(lookAngleY);
        originZ1 = originZ1 + targetradius * (float) Math.sin(lookAngleY);
        originY1 = originY1 + targetradius * (float) Math.sin(lookAngleX);


        AlteMinigunProjectile snowball = new AlteMinigunProjectile(this.alte, originX1, originY1, originZ1);
        snowball.setNoGravity(true);
        if (this.alte.level() instanceof ServerLevel level && this.alte.tickCount % 2 == 0) {
            level.sendParticles(ParticleInit.ALTE_GUNFLASH.get(), originX1, originY1, originZ1, 1, 0, 0, 0, 0);
        }
        float targetX1 = originX1 + 1 * (float) Math.cos(lookAngleY);
        float targetZ1 = originZ1 + 1 * (float) Math.sin(lookAngleY);
        float targetY1 = originY1 + 1 * (float) Math.sin(lookAngleX);
        double d1 = targetX1 - originX1;
        double d2 = targetY1 - snowball.getY();
        double d3 = targetZ1 - originZ1;
        snowball.shoot(d1, d2, d3, 1.6F, 12.0F);
        this.alte.level().addFreshEntity(snowball);

        originX1 = (float) (this.alte.getX() + mini1dist * (float) Math.cos(lookAngleY - Math.PI / 2));
        originZ1 = (float) (this.alte.getZ() + mini1dist * (float) Math.sin(lookAngleY - Math.PI / 2));
        originY1 = (float) (this.alte.getEyeY() - 0.55F + mini1dist * (float) Math.sin(lookAngleX));

        originX1 = originX1 + targetradius * (float) Math.cos(lookAngleY);
        originZ1 = originZ1 + targetradius * (float) Math.sin(lookAngleY);
        originY1 = originY1 + targetradius * (float) Math.sin(lookAngleX);

        snowball = new AlteMinigunProjectile(this.alte, originX1, originY1, originZ1);
        snowball.setNoGravity(true);
        if (this.alte.level() instanceof ServerLevel level && this.alte.tickCount % 2 == 0) {
            level.sendParticles(ParticleInit.ALTE_GUNFLASH.get(), originX1, originY1, originZ1, 1, 0, 0, 0, 0);
        }
        targetX1 = originX1 + 1 * (float) Math.cos(lookAngleY);
        targetZ1 = originZ1 + 1 * (float) Math.sin(lookAngleY);
        targetY1 = originY1 + 1 * (float) Math.sin(lookAngleX);
        d1 = targetX1 - originX1;
        d2 = targetY1 - snowball.getY();
        d3 = targetZ1 - originZ1;
        snowball.shoot(d1, d2, d3, 1.6F, 12.0F);
        this.alte.level().addFreshEntity(snowball);

    }

    public void shootPanels() {

        float lookAngleX = (float) Math.atan2(this.alte.getLookAngle().y, Math.sqrt(this.alte.getLookAngle().z * this.alte.getLookAngle().z + this.alte.getLookAngle().x * this.alte.getLookAngle().x));
        float lookAngleY = (float) Math.atan2(this.alte.getLookAngle().z, this.alte.getLookAngle().x);
        if (this.panelcooldown == 10) {
            this.alte.playSound(ALTE_SHOCKWAVE.get());
        }
        if (this.panelcooldown == 1 || this.panelcooldown == 9 || this.panelcooldown == 16) {
            this.panelOne(lookAngleY, lookAngleX, 1);
            this.panelFour(lookAngleY, lookAngleX, 1);
            this.alte.playSound(AlteSoundInit.ALTE_PANEL.get());
        } else if (this.panelcooldown == 3 || this.panelcooldown == 11 || this.panelcooldown == 19) {
            this.panelOne(lookAngleY, lookAngleX, -1);
            this.panelFour(lookAngleY, lookAngleX, -1);
            this.alte.playSound(AlteSoundInit.ALTE_PANEL.get());
        } else if (this.panelcooldown == 6 || this.panelcooldown == 14) {
            this.panelTwo(lookAngleY, lookAngleX, 1);
            this.panelTwo(lookAngleY, lookAngleX, -1);
            this.alte.playSound(AlteSoundInit.ALTE_PANEL.get());
        } else if (this.panelcooldown == 20) {
            this.shockwave();
            this.panelcooldown = 0;
        }

    }

    public void shockwave() {
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
            this.alte.spawnParticlesInUpFacingCircle(this.alte, 1.5F, ALTE_ENERGY_PARTICLE.get());
        }
    }

    public float getHyperMod() {
        return this.alte.getSkillLevels()[5] + 1;
    }

    public void panelOne(float lookAngleY, float lookAngleX, int mod) {
        lookAngleY += Math.PI / 9 * mod;
        float targetradius = 0.3F;
        float paneldist = 1.7F;
        float originX1 = (float) (this.alte.getX() + paneldist * (float) Math.cos(lookAngleY + Math.PI / 2 * mod));
        float originZ1 = (float) (this.alte.getZ() + paneldist * (float) Math.sin(lookAngleY + Math.PI / 2 * mod));
        float originY1 = (float) (this.alte.getEyeY() - 0.85F + paneldist * (float) Math.sin(lookAngleX));

        originX1 = originX1 + targetradius * (float) Math.cos(lookAngleY);
        originZ1 = originZ1 + targetradius * (float) Math.sin(lookAngleY);
        originY1 = originY1 + targetradius * (float) Math.sin(lookAngleX);

        AltePanelProjectile snowball = new AltePanelProjectile(this.alte, originX1, originY1, originZ1);
        snowball.setNoGravity(true);

        float targetX1 = originX1 + 1 * (float) Math.cos(lookAngleY);
        float targetZ1 = originZ1 + 1 * (float) Math.sin(lookAngleY);
        float targetY1 = originY1 + 1 * (float) Math.sin(lookAngleX);
        double d1 = targetX1 - originX1;
        double d2 = targetY1 - snowball.getY();
        double d3 = targetZ1 - originZ1;
        snowball.shoot(d1, d2, d3, 1.6F, 12.0F);
        this.alte.level().addFreshEntity(snowball);
    }

    public void panelFour(float lookAngleY, float lookAngleX, int mod) {
        lookAngleY -= Math.PI / 20 * mod;
        lookAngleX += Math.PI / 30;
        float targetradius = 0.1F;
        float paneldist = 0.7F;
        float originX1 = (float) (this.alte.getX() + paneldist * (float) Math.cos(lookAngleY - Math.PI / 2 * mod));
        float originZ1 = (float) (this.alte.getZ() + paneldist * (float) Math.sin(lookAngleY - Math.PI / 2 * mod));
        float originY1 = (float) (this.alte.getEyeY() + 1.125F + paneldist * (float) Math.sin(lookAngleX));

        originX1 = originX1 + targetradius * (float) Math.cos(lookAngleY);
        originZ1 = originZ1 + targetradius * (float) Math.sin(lookAngleY);
        originY1 = originY1 + targetradius * (float) Math.sin(lookAngleX);

        AltePanelProjectile snowball = new AltePanelProjectile(this.alte, originX1, originY1, originZ1);
        snowball.setNoGravity(true);

        float targetX1 = originX1 + 1 * (float) Math.cos(lookAngleY);
        float targetZ1 = originZ1 + 1 * (float) Math.sin(lookAngleY);
        float targetY1 = originY1 + 1 * (float) Math.sin(lookAngleX);
        double d1 = targetX1 - originX1;
        double d2 = targetY1 - snowball.getY();
        double d3 = targetZ1 - originZ1;
        snowball.shoot(d1, d2, d3, 1.6F, 12.0F);
        this.alte.level().addFreshEntity(snowball);
    }

    public void panelTwo(float lookAngleY, float lookAngleX, int mod) {
        lookAngleY -= Math.PI / 15 * mod;
        lookAngleX += Math.PI / 60;
        float targetradius = 0.4F;
        float paneldist = 1.6F;
        float originX1 = (float) (this.alte.getX() + paneldist * (float) Math.cos(lookAngleY - Math.PI / 2 * mod));
        float originZ1 = (float) (this.alte.getZ() + paneldist * (float) Math.sin(lookAngleY - Math.PI / 2 * mod));
        float originY1 = (float) (this.alte.getEyeY() + 0.1675F + paneldist * (float) Math.sin(lookAngleX));

        originX1 = originX1 + targetradius * (float) Math.cos(lookAngleY);
        originZ1 = originZ1 + targetradius * (float) Math.sin(lookAngleY);
        originY1 = originY1 + targetradius * (float) Math.sin(lookAngleX);

        AltePanelProjectile snowball = new AltePanelProjectile(this.alte, originX1, originY1, originZ1);
        snowball.setNoGravity(true);

        float targetX1 = originX1 + 1 * (float) Math.cos(lookAngleY);
        float targetZ1 = originZ1 + 1 * (float) Math.sin(lookAngleY);
        float targetY1 = originY1 + 1 * (float) Math.sin(lookAngleX);
        double d1 = targetX1 - originX1;
        double d2 = targetY1 - snowball.getY();
        double d3 = targetZ1 - originZ1;
        snowball.shoot(d1, d2, d3, 1.6F, 12.0F);
        this.alte.level().addFreshEntity(snowball);
    }
}
