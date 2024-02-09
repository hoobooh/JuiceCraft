package com.usagin.juicecraft.ai.goals.common;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;

public class FriendOwnerHurtTargetGoal extends OwnerHurtTargetGoal {
    Friend friend;

    public FriendOwnerHurtTargetGoal(TamableAnimal pTameAnimal) {
        super(pTameAnimal);
        this.friend = (Friend) pTameAnimal;
    }

    @Override
    public boolean canUse() {
        if (!this.friend.canDoThings() || this.friend.getCombatSettings().aggression < 2) {
            return false;
        } else {
            return super.canUse();
        }
    }

    public void start() {
        if (!this.friend.getInSittingPose() && !this.friend.isDying) {
            super.start();
        }
    }
}
