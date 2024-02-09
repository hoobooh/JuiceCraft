package com.usagin.juicecraft.ai.goals.common;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;

public class FriendOpenDoorGoal extends OpenDoorGoal {
    Friend friend;
    public FriendOpenDoorGoal(Friend pMob, boolean pCloseDoor) {
        super(pMob, pCloseDoor);
        this.friend=pMob;
    }
    public void start() {
        super.start();
        this.friend.setFriendPlaceCounter(10);
    }

}
