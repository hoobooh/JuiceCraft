package com.usagin.juicecraft.goals;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;

public class FriendOwnerHurtTargetGoal extends OwnerHurtTargetGoal {
    Friend friend;
    public FriendOwnerHurtTargetGoal(TamableAnimal pTameAnimal) {
        super(pTameAnimal);
        this.friend=(Friend) pTameAnimal;
    }
    public void start(){
        if(!this.friend.getInSittingPose() && !this.friend.isDying){
            super.start();
        }
    }
    @Override
    public boolean canUse(){
        if(this.friend.getInSittingPose()||this.friend.isDying){return false;}else{return super.canUse();}
    }
}
