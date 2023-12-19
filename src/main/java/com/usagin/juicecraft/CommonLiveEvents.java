package com.usagin.juicecraft;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import static com.usagin.juicecraft.friends.Friend.FRIEND_ISDYING;

@Mod.EventBusSubscriber
public class CommonLiveEvents {
    @SubscribeEvent
    public static void onFriendHurt(LivingAttackEvent event){
        if(event.getEntity() instanceof Friend friend){
            Logger LOGGER = LogUtils.getLogger();
            for(int i=3;i<7;i++){
                if(!friend.inventory.getItem(i).isEmpty()){
                    if(i==3){
                        friend.inventory.getItem(i).hurtAndBreak((int) event.getAmount(),friend,(a) -> friend.broadcastBreakEvent(EquipmentSlot.HEAD));
                    }
                    else if(i==4){
                        friend.inventory.getItem(i).hurtAndBreak((int) event.getAmount(),friend,(a) -> friend.broadcastBreakEvent(EquipmentSlot.CHEST));
                    }
                    else if(i==5){
                        friend.inventory.getItem(i).hurtAndBreak((int) event.getAmount(),friend,(a) -> friend.broadcastBreakEvent(EquipmentSlot.LEGS));
                    }
                    else{
                        friend.inventory.getItem(i).hurtAndBreak((int) event.getAmount(),friend,(a) -> friend.broadcastBreakEvent(EquipmentSlot.FEET));
                    }
                }
            }
            friend.updateGear();
            if(event.getAmount() >= friend.getHealth()){
                friend.setTarget(null);
                if(!friend.isDying){
                    if (event.getSource() != null) {
                        friend.deathSource = event.getSource();
                    }
                    friend.deathTimer=200;
                    friend.setDeathAnimCounter(60);
                    friend.setIsDying(true);
                }
                friend.setHealth(0.1F);
                if(friend.deathCounter!= 7- friend.getRecoveryDifficulty()){
                    LOGGER.info(friend.deathCounter +"");
                    friend.deathCounter--;
                }
                if(friend.deathCounter>=0){
                    event.setCanceled(true);
                }
                else{
                    friend.doDeathEvent();
                }
            }
            if(!event.isCanceled()){
                friend.playVoice(friend.getHurt(event.getAmount()));
            }
        }
    }
    @SubscribeEvent
    public static void onHostileSpawn(MobSpawnEvent event){
        if(event.getEntity() instanceof Monster mon){
            mon.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mon, Friend.class, true));
        }
    }
    @SubscribeEvent
    public static void onFriendKill(LivingDeathEvent event){
        if(event.getSource()!=null){
            if(event.getSource().getDirectEntity() instanceof Friend pFriend){
                if(event.getEntity() instanceof Enemy){
                    pFriend.setFriendEnemiesKilled(pFriend.getFriendEnemiesKilled()+1);
                }
                //make enemy evaluation code
            }
        }
    }
}
