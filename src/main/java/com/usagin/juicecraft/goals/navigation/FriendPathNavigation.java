package com.usagin.juicecraft.goals.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class FriendPathNavigation extends GroundPathNavigation {

    public FriendPathNavigation(Mob pMob, Level pLevel) {
        super(pMob, pLevel);
    }
    @Override
    protected PathFinder createPathFinder(int pMaxVisitedNodes) {
        this.nodeEvaluator = new FriendNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        return new FriendPathfinder(this.nodeEvaluator, pMaxVisitedNodes);
    }

}
