package com.usagin.juicecraft.ai.goals.common;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import org.slf4j.Logger;

public class FriendRangedBowAttackGoal<T extends Friend> extends RangedBowAttackGoal<T> {
    private static final Logger LOGGER = LogUtils.getLogger();
    Friend mob;

    public <M extends Monster & RangedAttackMob> FriendRangedBowAttackGoal(T pMob, double pSpeedModifier, int pAttackIntervalMin, float pAttackRadius) {
        super(pMob, pSpeedModifier, pAttackIntervalMin, pAttackRadius);
        this.mob = pMob;
    }

    public boolean canUse() {
        return this.mob.canDoThings() && this.mob.getTarget() != null && this.isHoldingBow() && !this.mob.isAttackLockedOut();
    }

    public void start() {
        this.mob.getFriendNav().setShouldMove(false);
        super.start();
    }

    public void stop() {
        super.stop();
        this.mob.getFriendNav().setShouldMove(true);
    }
}
