package com.usagin.juicecraft.ai.goals.common;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import org.slf4j.Logger;

public class FriendSitGoal extends Goal {
    private static final Logger LOGGER = LogUtils.getLogger();
    Friend friend;
    float att;
    Pose pose;

    public FriendSitGoal(Friend f) {
        this.friend = f;
    }

    @Override
    public boolean canUse() {
        return (!friend.isAttackLockedOut() && friend.getInSittingPose() && !friend.isDying && !friend.isFallFlying() && (!friend.isInWater() || friend.onGround()));
    }

    public boolean isInterruptable() {
        return true;
    }

    public void start() {
        this.friend.getNavigation().stop();
        this.friend.getFriendNav().setShouldMove(false);
        this.pose = this.friend.getPose();
        this.friend.setPose(Pose.SITTING);
        this.friend.reapplyPosition();
        this.friend.refreshDimensions();
    }

    public void stop() {
        this.friend.getFriendNav().setShouldMove(true);
        this.friend.setPose(pose);
        this.friend.reapplyPosition();
        this.friend.refreshDimensions();
    }
}
