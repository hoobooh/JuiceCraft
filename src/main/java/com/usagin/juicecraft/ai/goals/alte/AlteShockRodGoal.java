package com.usagin.juicecraft.ai.goals.alte;

import com.usagin.juicecraft.friends.Alte;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;

public class AlteShockRodGoal extends Goal {
    protected final Alte alte;
    protected LivingEntity target;
    public AlteShockRodGoal(Alte alte){
        this.alte=alte;
    }

    @Override
    public boolean canUse() {
        this.target=this.alte.getTarget();
        return this.alte.isUsingHyper() && this.alte.canDoThings() && this.alte.rodcooldown >= 12000 && this.alte.getPose()!= Pose.SLEEPING && this.target!=null && !this.alte.areAnimationsBusy();
    }
    @Override
    public boolean canContinueToUse(){
        return this.alte.getRodDuration() > this.alte.rodcooldown && this.alte.canDoThings();
    }
    @Override
    public void start(){

    }
    @Override
    public void stop(){

    }
}
