package com.usagin.juicecraft.ai.awareness;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;

public class EnemyEvaluator {
    static Logger LOGGER = LogUtils.getLogger();
    public static int evaluate(LivingEntity pEntity){
        int danger=0;
        float health = pEntity.getMaxHealth();

        double attack=0;
        try{
       attack = pEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);}catch(Exception e){//do nothing
            }

        double speed=0;
        try{speed=pEntity.getAttributeValue(Attributes.MOVEMENT_SPEED);}catch(Exception e) {//do nothing
        }

        double size = pEntity.getBbHeight()*pEntity.getBbWidth();
        int effectCount = pEntity.getActiveEffects().size();
        double armor = pEntity.getArmorValue();
        boolean familiar = pEntity.getMobType()!=MobType.UNDEFINED;
        danger+=1.04* Mth.clamp(health,1,Math.abs(health))*Mth.clamp(armor,1,Math.abs(armor));
        danger+=attack*10;
        danger+=(speed)*20;
        danger+=size*size;
        danger*=(effectCount/2+1);
        if(!familiar){
            danger*=3;
        }
        return danger;
    }
    public static int evaluateAreaDanger(Friend friend){
        AABB detect = new AABB(friend.getX()-20,friend.getY()-20,friend.getZ()-20,friend.getX()+20,friend.getY()+20,friend.getZ()+20);
        int danger=0;
        for(LivingEntity entity: friend.level().getNearbyEntities(LivingEntity.class,TargetingConditions.forCombat(),friend,detect)){
            danger+=evaluate(entity);
        }
        LOGGER.info("Evaluated Area Danger Level: " + danger);
        return danger;
    }
    public static int evaluateEnemyAreaDanger(LivingEntity friend){
        AABB detect = new AABB(friend.getX()-20,friend.getY()-20,friend.getZ()-20,friend.getX()+20,friend.getY()+20,friend.getZ()+20);
        int danger=0;
        for(LivingEntity entity: friend.level().getNearbyEntities(LivingEntity.class,TargetingConditions.forCombat(),friend,detect)){
            danger+=evaluate(entity);
        }
        LOGGER.info("Evaluated Target Area Danger Level: " + danger);
        return danger;
    }

    public static double calculateNetGain(Friend friend, LivingEntity target){
        double totalXP = EnemyEvaluator.evaluate(target);
        double netXP=0;

        float tempexp = friend.getFriendExperience();
        int xpCount=0;
        while(xpCount<=totalXP){
            float currentexp=tempexp;
            for(int i=0;i<5;i++){
                tempexp+=(40*totalXP/(tempexp+100)/5);
                xpCount+=20;
            }
            netXP+=tempexp-currentexp;
        }

        return netXP*friend.getCombatAffinityModifier();
    }
}
