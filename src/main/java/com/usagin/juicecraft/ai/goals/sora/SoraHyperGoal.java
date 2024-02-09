package com.usagin.juicecraft.ai.goals.sora;

import com.usagin.juicecraft.ai.awareness.CombatSettings;
import net.minecraft.world.entity.ai.goal.Goal;

public class SoraHyperGoal extends Goal {
    boolean hasActivator;
    CombatSettings settings;

    public SoraHyperGoal(boolean pHasActivator, CombatSettings pSettings) {
        this.hasActivator = pHasActivator;
        this.settings = pSettings;
    }

    @Override
    public boolean canUse() {
        return this.hasActivator;
    }
}
