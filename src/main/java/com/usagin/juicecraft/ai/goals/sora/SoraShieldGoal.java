package com.usagin.juicecraft.ai.goals.sora;

import com.usagin.juicecraft.Init.EntityInit;
import com.usagin.juicecraft.Init.sounds.SoraSoundInit;
import com.usagin.juicecraft.friends.Sora;
import com.usagin.juicecraft.miscentities.SoraShieldEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SoraShieldGoal extends Goal {
    public final Sora sora;
    public final LivingEntity owner;
    public SoraShieldGoal(Sora sora){
        this.sora=sora;
        this.owner=sora.getOwner();
    }

    @Override
    public boolean canUse() {
        return (this.sora.canDoThings() && this.sora.shieldcooldown <= 0 && this.sora.isAggressive() && this.sora.getSkillEnabled()[2] || this.sora.usingshield);
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

        if(this.sora.shieldduration!=0){
            this.sora.usingshield=true;
        this.sora.shieldcooldown=1200;
        this.sora.playVoice(SoraSoundInit.SORA_SHIELD.get(),true);
        this.sora.playSound(SoraSoundInit.SHIELD_START.get());
        SoraShieldEntity entity = new SoraShieldEntity(EntityInit.SORA_SHIELD_ENTITY.get(),this.sora.level());
        entity.setPos(this.sora.position());
        entity.host=this.sora;
        entity.lifetime= (int) this.getShieldDuration();
        entity.damagetaken=0;
        this.sora.level().addFreshEntity(entity);

        if(this.owner != null){
            entity = new SoraShieldEntity(EntityInit.SORA_SHIELD_ENTITY.get(),this.sora.level());
            entity.setPos(this.owner.position());
            entity.host=this.owner;
            entity.lifetime= (int) this.getShieldDuration();
            entity.damagetaken=0;
            this.owner.level().addFreshEntity(entity);
        }
        }
    }
    @Override
    public void stop(){
        this.sora.shieldduration=0;
        if(this.sora.getSkillEnabled()[3]){
            this.doShieldInvert();
        }
        this.sora.usingshield=false;
    }
    public void doShieldInvert(){
        this.sora.playVoice(SoraSoundInit.SORA_SHIELD_INVERT.get(), true)
        this.broadcastToAllShields();
    }
    public void broadcastToAllShields(){
        AABB box = this.sora.getBoundingBox().inflate(20);
        List<Entity> list = this.sora.level().getEntities(this.sora,box);
        for(Entity entity: list){
            if(entity instanceof SoraShieldEntity shield){
                shield.releaseEnergy(this.sora);
            }
        }
    }
    @Override
    public void tick(){
        this.sora.shieldduration++;
    }
}
