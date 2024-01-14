package com.usagin.juicecraft.ai.goals.navigation;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;

public class FriendPathNavigation extends GroundPathNavigation {
    final Friend friend;

    public FriendPathNavigation(Friend pMob, Level pLevel) {
        super(pMob, pLevel);
        friend = pMob;
    }
    @Override
    protected PathFinder createPathFinder(int pMaxVisitedNodes) {
        this.nodeEvaluator = new FriendNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        return new FriendPathfinder(this.nodeEvaluator, pMaxVisitedNodes);
    }
    @Override
    public void stop() {
        this.friend.chasingitem=false;
        super.stop();
    }
}
