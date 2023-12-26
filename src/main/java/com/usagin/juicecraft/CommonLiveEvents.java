package com.usagin.juicecraft;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.ai.awareness.FriendDefense;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
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

import static com.usagin.juicecraft.Init.UniversalSoundInit.COUNTER_BLOCK;
import static com.usagin.juicecraft.friends.Friend.FRIEND_ISDYING;

@Mod.EventBusSubscriber
public class CommonLiveEvents {
    @SubscribeEvent
    public static void onFriendHurt(LivingAttackEvent event) {
        if (event.getEntity() instanceof Friend friend) {
            if (!friend.level().isClientSide()) {
                if (event.getSource().getEntity() != null && !friend.isDying) {
                    if (friend.getAttackType() == 50 && friend.getAttackCounter() > 26) {
                        event.setCanceled(true);
                    } else if (FriendDefense.shouldDefendAgainst(friend)) {
                        friend.setAttackCounter(34);
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
            if (event.getSource().getDirectEntity() instanceof Friend pFriend) {
                if (event.getEntity() instanceof Enemy) {
                    pFriend.setFriendEnemiesKilled(pFriend.getFriendEnemiesKilled() + 1);
                }

                //XP CALC EVENT
                pFriend.increaseEXP(EnemyEvaluator.calculateNetGain(pFriend, event.getEntity()));
            }
        }
    }
}
