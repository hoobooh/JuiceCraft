package com.usagin.juicecraft.ai.goals.sora;

import com.usagin.juicecraft.Init.EntityInit;
import com.usagin.juicecraft.Init.sounds.SoraSoundInit;
import com.usagin.juicecraft.Init.sounds.UniversalSoundInit;
import com.usagin.juicecraft.ai.awareness.CombatSettings;
import com.usagin.juicecraft.client.renderer.entities.SoraChargeEntityRenderer;
import com.usagin.juicecraft.friends.Sora;
import com.usagin.juicecraft.miscentities.SoraChargeEntity;
import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class SoraHyperGoal extends Goal {
    final Sora sora;
    LivingEntity target;

    public SoraHyperGoal(Sora sora) {
        this.sora = sora;
    }

    @Override
    public boolean canUse() {
        this.target = this.sora.getTarget();
        return this.sora.getSkillEnabled()[5] && this.target != null && !this.sora.inventory.getItem(0).isEmpty() && this.sora.canDoThings() && !this.sora.areAnimationsBusy() && this.sora.aggressiontimer > 80 && this.sora.chargecooldown == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.sora.canDoThings() && this.sora.getSyncInt(Sora.CHARGECOUNTER) > 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.sora.playVoice(SoraSoundInit.SORA_UNLIMITED_CHARGE.get(), true);
        this.sora.playSound(SoraSoundInit.SORA_CHARGE_BURST.get(),2,1);
        this.sora.setInvulnerable(true);
        this.sora.getFriendNav().setShouldMove(false);
        this.sora.setSyncInt(Sora.CHARGECOUNTER, 60);
        this.sora.chargecooldown=12000;
    }
    @Override
    public void stop(){
        this.sora.setInvulnerable(false);
        this.sora.getFriendNav().setShouldMove(true);
    }
    @Override
    public void tick(){
        int n = this.sora.getSyncInt(Sora.CHARGECOUNTER);
        if(n ==45){ //main charge
            SoraChargeEntity entity = new SoraChargeEntity(EntityInit.SORA_CHARGE_ENTITY.get(),this.sora.level());
            entity.sora=this.sora;
            entity.soraid=this.sora.getId();
            entity.lifetime= 120;

            float lookAngleX = (float) Math.atan2(this.sora.getLookAngle().y, Math.sqrt(this.sora.getLookAngle().z * this.sora.getLookAngle().z + this.sora.getLookAngle().x * this.sora.getLookAngle().x));
            float lookAngleY = (float) Math.atan2(this.sora.getLookAngle().z, this.sora.getLookAngle().x);

            float speed = 0.3F;

            float posX = (float) this.sora.getX();
            float posY = (float) this.sora.getEyeY() - 0.5F;
            float posZ = (float) this.sora.getZ();
            float originradius = 1;

            float originX = posX + originradius * (float) Math.cos(lookAngleY);
            float originZ = posZ + originradius * (float) Math.sin(lookAngleY);
            float originY = posY + originradius * (float) Math.sin(lookAngleX);

            entity.setPos(new Vec3(originX,originY,originZ));

            float targetX = speed * (float) Math.cos(lookAngleY);
            float targetZ = speed * (float) Math.sin(lookAngleY);
            float targetY = speed * (float) Math.sin(lookAngleX);

            entity.setDeltaMovement(Vec3.ZERO.add(targetX, targetY, targetZ));
            this.sora.level().addFreshEntity(entity);
        }
    }
}
