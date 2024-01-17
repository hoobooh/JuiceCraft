package com.usagin.juicecraft.ai.goals;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import static com.mojang.text2speech.Narrator.LOGGER;

public class FriendSitGoal extends Goal {
    Friend friend;
    float att;
    Pose pose;
    public FriendSitGoal(Friend f){
        this.friend=f;
    }
    private static final Logger LOGGER = LogUtils.getLogger();
    public void start() {
        this.friend.getNavigation().stop();
        this.pose=this.friend.getPose();
        this.friend.setPose(Pose.SITTING);
        this.friend.reapplyPosition();
        this.friend.refreshDimensions();
    }
    public boolean isInterruptable() {
        return true;
    }

    public void stop() {
        this.friend.setPose(pose);
        this.friend.reapplyPosition();
        this.friend.refreshDimensions();
    }
    @Override
    public boolean canUse() {
        return (friend.getInSittingPose()&&!friend.isDying&&!friend.isFallFlying()&&(!friend.isInWater() || friend.onGround()));
    }
}
