package com.usagin.juicecraft.ai.goals.common;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import org.slf4j.Logger;

public class FriendRandomLookAroundGoal extends RandomLookAroundGoal {
    private static final Logger LOGGER = LogUtils.getLogger();
    Friend friend;

    public FriendRandomLookAroundGoal(Mob pMob) {
        super(pMob);
        this.friend = (Friend) pMob;
    }

    @Override
    public boolean canUse() {
        if (this.friend.lockLookAround() || this.friend.isDying || this.friend.lockLookAround() || this.friend.getPose() == Pose.SLEEPING) {
            return false;
        } else {
            return super.canUse();
        }
    }

    public boolean canContinueToUse() {
        if (this.friend.isDying || this.friend.lockLookAround() || this.friend.getPose() == Pose.SLEEPING) {
            return false;
        } else {
            return super.canContinueToUse();
        }
    }

    public void start() {
        if (!this.friend.isDying) {
            if (!this.friend.sleepy() || !this.friend.getFeetBlockState().isBed(this.friend.level(), new BlockPos(this.friend.getBlockX(), this.friend.getBlockY() - 1, this.friend.getBlockZ()), null)) {
                super.start();
            }
        }

    }

    public void tick() {
        if (!this.friend.sleepy() || !this.friend.getFeetBlockState().isBed(this.friend.level(), new BlockPos(this.friend.getBlockX(), this.friend.getBlockY() - 1, this.friend.getBlockZ()), null)) {
            super.tick();
        }
    }
}
