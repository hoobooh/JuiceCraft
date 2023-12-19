package com.usagin.juicecraft.ai.goals;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import org.slf4j.Logger;

public class FriendRandomLookAroundGoal extends RandomLookAroundGoal
{
    Friend friend;
    private static final Logger LOGGER = LogUtils.getLogger();
    public FriendRandomLookAroundGoal(Mob pMob) {
        super(pMob);
        this.friend= (Friend) pMob;
    }
    public void start() {
        if(!this.friend.isDying){
            if(!this.friend.sleeping() || !this.friend.getFeetBlockState().isBed(this.friend.level(),new BlockPos(this.friend.getBlockX(),this.friend.getBlockY()-1, this.friend.getBlockZ()),null)){
                super.start();
            }
        }

    }
    @Override
    public boolean canUse(){
        if(this.friend.getInSittingPose()||this.friend.isDying){return false;}else{return super.canUse();}
    }
    public void tick() {
        if(!this.friend.sleeping() || !this.friend.getFeetBlockState().isBed(this.friend.level(),new BlockPos(this.friend.getBlockX(),this.friend.getBlockY()-1, this.friend.getBlockZ()),null)){
            super.tick();
        }
    }
}
