package com.usagin.juicecraft.ai.goals;

import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.ai.awareness.FriendFlee;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.EnumSet;
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

    public void start() {
        if (this.friend.canDoThings() && this.friend.getCombatSettings().aggression == 3) {
            this.mob.setTarget(this.target);
            if (EnemyEvaluator.evaluate(this.target) > this.friend.getFriendExperience() / 2) {
                this.friend.playTimedVoice(this.friend.getWarning());
            }
            super.start();
        }
    }

    @Override
    public boolean canUse() {
        if (!this.friend.canDoThings() || this.friend.getCombatSettings().aggression != 3) {
            return false;
        } else {
            return super.canUse();
        }
    }

    @Override
    protected void findTarget() {
        if (this.targetType != Player.class && this.targetType != ServerPlayer.class) {
            this.target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), (p_148152_) -> {
                return true;
            }), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            if (this.target == null && friend.canDoThings() && friend.getPose() != Pose.SLEEPING) {
                AABB tempbox = this.getTargetSearchArea(this.getFollowDistance());
                List<Entity> entityList = this.friend.level().getEntities(this.friend, tempbox);
                if (this.friend.getFriendItemPickup() != 2) {
                    for (Entity e : entityList) {
                        if (e instanceof ItemEntity item) {
                            if (this.friend.wantsToPickUp(item.getItem())) {
                                this.friend.getNavigation().moveTo(e,1);
                                return;
                            }
                        }
                    }
                }
                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        for (int z = -1; z < 2; z++) {
                            BlockPos pos = new BlockPos(this.friend.getBlockX() + x, this.friend.getBlockY() + y, this.friend.getBlockZ() + z);
                            BlockState state = this.friend.level().getBlockState(pos);
                            Block block = state.getBlock();

                            if (block instanceof FlowerBlock) {
                                this.friend.getNavigation().stop();
                                this.friend.lookAt(EntityAnchorArgument.Anchor.EYES,pos.getCenter());
                                this.friend.playVoice(this.friend.getLaugh());
                                this.friend.setViewFlower(60);
                                return;
                            }
                        }

                    }
                }
                for (int x = -3; x < 4; x++) {
                    for (int y = -1; y < 2; y++) {
                        for (int z = -3; z < 4; z++) {
                            BlockPos pos = new BlockPos(this.friend.getBlockX() + x, this.friend.getBlockY() + y, this.friend.getBlockZ() + z);
                            BlockState state = this.friend.level().getBlockState(pos);
                            Block block = state.getBlock();

                            if (block instanceof FlowerBlock) {
                                this.friend.getNavigation().moveTo(pos.getX(),pos.getY(),pos.getZ(),1);
                                return;
                            }
                        }

                    }
                }
            }
        } else {
            this.target = this.mob.level().getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        }

    }
}
