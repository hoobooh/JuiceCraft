package com.usagin.juicecraft.ai.goals.common;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;

import static com.usagin.juicecraft.friends.Friend.LOGGER;

public class FriendGoHomeGoal extends Goal
{
    public final Friend friend;
    public Path path;
    public FriendGoHomeGoal(Friend friend){
        this.friend=friend;
    }
    @Override
    public boolean canUse() {
        if(!this.friend.wandering && this.friend.isTame()){
            return false;
        }
        BlockPos pos = this.friend.getHome();
        if(this.friend.canDoThings() && Math.sqrt(this.friend.distanceToSqr(pos.getX(),pos.getY(),pos.getZ()))>32){
            this.path=this.friend.getNavigation().createPath(this.friend.getHome(),1);
            this.friend.getNavigation().moveTo(this.path,1);
            return true;
        }else{
            return false;
        }
    }
    @Override
    public boolean canContinueToUse(){
        if(this.friend.getNavigation().getPath()==null){
            return false;
        }
        return this.friend.canDoThings() && this.friend.getNavigation().getPath().sameAs(this.path) && !this.friend.isAggressive() && (this.friend.wandering || !this.friend.isTame());
    }
    @Override
    public void start(){
        this.friend.getNavigation().moveTo(this.path,1);
    }
}
