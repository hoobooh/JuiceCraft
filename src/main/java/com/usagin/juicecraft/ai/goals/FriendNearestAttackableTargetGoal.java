package com.usagin.juicecraft.ai.goals;

import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class FriendNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    Friend friend;
    public FriendNearestAttackableTargetGoal(Mob pMob, Class<T> pTargetType, boolean pMustSee) {
        super(pMob, pTargetType, pMustSee);
        this.friend=(Friend) pMob;
    }

    public FriendNearestAttackableTargetGoal(Mob pMob, Class<T> pTargetType, boolean pMustSee, Predicate<LivingEntity> pTargetPredicate) {
        super(pMob,pTargetType,pMustSee,pTargetPredicate);
        this.friend=(Friend) pMob;
    }

    public FriendNearestAttackableTargetGoal(Mob pMob, Class<T> pTargetType, boolean pMustSee, boolean pMustReach) {
        super(pMob,pTargetType,pMustSee,pMustReach);
        this.friend=(Friend) pMob;
    }

    public FriendNearestAttackableTargetGoal(Mob pMob, Class<T> pTargetType, int pRandomInterval, boolean pMustSee, boolean pMustReach, @Nullable Predicate<LivingEntity> pTargetPredicate) {
        super(pMob,pTargetType,pRandomInterval,pMustSee,pMustReach,pTargetPredicate );
        this.friend=(Friend) pMob;
    }

    public void start() {
        if(this.friend.canDoThings() && this.friend.getCombatSettings().aggression==3){
            this.mob.setTarget(this.target);
            super.start();
        }
    }
    @Override
    public boolean canUse(){
        if(!this.friend.canDoThings()||this.friend.getCombatSettings().aggression!=3){return false;}else{return super.canUse();}
    }
}
