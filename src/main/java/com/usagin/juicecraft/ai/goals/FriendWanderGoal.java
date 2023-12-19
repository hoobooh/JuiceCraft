package com.usagin.juicecraft.ai.goals;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import org.slf4j.Logger;

public class FriendWanderGoal extends WaterAvoidingRandomStrollGoal {
    Friend friend;
    public FriendWanderGoal(PathfinderMob pMob, double pSpeedModifier) {
        super(pMob, pSpeedModifier);
        if(pMob instanceof Friend pFriend){
            this.friend=pFriend;
        }
    }
    private static final Logger LOGGER = LogUtils.getLogger();
    @Override
    public void start(){
        if(friend.patCounter==0 && !friend.getInSittingPose() && !friend.sleeping() && friend.day()){
            super.start();
        }
    }
    @Override
    public boolean canUse(){
        if(this.friend.getInSittingPose()||this.friend.isDying){return false;}else{return super.canUse();}
    }
    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone() && !this.mob.hasControllingPassenger() && !this.friend.getInSittingPose() && !this.friend.isDying;
    }
}
