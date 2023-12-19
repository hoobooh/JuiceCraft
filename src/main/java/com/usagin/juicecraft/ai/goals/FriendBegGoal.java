package com.usagin.juicecraft.ai.goals;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class FriendBegGoal extends Goal{
    private final Friend friend;
    @Nullable
    private Player player;
    private final Level level;
    private final float lookDistance;
    private int lookTime;
    private final TargetingConditions begTargeting;

    public FriendBegGoal(Friend pWolf, float pLookDistance) {
        this.friend = pWolf;
        this.level = pWolf.level();
        this.lookDistance = pLookDistance;
        this.begTargeting = TargetingConditions.forNonCombat().range((double)pLookDistance);
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if(!this.friend.getInSittingPose() && !this.friend.isDying){
        this.player = this.level.getNearestPlayer(this.begTargeting, this.friend);
        return this.player != null && this.playerHoldingInteresting(this.player);}
        else{return false;}
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        if(!this.friend.getInSittingPose() && !this.friend.isDying){
        if (!this.player.isAlive()) {
            return false;
        } else if (this.friend.distanceToSqr(this.player) > (double)(this.lookDistance * this.lookDistance)) {
            return false;
        } else {
            return this.lookTime > 0 && this.playerHoldingInteresting(this.player);
        }}else{
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        if(!this.friend.getInSittingPose() && !this.friend.isDying){
        this.friend.setIsInterested(true);
        this.lookTime = this.adjustedTickDelay(40 + this.friend.getRandom().nextInt(40));}
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.friend.setIsInterested(false);
        this.player = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.friend.getLookControl().setLookAt(this.player.getX(), this.player.getEyeY(), this.player.getZ(), 10.0F, (float)this.friend.getMaxHeadXRot());
        --this.lookTime;
    }

    /**
     * Gets if the Player has the Bone in the hand.
     */
    private boolean playerHoldingInteresting(Player pPlayer) {
        for(InteractionHand interactionhand : InteractionHand.values()) {
            ItemStack itemstack = pPlayer.getItemInHand(interactionhand);
            if (this.friend.isTame() && this.friend.isEdible(itemstack)) {
                return true;
            }
        }

        return false;
    }
}
