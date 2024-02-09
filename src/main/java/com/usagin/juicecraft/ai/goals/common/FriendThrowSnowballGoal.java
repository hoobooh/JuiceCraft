package com.usagin.juicecraft.ai.goals.common;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class FriendThrowSnowballGoal extends Goal {
    protected final Friend friend;
    private final double speedModifier;
    private final float attackRadiusSqr;
    private int attackIntervalMin;
    private int attackTime = -1;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    public FriendThrowSnowballGoal(Friend f) {
        this.friend = f;
        this.speedModifier = 1;
        this.attackRadiusSqr = 225;
    }

    @Override
    public boolean canUse() {
        return this.friend.canDoThings() && this.friend.getTarget() != null && this.friend.isHoldingThrowable() && this.friend.getAttackCounter() <= 0;
    }

    @Override
    public void start() {
        this.friend.setAttackCounter(20);
        this.friend.setAttackType(60);
        this.friend.setAggressive(true);
    }

    public void stop() {
        this.friend.setAggressive(false);
        this.seeTime = 0;
        this.attackTime = -1;
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        LivingEntity livingentity = this.friend.getTarget();
        if (livingentity != null) {
            double d0 = this.friend.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
            boolean flag = this.friend.getSensing().hasLineOfSight(livingentity);
            boolean flag1 = this.seeTime > 0;
            if (flag != flag1) {
                this.seeTime = 0;
            }

            if (flag) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }

            if (!(d0 > (double) this.attackRadiusSqr)) {
                this.friend.getNavigation().stop();
                ++this.strafingTime;
            } else {
                this.friend.getNavigation().moveTo(livingentity, this.speedModifier);
                this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
                if ((double) this.friend.getRandom().nextFloat() < 0.3D) {
                    this.strafingClockwise = !this.strafingClockwise;
                }

                if ((double) this.friend.getRandom().nextFloat() < 0.3D) {
                    this.strafingBackwards = !this.strafingBackwards;
                }

                this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
                if (d0 > (double) (this.attackRadiusSqr * 0.75F)) {
                    this.strafingBackwards = false;
                } else if (d0 < (double) (this.attackRadiusSqr * 0.25F)) {
                    this.strafingBackwards = true;
                }

                this.friend.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);

                this.friend.lookAt(livingentity, 30.0F, 30.0F);
            } else {
                this.friend.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            }

        }
    }
}
