package com.usagin.juicecraft.ai.awareness;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
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
        float health = pEntity.getHealth();
        double attack = pEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
        double speed = pEntity.getAttributeValue(Attributes.MOVEMENT_SPEED);
        double size = pEntity.getBbHeight()*pEntity.getBbWidth();
        int effectCount = pEntity.getActiveEffects().size();
        double armor = pEntity.getArmorValue();
        boolean familiar = pEntity.getMobType()!=MobType.UNDEFINED;
        danger+=1.04*health*armor;
        danger+=attack*2;
        danger+=(speed-1)*20;
        danger+=size*size;
        danger*=(effectCount/2+1);
        if(!familiar){
            danger*=3;
        }
        LOGGER.info("Evaluated " +pEntity.getName().getString() + ": Danger Level " + danger);
        return danger;
    }
    public static int evaluateAreaDanger(Friend friend){
        AABB detect = new AABB(friend.getX()-20,friend.getY()-20,friend.getZ()-20,friend.getX()+20,friend.getY()+20,friend.getZ()+20);
        int danger=0;
        for(LivingEntity entity: friend.level().getNearbyEntities(Friend.class,TargetingConditions.forCombat(),friend,detect)){
            danger+=evaluate(entity);
        }
        LOGGER.info("Evaluated Area Danger Level: " + danger);
        return danger;
    }
}
