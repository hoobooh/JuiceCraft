package com.usagin.juicecraft.ai.goals;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraftforge.common.ForgeMod;
import org.slf4j.Logger;

public class FriendFloatGoal extends FloatGoal {
    public final Friend friend;
    private static final Logger LOGGER = LogUtils.getLogger();

    public FriendFloatGoal(Friend f) {
        super(f);
        this.friend = f;
    }
    public void start(){
        this.friend.setFriendSwimCounter(10);
    }

    public boolean canUse() {
        return this.friend.isInWater() //true
                //&& this.friend.getFluidHeight(FluidTags.WATER) > this.friend.getFluidJumpThreshold()
                || this.friend.isInLava(); //false

        //|| this.friend.isInFluidType((fluidType, height) -> this.friend.canSwimInFluidType(fluidType) && height > this.friend.getFluidJumpThreshold());
    }

    public void tick() {
        //LOGGER.info(this.friend.getSurfaceWaterDistanceFromEye() +"");
        if (this.friend.getSurfaceWaterDistanceFromEye() < 0.2) {
            double change = (double) 0.02F * this.friend.getAttributeValue(ForgeMod.SWIM_SPEED.get());
            this.friend.setDeltaMovement(this.friend.getDeltaMovement().add(0.0D, change, 0.0D));
        } else if (this.friend.horizontalCollision && this.friend.getFriendSwimCounter() == 0) {
            double change = (double) 0.04F * this.friend.getAttributeValue(ForgeMod.SWIM_SPEED.get());
            this.friend.setDeltaMovement(this.friend.getDeltaMovement().add(0.0D, change, 0.0D));
        }
        if (this.friend.getSurfaceWaterDistanceFromEye() > 0.45) {
            this.friend.setFriendSwimCounter(11);
        }
        if (this.friend.getFriendSwimCounter() > 0) {
            this.friend.setFriendSwimCounter(this.friend.getFriendSwimCounter()-1);
        }
    }

    public void stop() {
        this.friend.setFriendSwimCounter(10);
    }
}
