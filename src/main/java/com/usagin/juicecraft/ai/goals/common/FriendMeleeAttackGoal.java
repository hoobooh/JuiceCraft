package com.usagin.juicecraft.ai.goals.common;

import com.usagin.juicecraft.ai.awareness.FriendFlee;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import org.jetbrains.annotations.NotNull;

public class FriendMeleeAttackGoal extends ShellMeleeGoal {
    Friend friend;
    private int ticksUntilNextAttack;

    public FriendMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.friend = (Friend) pMob;
    }

    @Override
    public boolean canUse() {
        if (this.friend.getInSittingPose() || this.friend.isDying || this.friend.isHoldingThrowable() || this.friend.isAttackLockedOut()) {
            return false;
        } else {
            return super.canUse() && !FriendFlee.willFriendFlee(this.friend);
        }
    }

    @Override
    public void start() {
        if (!this.friend.isDying) {
            this.friend.playTimedVoice(((Friend) this.mob).getBattle());
            super.start();
        }
    }

    @Override
    public void stop() {

        super.stop();
    }

    @Override
    public void tick() {
        if (!this.friend.level().isClientSide()) {
            if (this.friend.canDoThings() && this.friend.runTimer <= 0 && this.friend.getTarget() != null) {
                if (!this.friend.getBoundingBox().intersects(this.friend.getTarget().getBoundingBox())) {
                    this.friend.getNavigation().stop();
                    this.friend.getNavigation().moveTo(this.friend.getTarget(), 1);
                }
                super.tick();
            }
        } else {
            super.tick();
        }
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity pTarget) {
        if (this.canPerformAttack(pTarget) && this.mob instanceof Friend pFriend) {
            pFriend.swing(InteractionHand.MAIN_HAND);
        }
    }

    protected boolean canPerformAttack(LivingEntity pEntity) {
        boolean flag = this.mob.isWithinMeleeAttackRange(pEntity);
        return flag && this.mob.getSensing().hasLineOfSight(pEntity);
    }

    protected boolean isTimeToAttack() {
        return true;
    }

    @Override
    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(0);
    }
}
