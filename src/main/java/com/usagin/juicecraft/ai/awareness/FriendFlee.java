package com.usagin.juicecraft.ai.awareness;

import com.usagin.juicecraft.friends.Friend;

public class FriendFlee {
    public static boolean willFriendFlee(Friend friend) {
        int setting = friend.getCombatSettings().willFlee;
        if (setting == 0) {
            return false;
        } else if (setting == 1) {
            return friend.getHealth() / friend.getMaxHealth() <= 0.25;
        } else if (setting == 2) {
            if (friend.getTarget() != null) {
                return EnemyEvaluator.evaluate(friend.getTarget()) > friend.getFriendExperience() / 2;

            }
        }
        return true;
    }
}
