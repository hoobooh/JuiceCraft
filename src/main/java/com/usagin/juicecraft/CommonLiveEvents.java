package com.usagin.juicecraft;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.ai.awareness.FriendDefense;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import static com.usagin.juicecraft.Init.UniversalSoundInit.COUNTER_BLOCK;
import static com.usagin.juicecraft.friends.Friend.FRIEND_ISDYING;

@Mod.EventBusSubscriber
public class CommonLiveEvents {
    @SubscribeEvent
    public static void onFriendHurt(LivingAttackEvent event) {
        if (event.getEntity() instanceof Friend friend) {
            if (!friend.level().isClientSide()) {
                if (event.getSource().getEntity() != null && !friend.isDying) {
                    if (friend.getAttackType() == 50 && friend.getAttackCounter() > 26/friend.getAttackSpeed()) {
                        event.setCanceled(true);
                    } else if (FriendDefense.shouldDefendAgainst(friend)) {
                        friend.setAttackCounter((int) (34/friend.getAttackSpeed()));
                        friend.setAttackType(50);
                        friend.playTimedVoice(friend.getEvade());
                        friend.playSound(COUNTER_BLOCK.get());
                        event.setCanceled(true);
                    }
                }
                if (!event.isCanceled()) {
                    if (event.getAmount() >= friend.getHealth()) {
                        friend.setTarget(null);
                        if (!friend.isDying) {
                            if (event.getSource() != null) {
                                friend.deathSource = event.getSource();
                            }
                            friend.deathTimer = 200;
                            if (!friend.level().isClientSide) {
                                friend.setDeathAnimCounter(60);
                            }
                            friend.getNavigation().stop();
                            friend.setIsDying(true);
                        }
                        friend.setHealth(0.1F);
                        if (friend.deathCounter != 7 - friend.getRecoveryDifficulty()) {
                            friend.deathCounter--;
                        }
                        if (friend.deathCounter >= 0) {
                            event.setCanceled(true);
                            return;
                        } else {
                            friend.doDeathEvent();
                        }
                    }
                    friend.playVoice(friend.getHurt(event.getAmount()));
                    for (int i = 3; i < 7; i++) {
                        if (!friend.inventory.getItem(i).isEmpty()) {
                            if (i == 3) {
                                friend.inventory.getItem(i).hurtAndBreak((int) event.getAmount(), friend, (a) -> friend.broadcastBreakEvent(EquipmentSlot.HEAD));
                            } else if (i == 4) {
                                friend.inventory.getItem(i).hurtAndBreak((int) event.getAmount(), friend, (a) -> friend.broadcastBreakEvent(EquipmentSlot.CHEST));
                            } else if (i == 5) {
                                friend.inventory.getItem(i).hurtAndBreak((int) event.getAmount(), friend, (a) -> friend.broadcastBreakEvent(EquipmentSlot.LEGS));
                            } else {
                                friend.inventory.getItem(i).hurtAndBreak((int) event.getAmount(), friend, (a) -> friend.broadcastBreakEvent(EquipmentSlot.FEET));
                            }
                        }
                    }
                }
                friend.updateGear();
            }
        }
    }

    @SubscribeEvent
    public static void onHostileSpawn(MobSpawnEvent event) {
        if (event.getEntity() instanceof Monster mon) {
            mon.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mon, Friend.class, true));
        }
    }

    @SubscribeEvent
    public static void onFriendKill(LivingDeathEvent event) {
        if (event.getSource() != null) {

            //when friend kills something

            if (event.getSource().getDirectEntity() instanceof Friend pFriend) {
                if (event.getEntity() instanceof Enemy) {
                    pFriend.setFriendEnemiesKilled(pFriend.getFriendEnemiesKilled() + 1);
                }

                //XP CALC EVENT
                float temp = (float) EnemyEvaluator.calculateNetGain(pFriend, event.getEntity());
                if(temp>50){
                    pFriend.appendEventLog(Component.translatable("juicecraft.menu." + pFriend.getFriendName().toLowerCase()+".eventlog.killhighratingfirst").getString() + event.getEntity().getDisplayName().getString() + Component.translatable("juicecraft.menu." + pFriend.getFriendName().toLowerCase()+".eventlog.killhighratingsecond").getString());
                }
                pFriend.updateFriendNorma(temp/1000,1);
                pFriend.increaseEXP(temp);
            }

            //when villager dies
            if(event.getEntity() instanceof Villager villager){
                if(event.getSource().getDirectEntity() instanceof Player player){

                    AABB detect = new AABB(villager.getX()-8,villager.getY()-8,villager.getZ()-8,villager.getX()+8,villager.getY()+8,villager.getZ()+8);
                    for(LivingEntity entity: villager.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(),villager,detect)){
                        if(entity instanceof Friend friend){
                            if(friend.isTame() && friend.getOwner()!=null){
                                if(friend.getOwner().getStringUUID().equals(player.getStringUUID())){
                                    if(friend.aggression<50){
                                        friend.playTimedVoice(friend.getAngry());
                                        friend.updateFriendNorma(-0.4F,1);
                                    } else if (friend.aggression > 90) { //starbo
                                        friend.playTimedVoice(friend.getLaugh());
                                        friend.updateFriendNorma(0.05F,1);
                                    }
                                }
                            }
                        }
                    }

                }
            }

            //player death
            else if(event.getEntity() instanceof Player player){
                AABB detect = new AABB(player.getX()-16,player.getY()-16,player.getZ()-16,player.getX()+16,player.getY()+16,player.getZ()+16);
                for(LivingEntity entity: player.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(),player,detect)){
                    if(entity instanceof Friend friend){
                        if(friend.isTame() && friend.getOwner()!=null){
                            if(friend.getOwner().getStringUUID().equals(player.getStringUUID())){
                                friend.appendEventLog(player.getScoreboardName() + Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase()+".eventlog.playerdeath").getString());
                            }
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void onCreeperPrime(LivingEvent event){
        if(event.getEntity() instanceof Creeper creeper){
            if(creeper.getSwellDir()==1){
                AABB detect = new AABB(creeper.getX()-8,creeper.getY()-8,creeper.getZ()-8,creeper.getX()+8,creeper.getY()+8,creeper.getZ()+8);
                for(LivingEntity entity: creeper.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(),creeper,detect)){
                    if(entity instanceof Friend friend){
                        friend.fleeTarget=creeper;
                    }
                }
            }
        }
    }
}
