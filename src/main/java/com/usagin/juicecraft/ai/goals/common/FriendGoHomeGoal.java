package com.usagin.juicecraft.ai.goals.common;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public class FriendGoHomeGoal extends Goal
{
    public final Friend friend;
    public FriendGoHomeGoal(Friend friend){
        this.friend=friend;
    }
    @Override
    public boolean canUse() {
        if(!this.friend.wandering && this.friend.isTame()){
            return false;
        }
        BlockPos pos = this.friend.getHome();
        return this.friend.canDoThings() && Math.sqrt(this.friend.distanceToSqr(pos.getX(),pos.getY(),pos.getZ()))>32;
    }
    @Override
    public boolean canContinueToUse(){
        return this.friend.canDoThings() && this.friend.getNavigation().isInProgress() && !this.friend.isAggressive();
    }

}
