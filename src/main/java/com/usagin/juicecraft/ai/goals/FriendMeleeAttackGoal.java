package com.usagin.juicecraft.ai.goals;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class FriendMeleeAttackGoal extends MeleeAttackGoal {
    private int ticksUntilNextAttack;
    Friend friend;
    public FriendMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.friend=(Friend) pMob;
    }
    @Override
    public void start() {
        if(!this.friend.isDying){
        this.friend.playVoice(((Friend)this.mob).getBattle());
        super.start();}
    }
    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity pTarget) {
        Logger LOGGER = LogUtils.getLogger();
        if (this.canPerformAttack(pTarget) && this.mob instanceof Friend pFriend) {
            if(pFriend.getAttackCounter()==0){
                this.resetAttackCooldown();
                pFriend.swing(InteractionHand.MAIN_HAND);
            }
        }
    }
    @Override
    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(0);
    }
    @Override
    public boolean canUse(){
        if(this.friend.getInSittingPose()||this.friend.isDying){return false;}else{return super.canUse();}
    }
    @Override
    public void tick(){
        if(!this.friend.getInSittingPose()&&!this.friend.isDying){
            super.tick();
        }
    }
}
