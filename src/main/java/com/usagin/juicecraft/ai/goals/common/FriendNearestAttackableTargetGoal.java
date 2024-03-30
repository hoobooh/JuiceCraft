package com.usagin.juicecraft.ai.goals.common;

import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Alte;
import com.usagin.juicecraft.friends.Friend;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class FriendNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    Friend friend;

    public FriendNearestAttackableTargetGoal(Mob pMob, Class<T> pTargetType, boolean pMustSee) {
        super(pMob, pTargetType, pMustSee);
        this.friend = (Friend) pMob;
    }

    public FriendNearestAttackableTargetGoal(Mob pMob, Class<T> pTargetType, boolean pMustSee, Predicate<LivingEntity> pTargetPredicate) {
        super(pMob, pTargetType, pMustSee, pTargetPredicate);
        this.friend = (Friend) pMob;
    }

    public FriendNearestAttackableTargetGoal(Mob pMob, Class<T> pTargetType, boolean pMustSee, boolean pMustReach) {
        super(pMob, pTargetType, pMustSee, pMustReach);
        this.friend = (Friend) pMob;
    }

    public FriendNearestAttackableTargetGoal(Mob pMob, Class<T> pTargetType, int pRandomInterval, boolean pMustSee, boolean pMustReach, @Nullable Predicate<LivingEntity> pTargetPredicate) {
        super(pMob, pTargetType, pRandomInterval, pMustSee, pMustReach, pTargetPredicate);
        this.friend = (Friend) pMob;
    }

    @Override
    public boolean canUse() {
        if (!this.friend.canDoThings() || this.friend.getCombatSettings().aggression != 3) {
            return false;
        } else {
            return super.canUse();
        }
    }
    public void doSpecialChecks(Entity e){
        if(this.friend instanceof Alte){
            if(e instanceof Sora){
                if(this.friend.getSpecialDialogueEnabled()[1] == 0)     {
                    this.friend.setSpecialDialogueEnabled(new int[]{this.friend.getSpecialDialogueEnabled()[0], 1,this.friend.getSpecialDialogueEnabled()[2]});
                }
            }
        }
    }

    @Override
    protected void findTarget() {
        //LOGGER.info(this.friend.flowercooldown +" " + this.friend.getViewFlower());
        if (this.targetType != Player.class && this.targetType != ServerPlayer.class) {
            this.target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), (p_148152_) -> {
                return true;
            }), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            if (this.target == null && friend.canDoThings() && friend.getPose() != Pose.SLEEPING && !this.friend.isAttackLockedOut()) {
                AABB tempbox = this.getTargetSearchArea(this.getFollowDistance());
                List<Entity> entityList = this.friend.level().getEntities(this.friend, tempbox);
                for(Entity e: entityList){
                    this.doSpecialChecks(e);
                }
                if(this.friend.getViewFlower()==1){
                    this.friend.getFriendNav().setShouldMove(true);
                }
                if (this.friend.getFriendItemPickup() != 2 && this.friend.isTame()) {
                    for (Entity e : entityList) {
                        if (e instanceof ItemEntity item) {
                            if (this.friend.wantsToPickUp(item.getItem())) {
                                this.friend.chasingitem = true;
                                this.friend.getNavigation().moveTo(e, 1);
                                return;
                            }
                        }
                    }
                }
                if (this.friend.getViewFlower() == 0 && this.friend.flowercooldown <= 0 && !this.friend.sleepy()) {
                    for (int x = -1; x < 2; x++) {
                        for (int y = -1; y < 2; y++) {
                            for (int z = -1; z < 2; z++) {
                                BlockPos pos = new BlockPos(this.friend.getBlockX() + x, this.friend.getBlockY() + y, this.friend.getBlockZ() + z);
                                BlockState state = this.friend.level().getBlockState(pos);
                                Block block = state.getBlock();
                                //LOGGER.info(block.getName().getString() +" " + block.getClass().getSimpleName());
                                if (block instanceof BushBlock) {
                                    this.friend.getFriendNav().stop();
                                    this.friend.getFriendNav().setShouldMove(false);
                                    this.friend.lookAt(EntityAnchorArgument.Anchor.EYES, pos.getCenter());
                                    this.friend.playVoice(this.friend.getLaugh());
                                    this.friend.setViewFlower(60);
                                    this.friend.flowercooldown = 300;
                                    return;
                                }
                            }
                        }
                    }
                    if (this.friend.tickCount % 200 == 0) {
                        for (int x = -10; x < 11; x++) {
                            for (int y = -2; y < 3; y++) {
                                for (int z = -10; z < 11; z++) {
                                    BlockPos pos = new BlockPos(this.friend.getBlockX() + x, this.friend.getBlockY() + y, this.friend.getBlockZ() + z);
                                    BlockState state = this.friend.level().getBlockState(pos);
                                    Block block = state.getBlock();

                                    if (block instanceof BushBlock) {
                                        this.friend.getNavigation().moveTo(pos.getX(), pos.getY(), pos.getZ(), 1);
                                        return;
                                    }
                                }

                            }
                        }
                    }
                }
                if (this.friend.flowercooldown > 0) {
                    this.friend.flowercooldown--;
                }
            }
        }
    }

    public void start() {
        if (this.friend.canDoThings() && this.friend.getCombatSettings().aggression == 3) {
            this.mob.setTarget(this.target);
            if (EnemyEvaluator.evaluate(this.target) > this.friend.getFriendExperience() / 2) {
                this.friend.playTimedVoice(this.friend.getWarning());
            }
            super.start();
        }
    }
}
