package com.usagin.juicecraft.ai.goals.common;

import com.usagin.juicecraft.blocks.FriendBedBlock;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

import static com.usagin.juicecraft.Init.ParticleInit.SLEEPY;
import static com.usagin.juicecraft.particles.AlteLightningParticle.LOGGER;
import static net.minecraft.world.level.block.BedBlock.PART;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.OCCUPIED;


public class FriendSleepGoal extends Goal {
    Friend friend;
    int sleeptime = 0;

    public FriendSleepGoal(Friend f) {
        this.friend = f;
    }

    private static Direction getNeighbourDirection(BedPart pPart, Direction pDirection) {
        return pPart == BedPart.FOOT ? pDirection : pDirection.getOpposite();
    }    @Override
    public boolean canUse() {
        return !friend.isAggressive() && friend.canDoThings() && !friend.day();
    }

    public boolean isInterruptable() {
        return false;
    }

    public void start() {

    }

    public void stop() {
        this.friend.setPose(Pose.STANDING);
        this.friend.refreshDimensions();
        if (this.sleeptime > 2400) {
            this.friend.appendEventLog(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".eventlog.sleep").getString());
        }
        this.sleeptime = 0;
    }

    public void tick() {
        if (this.canUse()) {
            if (this.friend.getFeetBlockState().isBed(this.friend.level(), new BlockPos(this.friend.getBlockX(), this.friend.getBlockY() - 1, this.friend.getBlockZ()), null)) {
                if (this.friend.sleepy() && this.friend.getPose() != Pose.SLEEPING && !this.friend.getInSittingPose() && !this.friend.getFeetBlockState().getValue(OCCUPIED)) {
                    moveToBed(this.friend.getOnPos(), this.friend.getFeetBlockState());
                    this.friend.setDeltaMovement(Vec3.ZERO);
                    this.friend.setPose(Pose.SLEEPING);
                    this.friend.reapplyPosition();
                    this.friend.refreshDimensions();
                    if(this.friend.getFeetBlockState().getBlock() instanceof FriendBedBlock){
                        this.friend.setHome(this.friend.getBlockX(),this.friend.getBlockY(),this.friend.getBlockZ());
                    }
                } else if (this.friend.getFeetBlockState().getValue(OCCUPIED)) {
                    this.stop();
                } else {
                    this.sleeptime++;
                    this.friend.updateFriendNorma(0.00007F, 7);
                }

            } else if (!this.friend.getNavigation().isInProgress()) {
                ArrayList<BlockPos> beds = this.closeBeds();
                if (!beds.isEmpty()) {
                    for (BlockPos pos1 : beds) {
                        moveToBed(pos1, this.friend.level().getBlockState(pos1));
                        break;
                    }
                } else {
                    this.friend.setPose(Pose.STANDING);
                    this.friend.reapplyPosition();
                    this.friend.refreshDimensions();
                    beds = this.beds();
                    if (!beds.isEmpty()) {
                        for (BlockPos pos1 : beds) {
                            if (this.friend.getNavigation().moveTo(pos1.getCenter().x, pos1.getY(), pos1.getCenter().z, 0.75F)) {
                                break;
                            }
                        }
                    }
                }
            } else {
                ArrayList<BlockPos> beds = this.closeBeds();
                if (!beds.isEmpty()) {
                    for (BlockPos pos1 : beds) {
                        if (Math.sqrt(this.friend.distanceToSqr(pos1.getX(), pos1.getY(), pos1.getZ())) < 0.5) {
                            this.friend.getNavigation().stop();
                            moveToBed(pos1, this.friend.level().getBlockState(pos1));
                            break;
                        }
                    }
                } else {
                    this.friend.setPose(Pose.STANDING);
                    this.friend.reapplyPosition();
                    this.friend.refreshDimensions();
                }
            }

            if (this.friend.snoozeCounter-- == 0) {
                if (friend.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(SLEEPY.get(), this.friend.getX() + (float) this.friend.getRandom().nextInt(-1, 2) / 2,
                            this.friend.getY() + this.friend.getBbHeight() + (float) this.friend.getRandom().nextInt(-1, 2) / 7,
                            this.friend.getZ() + (float) this.friend.getRandom().nextInt(-1, 2) / 2,
                            1, 0, 0, 0, 0.1);
                }
                this.friend.snoozeCounter = 40;
            }
        } else {
            this.stop();
        }
    }

    void moveToBed(BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof BedBlock) {
            if (BedBlock.getBlockType(state) != DoubleBlockCombiner.BlockType.FIRST) { //if block is not head
                Direction dir = state.getBedDirection(this.friend.level(), pos);
                switch (dir) {
                    case NORTH ->
                            this.friend.setPos(new Vec3(pos.getCenter().x, pos.getY() + 0.7, pos.getCenter().z - 1));
                    case SOUTH ->
                            this.friend.setPos(new Vec3(pos.getCenter().x, pos.getY() + 0.7, pos.getCenter().z + 1));
                    case EAST ->
                            this.friend.setPos(new Vec3(pos.getCenter().x + 1, pos.getY() + 0.7, pos.getCenter().z));
                    default -> this.friend.setPos(new Vec3(pos.getCenter().x - 1, pos.getY() + 0.7, pos.getCenter().z));
                }
            }
        }
    }




    ArrayList<BlockPos> beds() {
        ArrayList<BlockPos> fin = new ArrayList<>();
        int[] xPos = new int[]{0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10};
        int[] zPos = new int[]{0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6, 7, -7, 8, -8, 9, -9, 10, -10};
        int[] yPos = new int[]{0, 1, -1, 2, -2, 3, -3};
        for (int x : xPos) {
            for (int y : yPos) {
                for (int z : zPos) {
                    BlockPos pos = new BlockPos(this.friend.getBlockX() + x, this.friend.getBlockY() + y, this.friend.getBlockZ() + z);
                    BlockState state = this.friend.level().getBlockState(pos);
                    if (state.getBlock().isBed(state, this.friend.level(), pos, null)) {
                        if (state.getBlock() instanceof BedBlock bBlock) {
                            if (state.getValue(PART) != BedPart.HEAD) {
                                if (!state.getValue(OCCUPIED)) {
                                    fin.add(pos);
                                }
                            }
                        }

                    }
                }
            }
        }
        return fin;
    }

    ArrayList<BlockPos> closeBeds() {
        ArrayList<BlockPos> fin = new ArrayList<>();
        int[] xPos = new int[]{0, 1, -1};
        int[] zPos = new int[]{0, 1, -1};
        int[] yPos = new int[]{0, 1, -1};
        for (int x : xPos) {
            for (int y : yPos) {
                for (int z : zPos) {
                    BlockPos pos = new BlockPos(this.friend.getBlockX() + x, this.friend.getBlockY() + y, this.friend.getBlockZ() + z);
                    BlockState state = this.friend.level().getBlockState(pos);
                    if (state.getBlock().isBed(state, this.friend.level(), pos, null)) {
                        if (state.getBlock() instanceof BedBlock bBlock) {
                            if (state.getValue(PART) != BedPart.HEAD) {
                                if (!state.getValue(OCCUPIED)) {
                                    fin.add(pos);
                                }
                            }
                        }

                    }
                }
            }
        }
        return fin;
    }
}
