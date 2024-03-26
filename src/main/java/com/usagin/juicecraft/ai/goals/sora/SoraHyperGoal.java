package com.usagin.juicecraft.ai.goals.sora;

import com.usagin.juicecraft.Init.sounds.SoraSoundInit;
import com.usagin.juicecraft.ai.awareness.CombatSettings;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class SoraHyperGoal extends Goal {
    final Sora sora;
    LivingEntity target;

    public SoraHyperGoal(Sora sora) {
        this.sora = sora;
    }

    @Override
    public boolean canUse() {
        this.target = this.sora.getTarget();
        return this.target != null && !this.sora.inventory.getItem(0).isEmpty() && this.sora.canDoThings() && !this.sora.areAnimationsBusy() && this.sora.isAggressive() && this.sora.chargecooldown == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.sora.canDoThings() && this.sora.getSyncInt(Sora.CHARGECOUNTER) > 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.sora.playVoice(SoraSoundInit.SORA_UNLIMITED_CHARGE.get(), true);
        this.sora.setInvulnerable(true);
        this.sora.getFriendNav().setShouldMove(false);
        this.sora.setSyncInt(Sora.CHARGECOUNTER, 60);
        this.sora.chargecooldown=12000;
    }
    @Override
    public void stop(){
        this.sora.setInvulnerable(false);
        this.sora.getFriendNav().setShouldMove(true);
    }
    @Override
    public void tick(){
        int n = this.sora.getSyncInt(Sora.CHARGECOUNTER);
        if(n ==45){ //main charge
            this.sora.
        }
    }
}
