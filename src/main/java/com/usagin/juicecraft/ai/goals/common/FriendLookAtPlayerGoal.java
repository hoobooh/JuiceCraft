package com.usagin.juicecraft.ai.goals.common;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;

public class FriendLookAtPlayerGoal extends LookAtPlayerGoal {
    private static final Logger LOGGER = LogUtils.getLogger();
    Friend friend;
    boolean lonely = false;

    public FriendLookAtPlayerGoal(Mob pMob, Class<? extends LivingEntity> pLookAtType, float pLookDistance) {
        super(pMob, pLookAtType, pLookDistance);
        this.friend = (Friend) pMob;
    }

    public void start() {
        if (!this.friend.isDying) {
            if ((!this.friend.sleepy() || !this.friend.animateSleep()) || !this.friend.getFeetBlockState().isBed(this.friend.level(), new BlockPos(this.friend.getBlockX(), this.friend.getBlockY() - 1, this.friend.getBlockZ()), null)) {
                super.start();
            }
        }
    }

    public void tick() {
        if ((!this.friend.sleepy() || !this.friend.animateSleep()) || !this.friend.getFeetBlockState().isBed(this.friend.level(), new BlockPos(this.friend.getBlockX(), this.friend.getBlockY() - 1, this.friend.getBlockZ()), null)) {
            super.tick();
        }
    }

    public boolean canUseShell() {
        this.lonely = this.friend.getTimeSinceLastPat() > 3600;
        if (this.mob.getRandom().nextFloat() >= this.probability && !this.lonely) {
            return false;
        } else {
            if (this.mob.getTarget() != null) {
                this.lookAt = this.mob.getTarget();
            }

            if (this.lookAtType == Player.class) {
                this.lookAt = this.mob.level().getNearestPlayer(this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            } else {
                this.lookAt = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.lookAtType, this.mob.getBoundingBox().inflate(this.lookDistance, 3.0D, this.lookDistance), (p_148124_) -> {
                    return true;
                }), this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            }

            return this.lookAt != null;
        }
    }

    public boolean canContinueToUse() {
        int mod = lonely ? 5 : 1;
        if (canUse()) {
            if (!this.lookAt.isAlive()) {
                return false;
            } else return this.mob.distanceToSqr(this.lookAt) < (double) (this.lookDistance * this.lookDistance) * mod;
        }
        return false;
    }

    @Override
    public boolean canUse() {
        if (this.friend.isDying || this.friend.lockLookAround() || this.friend.getPose() == Pose.SLEEPING) {
            return false;
        } else {
            return this.canUseShell();
        }
    }
}
