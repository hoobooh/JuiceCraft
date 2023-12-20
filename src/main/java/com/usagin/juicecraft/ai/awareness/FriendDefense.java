package com.usagin.juicecraft.ai.awareness;

import com.usagin.juicecraft.friends.Friend;

public class FriendDefense {
    static public boolean shouldDefendAgainst(Friend friend){
        return friend.getRandom().nextBoolean();
    }
}
