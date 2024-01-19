package com.usagin.juicecraft.ai.goals;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.CrossbowItem;


public class FriendRangedCrossbowAttackGoal extends ShellCrossbowGoal {
    Friend mob;
    public FriendRangedCrossbowAttackGoal(Friend pMob, double pSpeedModifier, float pAttackRadius) {
        super(pMob, pSpeedModifier, pAttackRadius);
        this.mob = pMob;
    }
    public boolean canUse() {
        return this.mob.canDoThings() && this.mob.getTarget() != null && this.mob.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof CrossbowItem && this.isValidTarget();
    }
}
