package com.usagin.juicecraft.goals;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import org.slf4j.Logger;

public class FriendLookAtPlayerGoal extends LookAtPlayerGoal {
    Friend friend;
    private static final Logger LOGGER = LogUtils.getLogger();
    public FriendLookAtPlayerGoal(Mob pMob, Class<? extends LivingEntity> pLookAtType, float pLookDistance) {
        super(pMob, pLookAtType, pLookDistance);
        this.friend= (Friend) pMob;
    }
    public void start() {
        if(!this.friend.isDying){
        if((!this.friend.sleeping()||!this.friend.animateSleep())|| !this.friend.getFeetBlockState().isBed(this.friend.level(),new BlockPos(this.friend.getBlockX(),this.friend.getBlockY()-1, this.friend.getBlockZ()),null)){
            super.start();
        }}
    }
    public void tick() {
        if((!this.friend.sleeping()||!this.friend.animateSleep())|| !this.friend.getFeetBlockState().isBed(this.friend.level(),new BlockPos(this.friend.getBlockX(),this.friend.getBlockY()-1, this.friend.getBlockZ()),null)){
            super.tick();
        }
    }
    @Override
    public boolean canUse(){
        if(!this.friend.getInSittingPose()&&!this.friend.isDying){return false;}else{return super.canUse();}
    }
}
