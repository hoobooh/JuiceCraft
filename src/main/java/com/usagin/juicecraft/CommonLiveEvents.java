package com.usagin.juicecraft;

import com.usagin.juicecraft.friends.Friend;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.usagin.juicecraft.friends.Friend.FRIEND_ISDYING;

@Mod.EventBusSubscriber
public class CommonLiveEvents {
    @SubscribeEvent
    public static void doFriendHurt(LivingAttackEvent event){
        if(event.getSource().getDirectEntity() instanceof Friend friend){
            friend.playSound(friend.getAttack());
        }
    }
    @SubscribeEvent
    public static void onFriendHurt(LivingAttackEvent event){
        if(event.getEntity() instanceof Friend friend){
            friend.playSound(friend.getHurt(event.getAmount()));
            if(event.getAmount() >= friend.getHealth()){
                if(friend.deathCounter!= 7- friend.recoveryDifficulty){
                    friend.deathCounter--;
                }
                else{
                    friend.getEntityData().set(FRIEND_ISDYING, true);
                    friend.isDying=true;
                }
                friend.setHealth(0.1F);
                if(friend.deathCounter>0){
                    event.setCanceled(true);
                }
                else{
                    friend.doDeathEvent();
                }
            }
        }
    }
}
