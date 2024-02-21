package com.usagin.juicecraft.ai.goals.harbinger;

import com.usagin.juicecraft.enemies.Harbinger;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class HarbingerMeleeAttackGoal extends Goal {
    public final Harbinger harbinger;
    public HarbingerMeleeAttackGoal(Harbinger harbinger){
        this.harbinger=harbinger;
    }
    @Override
    public boolean canUse() {
        return this.harbinger.getTarget()!=null && !this.harbinger.getSyncBoolean(Harbinger.PEACEFUL) && this.harbinger.getSyncInt(Harbinger.ANIMCOUNTER) <= 0;
    }
    @Override
    public boolean canContinueToUse(){
        return this.harbinger.getTarget()!=null && !this.harbinger.getSyncBoolean(Harbinger.PEACEFUL) && this.harbinger.getSyncInt(Harbinger.ANIMCOUNTER) <= 0;
    }
    @Override
    public void start(){
        this.harbinger.getNavigation().moveTo(this.harbinger.getTarget(),1);
        this.harbinger.setAggressive(true);
    }
    @Override
    public void stop(){
        this.harbinger.getNavigation().stop();
        this.harbinger.setAggressive(false);
    }
    @Override
            public boolean requiresUpdateEveryTick(){
        return true;
    }
    int tpcd=0;
    @Override
    public void tick(){
        if(!this.harbinger.getNavigation().isInProgress()){
            this.harbinger.getNavigation().moveTo(this.harbinger.getTarget(),1);
        }
        if(this.harbinger.tickCount%7==0){
            if(this.harbinger.distanceTo(this.harbinger.getTarget())>7 && this.tpcd==0){
                this.tpcd=40;
                this.harbinger.playSound(SoundEvents.ELDER_GUARDIAN_CURSE);
            }
        }
        if(tpcd>0){
            if(tpcd%3==0){
                this.harbinger.spawnParticlesInUpFacingCircle(this.harbinger.getTarget(),1.5F, ParticleTypes.PORTAL);
            }
            if(tpcd==1){
                this.harbinger.getTarget().teleportTo(this.harbinger.getX(),this.harbinger.getY(),this.harbinger.getZ());
            }
            tpcd--;
        }
        checkAndPerformAttack(this.harbinger.getTarget());
    }
    protected void checkAndPerformAttack(@NotNull LivingEntity pTarget) {
        if (this.canPerformAttack(pTarget)){
            this.harbinger.swing();
        }
    }

    protected boolean canPerformAttack(LivingEntity pEntity) {
        boolean flag = this.harbinger.isWithinMeleeAttackRange(pEntity);
        return flag && this.harbinger.getSensing().hasLineOfSight(pEntity);
    }
}
