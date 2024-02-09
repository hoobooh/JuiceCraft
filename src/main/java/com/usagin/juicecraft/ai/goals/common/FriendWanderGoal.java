package com.usagin.juicecraft.ai.goals.common;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import org.slf4j.Logger;

public class FriendWanderGoal extends WaterAvoidingRandomStrollGoal {
    private static final Logger LOGGER = LogUtils.getLogger();
    Friend friend;

    public FriendWanderGoal(PathfinderMob pMob, double pSpeedModifier) {
        super(pMob, pSpeedModifier);
        if (pMob instanceof Friend pFriend) {
            this.friend = pFriend;
        }
    }

    @Override
    public boolean canUse() {
        if (!this.friend.canDoThings()) {
            return false;
        } else {
            return super.canUse();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone() && !this.mob.hasControllingPassenger() && this.friend.canDoThings();
    }

    @Override
    public void start() {
        if (friend.patCounter == 0 && this.friend.canDoThings() && !friend.sleepy() && friend.day()) {
            super.start();
        }
    }
}
