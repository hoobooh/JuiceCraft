package com.usagin.juicecraft.ai.awareness;

import com.usagin.juicecraft.friends.Friend;

public class FriendDefense {
    static public boolean shouldDefendAgainst(Friend friend) {
        int setting = friend.getCombatSettings().defense;

        if (setting == 1) {
            if (friend.getHealth() / friend.getMaxHealth() > 0.5) {
                return false;
            }
        } else if (setting == 2) {
            if (friend.getTarget() != null) {
                if (EnemyEvaluator.evaluate(friend.getTarget()) < friend.getFriendExperience() / 2) {
                    return false;
                }
            }
        } else if (setting == 3) {
            return false;
        }

        int n = friend.getRandom().nextInt(0, 21);
        return n <= friend.getCombatMod();
    }
}
