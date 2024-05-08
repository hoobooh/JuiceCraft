package com.usagin.juicecraft.ai.goals.common;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.CrossbowItem;


public class FriendRangedCrossbowAttackGoal<T extends Friend & RangedAttackMob & CrossbowAttackMob> extends ShellCrossbowGoal<T> {
    Friend mob;

    public FriendRangedCrossbowAttackGoal(T pMob, double pSpeedModifier, float pAttackRadius) {
        super(pMob, pSpeedModifier, pAttackRadius);
        this.mob = pMob;
    }

    public boolean canUse() {
        return this.mob.canDoThings() && this.mob.getTarget() != null && this.mob.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof CrossbowItem && this.isValidTarget() && !this.mob.isAttackLockedOut();
    }
}
