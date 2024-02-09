package com.usagin.juicecraft.ai.goals.common;

import com.usagin.juicecraft.friends.Friend;


public class FriendLonelyGoal extends FriendFollowGoal {
    private final Friend friend;

    public FriendLonelyGoal(Friend pTamable, double pSpeedModifier, boolean pCanFly) {
        super(pTamable, pSpeedModifier, 3, 1, pCanFly);
        this.friend = pTamable;
    }

    public boolean canUse() {
        if (this.friend.getTimeSinceLastPat() > 3600) {
            return super.canUse();
        }
        return false;
    }

    public void start() {
        super.start();
    }
}
