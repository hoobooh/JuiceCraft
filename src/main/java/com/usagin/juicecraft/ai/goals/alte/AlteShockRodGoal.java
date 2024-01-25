package com.usagin.juicecraft.ai.goals.alte;

import com.usagin.juicecraft.Init.sounds.AlteSoundInit;
import com.usagin.juicecraft.friends.Alte;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SnowballItem;

import static com.usagin.juicecraft.friends.Friend.LOGGER;

public class AlteShockRodGoal extends Goal {
    protected final Alte alte;
    protected LivingEntity target;
    public AlteShockRodGoal(Alte alte){
        this.alte=alte;
    }

    @Override
    public boolean canUse() {
        this.target=this.alte.getTarget();
        Item item = this.alte.getFriendWeapon().getItem();
        boolean flag = item instanceof BowItem || item instanceof SnowballItem || item instanceof CrossbowItem;
        return this.alte.getSkillEnabled()[2] && !this.alte.isUsingHyper() && this.alte.canDoThings() && this.alte.getAlteSyncInt(Alte.ALTE_RODCOOLDOWN) >= 12000 && this.alte.getPose()!= Pose.SLEEPING && this.target!=null && !this.alte.areAnimationsBusy() && !flag;
    }
    @Override
    public boolean canContinueToUse(){
        Item item = this.alte.getFriendWeapon().getItem();
        boolean flag = item instanceof BowItem || item instanceof SnowballItem || item instanceof CrossbowItem;
        return this.alte.getSkillEnabled()[2] && this.alte.getRodDuration() > this.alte.getAlteSyncInt(Alte.ALTE_RODCOOLDOWN) && !this.alte.getIsDying()&&!flag;
    }
    @Override
    public void start(){
        this.alte.playSound(AlteSoundInit.ALTE_DRAW.get(),2,1);
        this.alte.setAlteSyncInt(Alte.ALTE_RODCOOLDOWN,0);
        this.alte.setAlteSyncInt(Alte.ALTE_RODSUMMONCOUNTER,30);
    }
    @Override
    public void stop(){
        this.alte.setAlteSyncInt(Alte.ALTE_RODSHEATHCOUNTER,25);
    }
}
