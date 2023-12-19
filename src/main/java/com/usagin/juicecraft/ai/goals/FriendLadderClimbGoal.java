package com.usagin.juicecraft.ai.goals;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
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
    double yMotion =0;
    private static final Logger LOGGER = LogUtils.getLogger();
    private int findCounter=20;
    int yMod=2;
    public FriendLadderClimbGoal(Friend pFriend){
        this.friend=pFriend;
    }
    @Override
    public boolean canUse(){
        if(this.friend.getInSittingPose()||this.friend.isDying){return false;}else{return !this.friend.getNavigation().isDone();}
    }
    @Override
    public void tick() {
        double xOffset;
        double zOffset;

        if(true){
            if(canUse()){
                try{
                    if(this.friend.getNavigation().getPath().getNextNodePos().getY()+yMod > this.friend.getY()){
                        //need enough momentum to clear a two block ladder
                        yMotion = 0.2;
                    }
                    else{
                        //make this work
                        //if a mob is moving on a ladder it automatically goes up, mitigate it
                        yMotion = -0.2;
                        yMod=0;

                    }}catch(Exception e){
                    //do nothing
                }
                findCounter=20;
        }

            try{if(isLadder(this.friend.getBlockX(), this.friend.getBlockY(), this.friend.getBlockZ()) && isLadder(this.friend.getNavigation().getPath().getNextNode())){
                this.friend.setDeltaMovement(this.friend.getDeltaMovement().multiply(0.1, 1, 0.1));
                //centers the friend onto the ladder to keep it from bumping into the roof or falling off
                xOffset=  friend.getBlockX()-friend.getX();
                zOffset= friend.getBlockZ()-friend.getZ();
                double center=0.5;
                if(friend.getX()<0){
                    xOffset=0-xOffset;
                    center=-0.5;
                }
                if(friend.getZ()<0){
                    zOffset=0-zOffset;
                    center=-0.5;
                }

                this.friend.setDeltaMovement(this.friend.getDeltaMovement().add(0, yMotion, 0));
            }else{
                this.yMod=2;
                this.yMotion=0;
            }
            }catch(Exception E){
                //do nothing
            }
        }
    }

    boolean isLadder(int x, int y, int z){
        BlockPos pPos = new BlockPos(x, y, z);
        return this.friend.level().getBlockState(
                        pPos)
                .getBlock()
                .isLadder(friend.level().getBlockState
                                (pPos),
                        this.friend.level(),
                        pPos,
                        this.friend);
    }
    boolean isLadder(Node pNode){
        BlockPos pPos = new BlockPos(pNode.x, pNode.y, pNode.z);
        return this.friend.level().getBlockState(
                        pPos)
                .getBlock()
                .isLadder(friend.level().getBlockState
                                (pPos),
                        this.friend.level(),
                        pPos,
                        this.friend);
    }
}
