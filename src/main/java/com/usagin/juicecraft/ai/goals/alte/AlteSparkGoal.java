package com.usagin.juicecraft.ai.goals.alte;

import com.usagin.juicecraft.friends.Alte;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import static com.usagin.juicecraft.friends.Alte.*;

public class AlteSparkGoal extends Goal {
    protected final Alte alte;
    protected LivingEntity target;
    public AlteSparkGoal(Alte alte){
        this.alte=alte;
    }
    @Override
    public boolean canUse() {
        this.target=this.alte.getTarget();
        return this.alte.canDoThings() && this.target!=null && this.alte.getSkillEnabled()[1] && this.alte.sparkcooldown<=0;
    }
    @Override
    public void start(){
        this.alte.sparkcooldown=3600;
        this.alte.setAlteAnimCounter(ALTE_SPARKCOUNTER,30);
    }
}
