package com.usagin.juicecraft.ai.goals.common;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.ai.awareness.FriendDefense;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class FriendHitAndRunGoal extends Goal {
    private static final Logger LOGGER = LogUtils.getLogger();
    protected final PathNavigation pathNav;
    @Nullable
    protected Path path;
    Friend friend;
    LivingEntity target;
    public FriendHitAndRunGoal(Friend friend) {
        this.friend = friend;
        this.pathNav = friend.getNavigation();
    }

    @Override
    public boolean canUse() {
        if (friend.getTarget() != null && this.friend.canDoThings() && this.friend.runTimer > 30) {
            this.target = this.friend.getTarget();
            Vec3 vec3 = this.findRandomAwayPos();
            if (vec3 == null) {
                return false;
            } else if (this.friend.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.target.distanceToSqr(this.friend)) {
                return false;
            } else {
                this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
                return this.path != null;
            }
        }
        return false;
    }

    Vec3 findRandomAwayPos() {
        Vec3 temp = this.nearbyVec(new Vec3(this.target.getX(), this.target.getY(), this.target.getZ()));
        for (int n = 0; n < 40; n++) {
            if (verifyMove(temp)) {
                if (temp.distanceToSqr(this.target.position()) > 140) {
                    //LOGGER.info(temp.distanceToSqr(this.target.position()) +"");
                    return temp;
                }
                temp = this.nearbyVec(temp);

            }
        }
        return temp;
    }

    Vec3 nearbyVec(Vec3 vec3) {
        RandomSource random = this.friend.getRandom();
        return new Vec3(vec3.x + random.nextInt(-1, 2), vec3.y + random.nextInt(0, 2), vec3.z + random.nextInt(-1, 2));
    }

    boolean verifyMove(Vec3 vec3) {
        return this.friend.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1);
    }

    @Override
    public boolean canContinueToUse() {
        return friend.getTarget() != null && this.friend.canDoThings() && this.friend.runTimer > 0;
    }

    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        this.pathNav.stop();
        this.pathNav.moveTo(this.path, this.friend.getAttributeValue(Attributes.MOVEMENT_SPEED));
    }

    @Override
    public void stop() {
        this.target = null;
    }

    @Override
    public void tick() {
        if (this.friend.distanceToSqr(this.target) < 5.0D) {
            this.friend.getNavigation().setSpeedModifier(1.3);
        } else {
            this.friend.getNavigation().setSpeedModifier(1);
        }
    }
}
