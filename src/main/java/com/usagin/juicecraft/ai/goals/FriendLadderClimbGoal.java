package com.usagin.juicecraft.ai.goals;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.client.particle.HeartParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;

import java.util.Objects;

import static net.minecraft.world.level.block.Blocks.LADDER;

public class FriendLadderClimbGoal extends Goal {

    private final Friend friend;
    double yMotion = 0;
    private static final Logger LOGGER = LogUtils.getLogger();
    private int findCounter = 20;
    int yMod = 2;
    int soundcounter = 15;
    Path path;
    BlockPos oldPos;

    public FriendLadderClimbGoal(Friend pFriend) {
        this.friend = pFriend;
    }
    public void start(){
        this.friend.setDeltaMovement(this.friend.getDeltaMovement().multiply(1,0,1));
    }
    @Override
    public boolean canUse() {
        if (!this.friend.canDoThings()) {
            return false;
        } else {
            try {
                this.path = this.friend.getNavigation().getPath().copy();
                return !this.path.isDone() && isLadder(this.friend.getBlockX(), this.friend.getBlockY(), this.friend.getBlockZ());
            } catch (Exception e) {
                return false;
            }
        }
    }
    public boolean canContinueToUse() {
        if (!this.friend.canDoThings()) {
            return false;
        } else {
            return !this.path.isDone() && isLadder(this.friend.getBlockX(), this.friend.getBlockY(), this.friend.getBlockZ());

        }
    }


    @Override
    public void tick() {
        double xOffset;
        double zOffset;

        yMotion = 0;
        if (canUse()) {
            //uncomment this if you want to see the path ingame
            /*for(int i=0;i<this.path.getNodeCount();i++){
                if(this.friend.level() instanceof ServerLevel level){
                    Node node = this.path.getNode(i);
                    level.sendParticles(ParticleTypes.HEART,node.x,node.y,node.z,1,0,0,0,1);
                }
            }*/
            if(this.friend.getOnPos().equals(this.path.getNextNodePos())){
                this.path.advance();
            }
            try {
                if (this.path.getNextNodePos().getY() + 1> this.friend.getY() || this.friend.horizontalCollision) {
                    //need enough momentum to clear a two block ladder
                    //LOGGER.info("HIT");
                    yMotion = 0.17;
                } else {
                    //LOGGER.info("HIT2");
                    yMotion = -0.17;
                    yMod = 0;

                }
            } catch (Exception e) {
                //do nothing
            }
            findCounter = 20;
        }

        try {
            if (isLadder(this.friend.getBlockX(), this.friend.getBlockY(), this.friend.getBlockZ())) {
                this.friend.setDeltaMovement(this.friend.getDeltaMovement().multiply(0.3, 1, 0.3));
                //centers the friend onto the ladder to keep it from bumping into the roof or falling off
                xOffset = friend.getBlockX() - friend.getX();
                zOffset = friend.getBlockZ() - friend.getZ();
                if (soundcounter <= 0) {
                    this.friend.playSound(SoundEvents.LADDER_STEP);
                    soundcounter = 15;
                } else {
                    soundcounter--;
                }
                this.friend.setDeltaMovement(this.friend.getDeltaMovement().multiply(1,0,1));
                this.friend.setDeltaMovement(this.friend.getDeltaMovement().add(0, yMotion, 0));
            } else {
                this.yMod = 2;
                this.yMotion = 0;
            }
        } catch (Exception E) {
            //do nothing
        }
    }

    boolean isLadder(int x, int y, int z) {
        BlockPos pPos = new BlockPos(x, y, z);
        return this.friend.level().getBlockState(pPos).getBlock().isLadder(friend.level().getBlockState(pPos), this.friend.level(), pPos, this.friend);
    }

    boolean isLadder(Node pNode) {
        BlockPos pPos = new BlockPos(pNode.x, pNode.y, pNode.z);
        return this.friend.level().getBlockState(pPos).getBlock().isLadder(friend.level().getBlockState(pPos), this.friend.level(), pPos, this.friend);
    }
}
