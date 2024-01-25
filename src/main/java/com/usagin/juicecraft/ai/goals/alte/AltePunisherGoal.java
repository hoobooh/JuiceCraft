package com.usagin.juicecraft.ai.goals.alte;

import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Alte;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class AltePunisherGoal extends Goal {
    protected final Alte alte;
    protected LivingEntity target;
    public AltePunisherGoal(Alte alte){
        this.alte=alte;
    }

    @Override
    public boolean canUse() {
        this.target=this.alte.getTarget();
        Item item = this.alte.getFriendWeapon().getItem();
        boolean flag = item instanceof BowItem || item instanceof SnowballItem || item instanceof CrossbowItem;
        return this.alte.getSkillEnabled()[3] && this.alte.getSkillEnabled()[2] && !this.alte.isUsingHyper() && this.alte.canDoThings() && this.alte.punishercooldown <=0 && this.alte.getPose()!= Pose.SLEEPING && this.target!=null && !this.alte.areAnimationsBusy() && !flag;
    }
    protected LivingEntity findPriorityTarget(){
        AABB box = this.alte.getBoundingBox().inflate(15);
        List<Entity> list = this.alte.level().getEntities(this.alte,box);
        LivingEntity finalTarget=this.target;
        for(Entity e: list){
            if(e instanceof LivingEntity ent){
                if(EnemyEvaluator.shouldDoHurtTarget(this.alte,ent)){
                    if(this.alte.distanceTo(finalTarget)<this.alte.distanceTo(ent)){
                        if(this.alte.level().clip(new ClipContext(this.alte.position(),ent.position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.alte)).getType()== HitResult.Type.MISS){
                            finalTarget=ent;
                        }
                    }
                }
            }
        }
        return finalTarget;
    }
    @Override
    public boolean canContinueToUse(){
        Item item = this.alte.getFriendWeapon().getItem();
        boolean flag = item instanceof BowItem || item instanceof SnowballItem || item instanceof CrossbowItem;
        return this.alte.canDoThings() && this.alte.getAlteSyncInt(Alte.ALTE_PUNISHERCOUNTER)>0 && !flag;
    }

    @Override
    public void start(){

    }
    @Override
    public void stop(){

    }
    @Override
    public void tick(){

    }
}
