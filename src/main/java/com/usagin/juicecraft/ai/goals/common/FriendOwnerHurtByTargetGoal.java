package com.usagin.juicecraft.ai.goals.common;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import org.slf4j.Logger;


public class FriendOwnerHurtByTargetGoal extends OwnerHurtByTargetGoal {
    private static final Logger LOGGER = LogUtils.getLogger();
    Friend friend;

    public FriendOwnerHurtByTargetGoal(TamableAnimal pTameAnimal) {
        super(pTameAnimal);
        this.friend = (Friend) pTameAnimal;
    }

    @Override
    public boolean canUse() {
        if (this.friend.isDying || this.friend.getCombatSettings().aggression == 0) {
            return false;
        } else {
            return super.canUse();
        }
    }

    public void start() {
        if (!this.friend.isDying) {
            this.friend.setFriendInSittingPose(false);
            this.friend.setPose(Pose.STANDING);
            super.start();
        }
    }
}
