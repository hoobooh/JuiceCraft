package com.usagin.juicecraft.ai.goals.sora;

import com.usagin.juicecraft.Init.EntityInit;
import com.usagin.juicecraft.Init.sounds.SoraSoundInit;
import com.usagin.juicecraft.friends.Sora;
import com.usagin.juicecraft.miscentities.SoraShieldEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class SoraShieldGoal extends Goal {
    public final Sora sora;
    public final LivingEntity owner;
    public SoraShieldGoal(Sora sora){
        this.sora=sora;
        this.owner=sora.getOwner();
    }

    @Override
    public boolean canUse() {
        return this.sora.canDoThings() && this.sora.shieldcooldown <= 0 && this.sora.isAggressive() && this.sora.getSkillEnabled()[2] && !this.sora.getSyncBoolean(Sora.BARRIER);
    }
    @Override
    public boolean canContinueToUse(){
        return this.sora.canDoThings() && this.sora.shieldduration < this.getShieldDuration() && this.sora.getSkillEnabled()[2];
    }
    public float getShieldDuration(){
        int n = this.sora.getSkillLevels()[2];
        return 20 * (20F + 20*n) / (20+n);
    }
    @Override
    public boolean requiresUpdateEveryTick(){
        return true;
    }
    @Override
    public void start(){
        this.sora.setSyncBoolean(Sora.BARRIER,true);
        this.sora.shieldcooldown=1200;
        this.sora.shieldduration=0;
        this.sora.playVoice(SoraSoundInit.SORA_SHIELD,true);
        this.sora.playSound(SoraSoundInit.SHIELD_START);
        this.sora.setInvulnerable(true);
        this.sora.absorbeddamage = 0;
        SoraShieldEntity entity = new SoraShieldEntity(EntityInit.SORA_SHIELD_ENTITY.get(),this.sora.level());
        entity.setPos(this.sora.position());
        entity.host=this.sora;
        this.sora.level().addFreshEntity(entity);
    }
    @Override
    public void stop(){
        this.sora.setSyncBoolean(Sora.BARRIER,false);
        if(this.sora.getSkillEnabled()[3]){
            this.doShieldInvert();
            this.sora.absorbeddamage=0;
        }
        this.sora.setInvulnerable(false);
    }
    @Override
    public void tick(){

    }
}
