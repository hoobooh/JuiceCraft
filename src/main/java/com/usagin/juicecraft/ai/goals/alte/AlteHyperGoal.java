package com.usagin.juicecraft.ai.goals.alte;

import com.usagin.juicecraft.friends.Alte;
import net.minecraft.world.entity.ai.goal.Goal;

public class AlteHyperGoal extends Goal {
    private final Alte alte;
    public AlteHyperGoal(Alte alte){
        this.alte=alte;
    }
    @Override
    public boolean canUse() {
        return false;
    }
}
