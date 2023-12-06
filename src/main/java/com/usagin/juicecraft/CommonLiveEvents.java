package com.usagin.juicecraft;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.usagin.juicecraft.friends.Friend.FRIEND_ISDYING;

@Mod.EventBusSubscriber
public class CommonLiveEvents {
    @SubscribeEvent
    public static void doFriendHurt(LivingAttackEvent event){
        //this will probably be moved somewhere else
        if(event.getSource().getDirectEntity() instanceof Friend friend){
            friend.playSound(friend.getAttack());
            friend.inventory.getItem(1).hurt(1, friend.getRandom(),null);
            friend.updateGear();
        }
    }
    @SubscribeEvent
    public static void onFriendHurt(LivingAttackEvent event){
        if(event.getEntity() instanceof Friend friend){
            for(int i=3; i<7;i++){
                friend.inventory.getItem(i).hurt((int) event.getAmount(), friend.getRandom(),null);
            }
            friend.updateGear();
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
    @SubscribeEvent
    public static void onHostileSpawn(MobSpawnEvent event){
        if(event.getEntity() instanceof Monster mon){
            mon.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mon, Friend.class, true));
        }
    }
}
