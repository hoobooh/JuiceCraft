package com.usagin.juicecraft.ai.goals.common;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;
import org.slf4j.Logger;

public class FriendFarmGoal extends Goal {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Friend friend;
    private BlockPos targetPos;

    public FriendFarmGoal(Friend friend) {
        this.friend = friend;
    }

    @Override
    public boolean canUse() {
        return !this.friend.farmqueue.isEmpty() && this.friend.canDoThings() && this.friend.getPose() != Pose.SLEEPING;
    }

    @Override
    public boolean canContinueToUse() {
        return this.targetPos != null && this.friend.canDoThings() && this.friend.day();
    }

    public void start() {
        while (!this.friend.farmqueue.isEmpty()) {
            BlockPos temppos = this.friend.farmqueue.poll();
            if (this.friend.distanceToSqr(temppos.getX(), temppos.getY(), temppos.getZ()) < 20) {
                this.targetPos = temppos;
                this.friend.getNavigation().stop();
                this.friend.getNavigation().moveTo(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ(), 1);
                break;
            }
        }
    }

    @Override
    public void tick() {
        if (targetPos != null) {
            if (this.friend.distanceToSqr(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ()) < 2) {
                BlockState state = this.friend.level().getBlockState(targetPos);
                Block block = state.getBlock();
                if (block instanceof CropBlock cropBlock) {
                    if (cropBlock.isMaxAge(state) && cropBlock.getPlantType(this.friend.level(), targetPos) == PlantType.CROP) {
                        if (this.friend.level().destroyBlock(targetPos, false)) {
                            if (this.friend.level() instanceof ServerLevel sLevel) {
                                for (ItemStack item : Block.getDrops(state, sLevel, this.targetPos, null, this.friend, this.friend.getFriendWeapon())) {
                                    this.moveItemToEmptySlots(this.friend.inventory, item);
                                }
                                for (int i = 0; i < this.friend.inventory.getContainerSize(); i++) {
                                    if (this.friend.inventory.getItem(i).is(cropBlock.getCloneItemStack(sLevel, targetPos, state).getItem())) {
                                        //may work bad with some modded plants
                                        //change later if it becomes an issue
                                        this.friend.inventory.getItem(i).setCount(this.friend.inventory.getItem(i).getCount() - 1);
                                        sLevel.setBlockAndUpdate(this.targetPos, cropBlock.defaultBlockState());
                                        this.friend.increaseEXP(2 * this.friend.getPeaceAffinityModifier());
                                        if (this.friend.farmqueue.isEmpty()) {
                                            this.friend.appendEventLog(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".eventlog.farming").getString());
                                        }
                                    }
                                }
                                this.friend.setFriendPlaceCounter(10);
                            }
                        }
                        this.friend.getNavigation().stop();
                        this.targetPos = null;
                    }
                }
            }
        }
    }

    private void moveItemToEmptySlots(SimpleContainer inventory, ItemStack pStack) {
        for (int i = 7; i < inventory.getContainerSize(); ++i) {
            if (ItemStack.isSameItemSameTags(inventory.getItem(i), pStack)) {
                moveItemToOccupiedSlotsWithSameType(inventory, pStack);
            }
        }
        if (!pStack.isEmpty()) {
            for (int i = 7; i < inventory.getContainerSize(); ++i) {
                if (inventory.getItem(i).isEmpty()) {
                    inventory.setItem(i, pStack.copyAndClear());
                    return;
                }
            }
        } else {
            return;
        }
        Block.popResource(this.friend.level(), this.friend.getOnPos(), pStack);
    }

    private void moveItemToOccupiedSlotsWithSameType(SimpleContainer inventory, ItemStack pStack) {
        for (int i = 7; i < inventory.getContainerSize(); ++i) {
            ItemStack itemstack = inventory.getItem(i);
            if (ItemStack.isSameItemSameTags(itemstack, pStack)) {
                this.moveItemsBetweenStacks(inventory, pStack, itemstack);
                if (pStack.isEmpty()) {
                    return;
                }
            }
        }

    }

    private void moveItemsBetweenStacks(SimpleContainer inventory, ItemStack pStack, ItemStack pOther) {
        int i = Math.min(inventory.getMaxStackSize(), pOther.getMaxStackSize());
        int j = Math.min(pStack.getCount(), i - pOther.getCount());
        if (j > 0) {
            pOther.grow(j);
            pStack.shrink(j);
            inventory.setChanged();
        }

    }
}
