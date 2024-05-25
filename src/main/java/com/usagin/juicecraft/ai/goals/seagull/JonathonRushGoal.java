package com.usagin.juicecraft.ai.goals.seagull;

import com.usagin.juicecraft.enemies.Seagull;
import net.minecraft.world.entity.ai.goal.Goal;

public class JonathonRushGoal extends Goal {
    protected final Seagull seagull;

    public JonathonRushGoal(Seagull seagull){
        this.seagull=seagull;
    }
    @Override
    public boolean canUse() {
        return false;
    }
}
