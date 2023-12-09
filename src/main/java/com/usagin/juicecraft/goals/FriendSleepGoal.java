package com.usagin.juicecraft.goals;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;

public class FriendSleepGoal extends Goal {
    Friend friend;
    public FriendSleepGoal(Friend f){
        this.friend=f;
    }
    @Override
    public boolean canUse() {
        return friend.getInSleepingPose();
    }
    public void tick() {
        if(this.canUse()){
            if(friend.level() instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(ParticleTypes.CLOUD,this.friend.getX(), this.friend.getY(), this.friend.getZ(),3,0,1,0,0);
            }
        }
    }
}
