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

public class FriendBegGoal extends FriendLonelyGoal{
    private final Friend friend;
    @Nullable
    private Player player;
    private final Level level;

    public FriendBegGoal(Friend pTamable, double pSpeedModifier, boolean pCanFly) {
        super(pTamable, pSpeedModifier, pCanFly);
        this.friend = pTamable;
        this.level = pTamable.level();
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if(!this.friend.getInSittingPose() && !this.friend.isDying){
        this.player = (Player) friend.getOwner();
        return this.player != null && this.playerHoldingInteresting(this.player);}
        else{return false;}
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
