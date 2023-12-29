package com.usagin.juicecraft.ai;

import com.usagin.juicecraft.ai.goals.FriendFollowGoal;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.goal.Goal;



public class FriendLonelyGoal extends FriendFollowGoal {
    private final Friend friend;
    public FriendLonelyGoal(Friend pTamable, double pSpeedModifier, boolean pCanFly) {
        super(pTamable, pSpeedModifier, 3, 1, pCanFly);
        this.friend=pTamable;
    }
    public boolean canUse(){
        if(this.friend.getTimeSinceLastPat()>3600){
            return super.canUse();
        }
        return false;
    }
    public void start(){
        if(this.friend.getOwner()!=null){
            this.friend.appendEventLog(this.friend.getOwner().getScoreboardName() + Component.translatable("juicecraft.menu."+this.friend.getFriendName().toLowerCase()+".eventlog.lonely").getString());
        }
        super.start();
    }
}
