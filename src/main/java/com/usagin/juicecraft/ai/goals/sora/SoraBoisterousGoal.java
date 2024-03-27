package com.usagin.juicecraft.ai.goals.sora;

import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.Init.sounds.SoraSoundInit;
import com.usagin.juicecraft.Init.sounds.UniversalSoundInit;
import com.usagin.juicecraft.friends.Sora;
import com.usagin.juicecraft.particles.GenericLightningParticle;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SnowballItem;

public class SoraBoisterousGoal extends Goal {
    public final Sora sora;
    public SoraBoisterousGoal(Sora sora){
        this.sora=sora;
    }
    @Override
    public boolean canUse() {
        return ( this.sora.aggressiontimer > 60 && this.sora.getSkillEnabled()[4] && !this.sora.isUsingHyper() && this.sora.canDoThings() && this.sora.boisterouscooldown <= 0 && this.sora.getPose() != Pose.SLEEPING && !this.sora.areAnimationsBusy() && this.sora.getAttackCounter() <=0) || this.sora.isBoisterous();
    }
    @Override
    public boolean canContinueToUse(){
        return (this.sora.canDoThings() && this.sora.getSkillEnabled()[4] && this.sora.boisterousduration < this.getBoisterousMax()) || this.sora.getAttackCounter()>0;
    }
    public float getBoisterousMax(){
        int n = this.sora.getSkillLevels()[4];
        return 20 * (60F+(n/(n+10F)*60));
    }
    public void start(){
        if(!this.sora.isBoisterous() && this.sora.level() instanceof ServerLevel level){
            this.sora.setSyncBoolean(Sora.BOISTEROUS,true);
            this.sora.playVoice(SoraSoundInit.SORA_HYPERUSE.get(),true); //add sound
            this.sora.playSound(UniversalSoundInit.ACCELERATOR.get());
            this.sora.spawnParticlesInRandomSpreadAtEntity(this.sora,3,0.5F,0,level, ParticleInit.GENERIC_LIGHTNING_PARTICLE.get());
            this.sora.boisterousduration=0;
            this.sora.boisterouscooldown=12000;
        }
    }
    public void stop(){
        this.sora.setSyncBoolean(Sora.BOISTEROUS,false);
    }
    public void tick(){
        this.sora.boisterousduration++;
        if(this.sora.tickCount%5==0 && this.sora.level() instanceof ServerLevel level){
            this.sora.playSound(UniversalSoundInit.LIGHTNING.get(),this.sora.getRandom().nextFloat(),this.sora.getRandom().nextFloat());
            this.sora.spawnParticlesInRandomSpreadAtEntity(this.sora,3,0.5F,0,level, ParticleInit.GENERIC_LIGHTNING_PARTICLE.get());
        }
    }
}
