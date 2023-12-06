package com.usagin.juicecraft.goals;

import com.usagin.juicecraft.data.CombatSettings;
import net.minecraft.world.entity.ai.goal.Goal;

public class SoraHyperGoal extends Goal {
    boolean hasActivator;
    CombatSettings settings;
    public SoraHyperGoal(boolean pHasActivator, CombatSettings pSettings){
        this.hasActivator=pHasActivator;
        this.settings=pSettings;
    }
    @Override
    public boolean canUse() {
        return this.hasActivator;
    }
}
