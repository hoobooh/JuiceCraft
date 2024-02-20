package com.usagin.juicecraft.ai.goals.harbinger;

import com.usagin.juicecraft.enemies.Harbinger;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
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
    }
    @Override
    public void stop(){
        this.harbinger.getNavigation().stop();
    }
    @Override
    public void tick(){
        if(!this.harbinger.getNavigation().isInProgress()){
            this.harbinger.getNavigation().moveTo(this.harbinger.getTarget(),1);
        }
        if(this.harbinger.tickCount%7==0){
            this.harbinger.lookAt(this.harbinger.getTarget(),30,30);
            this.harbinger.getLookControl().setLookAt(this.harbinger.getTarget());
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
