package com.usagin.juicecraft;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
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
            friend.updateGear();
            if(event.getAmount() >= friend.getHealth()){
                friend.setTarget(null);
                if(!friend.isDying){
                    friend.deathSource = event.getSource();
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
}
