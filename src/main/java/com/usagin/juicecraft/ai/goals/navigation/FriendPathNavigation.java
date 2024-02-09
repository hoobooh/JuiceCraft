package com.usagin.juicecraft.ai.goals.navigation;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;

public class FriendPathNavigation extends GroundPathNavigation {
    final Friend friend;
    public boolean shouldMove = true;

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

    public void setShouldMove(boolean b) {
        this.shouldMove = b;
    }

    @Override
    public void tick() {
        if (this.shouldMove) {
            super.tick();
        }
    }

    @Override
    public void stop() {
        this.friend.chasingitem = false;
        super.stop();
    }
}
