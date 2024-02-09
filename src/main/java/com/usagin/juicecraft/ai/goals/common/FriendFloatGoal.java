package com.usagin.juicecraft.ai.goals.common;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraftforge.common.ForgeMod;
import org.slf4j.Logger;

//unproducable bug in which the friend will climb into a block instead of on surface. dont know why

public class FriendFloatGoal extends FloatGoal {
    private static final Logger LOGGER = LogUtils.getLogger();
    public final Friend friend;
    public int swimsound = 7;

    public FriendFloatGoal(Friend f) {
        super(f);
        this.friend = f;
    }

    public void start() {
        this.friend.setFriendSwimCounter(10);
    }

    public void stop() {
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

        if (Math.sqrt(this.friend.getDeltaMovement().x * this.friend.getDeltaMovement().x + this.friend.getDeltaMovement().z * this.friend.getDeltaMovement().z) > 0.03) {
            if (this.swimsound % 10 == 0) {
                if (this.friend.level() instanceof ServerLevel level) {
                    level.sendParticles(ParticleTypes.SPLASH, this.friend.getX(), this.friend.getY() + 1, this.friend.getZ(), 5, 0.25, 0, 0.25, 1);
                }
                this.friend.playSound(SoundEvents.PLAYER_SPLASH, 0.05F, Mth.nextFloat(this.friend.getRandom(), 0.8F, 1.1F));
            }
        }
        if (this.swimsound <= 0) {
            if (this.friend.level() instanceof ServerLevel level) {
                level.sendParticles(ParticleTypes.SPLASH, this.friend.getX(), this.friend.getY() + 1, this.friend.getZ(), 5, 0.25, 0, 0.25, 1);
            }
            this.friend.playSound(SoundEvents.PLAYER_SWIM, 0.05F, Mth.nextFloat(this.friend.getRandom(), 0.8F, 1.1F));
            this.swimsound = 30;
        } else {
            this.swimsound--;
        }


        if (this.friend.getSurfaceWaterDistanceFromEye() > 0.45) {
            this.friend.setFriendSwimCounter(11);
        }
        if (this.friend.getFriendSwimCounter() > 0) {
            this.friend.setFriendSwimCounter(this.friend.getFriendSwimCounter() - 1);
        }
    }
}
