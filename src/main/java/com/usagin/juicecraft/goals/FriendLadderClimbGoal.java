package com.usagin.juicecraft.goals;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;

import java.util.Objects;

public class FriendLadderClimbGoal extends Goal {

    private Friend friend;
    private Path path;
    public FriendLadderClimbGoal(Friend pFriend){
        this.friend=pFriend;
        this.path=pFriend.getNavigation().getPath();
    }
    @Override
    public boolean canUse() {
        return !this.friend.getNavigation().isDone() ;
    }
    @Override
    public void tick() {
        double yMotion;
        if(canUse()){
            try{if(this.friend.level().getBlockState(new BlockPos(this.friend.getBlockX(), this.friend.getBlockY(), this.friend.getBlockZ())).getBlock() instanceof LadderBlock){
                if(Objects.requireNonNull(this.friend.getNavigation().getPath()).getNextNodePos().getY() > this.friend.getBlockY()){
                    yMotion = 0.15;
                }
                else{
                    yMotion = -0.15;
                }
                this.friend.setDeltaMovement(this.friend.getDeltaMovement().multiply(0.1, 1, 0.1));
                //centers the friend onto the ladder to keep it from bumping into the roof or falling off
                double xOffset=  friend.getBlockX()-friend.getX();
                double zOffset= friend.getBlockZ()-friend.getZ();
                if(friend.getX()>0){
                    xOffset=0-xOffset;
                }
                if(friend.getZ()>0){
                    zOffset=0-zOffset;
                }

                this.friend.setDeltaMovement(this.friend.getDeltaMovement().add(0, yMotion, 0));
            }}catch(Exception E){
                //do nothing
            }
        }
    }
}
