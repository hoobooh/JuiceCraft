package com.usagin.juicecraft.ai.goals;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import org.slf4j.Logger;

public class FriendRangedAttackGoal<T extends Friend> extends RangedBowAttackGoal<T> {
    Friend mob;
    private static final Logger LOGGER = LogUtils.getLogger();
    public <M extends Monster & RangedAttackMob> FriendRangedAttackGoal(T pMob, double pSpeedModifier, int pAttackIntervalMin, float pAttackRadius) {
        super(pMob, pSpeedModifier, pAttackIntervalMin, pAttackRadius);
        this.mob = pMob;
    }
    public boolean canUse() {
        return this.mob.canDoThings() && this.mob.getTarget() != null && this.isHoldingBow();
    }
    public void start(){
        super.start();
    }
    public void stop(){
        super.stop();
    }
}
