package com.usagin.juicecraft.ai.awareness;

import com.usagin.juicecraft.friends.Friend;

public class FriendDefense {
    static public boolean shouldDefendAgainst(Friend friend) {
        if(shouldBeCareful(friend) || friend.isFearless()){
            int n = friend.getRandom().nextInt(0, 21);
            return n <= friend.getCombatMod();
        }
        return false;

    }
    static public boolean shouldBeCareful(Friend friend){
        if(friend.isFearless()){
            return false;
        }

        int setting = friend.getCombatSettings().defense;

        if (setting == 1) {
            return !(friend.getHealth() / friend.getMaxHealth() > 0.5);
        } else if (setting == 2) {
            if (friend.getTarget() != null) {
                return !(EnemyEvaluator.evaluate(friend.getTarget()) < friend.getFriendExperience() / 2);
            }
        } else return setting != 3;
        return true;
    }
}
