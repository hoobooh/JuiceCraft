package com.usagin.juicecraft.items;

import com.usagin.juicecraft.Init.sounds.UniversalSoundInit;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

import static com.usagin.juicecraft.Init.ItemInit.*;

public class SweetHandler {
    public static void doSweetEffect(Friend friend, ItemStack pStack) {
        ServerLevel sLevel = (ServerLevel) friend.level();
        if (pStack.is(PUDDING.get())) {
            friend.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 2000, 1));
            playVoice(friend, friend.getOnHeal());
            friend.setHungerMeter(Mth.clamp(friend.getHungerMeter() + 5, 0, 100));
            friend.playSound(SoundEvents.GENERIC_EAT, 1, 1);
            sLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, friend.getX(), friend.getY() + 2, friend.getZ(), 6, 0.3, 0.3, 0.3, 0.4);
            sendParticles(sLevel, friend, pStack);
            friend.mood = Mth.clamp(friend.mood + 20, 0, 100);
        } else if (pStack.is(REDBEANICECREAM.get())) {
            friend.appendEventLog(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".eventlog.miraclebean").getString());
            friend.setHealth(friend.getMaxHealth());
            friend.setHungerMeter(100);
            playVoice(friend, friend.getOnHeal());
            friend.mood = 100;
            friend.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2000, 3));
            friend.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 2000, 3));
            friend.playSound(SoundEvents.GENERIC_EAT, 1, 1);
            friend.playSound(UniversalSoundInit.HYPER_EQUIP.get(), 0.3F, 1);
            friend.spawnHorizontalParticles();
            sendParticles(sLevel, friend, pStack);
        } else if (pStack.is(CANDY.get())) {
            friend.setHungerMeter(Mth.clamp(friend.getHungerMeter() + Objects.requireNonNull(pStack.getFoodProperties(friend)).getNutrition(), 0, 100));
            sendParticles(sLevel, friend, pStack);
            friend.playSound(SoundEvents.GENERIC_EAT, 1, 1);
            friend.mood = Mth.clamp(friend.mood + 1, 0, 100);
        } else if (pStack.is(ALTESCOOKING.get())) {
            friend.appendEventLog(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".eventlog.altesmeal").getString());
            friend.playVoice(friend.getHurt(100000));
            friend.setHealth(friend.getHealth() - 10);
            friend.setHungerMeter(Mth.clamp(friend.getHungerMeter() - 10, 0, 100));
            friend.playSound(SoundEvents.GENERIC_EAT, 1, 1);
            sLevel.sendParticles(ParticleTypes.ANGRY_VILLAGER, friend.getX(), friend.getY() + 2, friend.getZ(), 6, 0.3, 0.3, 0.3, 0.4);
            sendParticles(sLevel, friend, pStack);
            friend.mood = Mth.clamp(friend.mood - 40, 0, 100);
        } else if (pStack.is(SAKISCOOKIE.get())) {
            friend.setFriendNorma(5, -1);
            friend.setHealth(friend.getHealth() + 10);
            playVoice(friend, friend.getOnHeal());
            friend.setHungerMeter(Mth.clamp(friend.getHungerMeter() + 10, 0, 100));
            friend.playSound(SoundEvents.GENERIC_EAT, 1, 1);
            sLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, friend.getX(), friend.getY() + 2, friend.getZ(), 6, 0.3, 0.3, 0.3, 0.4);
            sendParticles(sLevel, friend, pStack);
            friend.mood = Mth.clamp(friend.mood + 20, 0, 100);
        } else if (pStack.is(RAWSEAGULL.get())) {
            friend.playSound(SoundEvents.GENERIC_EAT, 1, 1);
            sLevel.sendParticles(ParticleTypes.ANGRY_VILLAGER, friend.getX(), friend.getY() + 2, friend.getZ(), 6, 0.3, 0.3, 0.3, 0.4);
            sendParticles(sLevel, friend, pStack);
            friend.mood = Mth.clamp(friend.mood - 20, 0, 100);
            friend.setHungerMeter(Mth.clamp(friend.getHungerMeter() + 3, 0, 100));
        } else if (pStack.is(COOKEDSEAGULL.get())) {
            friend.playSound(SoundEvents.GENERIC_EAT, 1, 1);
            sendParticles(sLevel, friend, pStack);
            friend.mood = Mth.clamp(friend.mood + 1, 0, 100);
            friend.setHungerMeter(Mth.clamp(friend.getHungerMeter() + 6, 0, 100));
        } else if (pStack.is(Items.COOKIE)) {
            playVoice(friend, friend.getOnHeal());
            friend.setHealth(friend.getHealth() + 1);
            friend.playSound(SoundEvents.GENERIC_EAT, 1, 1);
            sendParticles(sLevel, friend, pStack);
        } else { //regular oranges
            friend.setHungerMeter(Mth.clamp(friend.getHungerMeter() + Objects.requireNonNull(pStack.getFoodProperties(friend)).getNutrition(), 0, 100));
            friend.playSound(SoundEvents.GENERIC_EAT, 1, 1);
            friend.mood = Mth.clamp(friend.mood + 1, 0, 100);
        }
    }

    static void playVoice(Friend friend, SoundEvent sound) {
        friend.playTimedVoice(sound);
    }

    static void sendParticles(ServerLevel sLevel, Friend friend, ItemStack pStack) {
        spawnItemParticles(pStack, 4, friend, sLevel);
        //sLevel.sendParticles(temp,friend.getX(),friend.getY()+friend.getEyeHeight(friend.getPose()),friend.getZ(),10,0.1,0.02,0.1,0.01);
    }

    private static void spawnItemParticles(ItemStack pStack, int pAmount, Friend friend, ServerLevel sLevel) {
        for (int i = 0; i < pAmount; ++i) {
            Vec3 vec3 = new Vec3(((double) friend.getRandom().nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vec3 = vec3.xRot(-friend.getXRot() * ((float) Math.PI / 180F));
            vec3 = vec3.yRot(-friend.getYRot() * ((float) Math.PI / 180F));
            double d0 = (double) (-friend.getRandom().nextFloat()) * 0.6D - 0.3D;
            Vec3 vec31 = new Vec3(((double) friend.getRandom().nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
            vec31 = vec31.add(-0.2, 0, -0.2);
            vec31 = vec31.xRot(-friend.getXRot() * ((float) Math.PI / 180F));
            vec31 = vec31.yRot(-friend.getYRot() * ((float) Math.PI / 180F));
            vec31 = vec31.add(friend.getX(), friend.getEyeY() + friend.getEyeHeight(friend.getPose()), friend.getZ());
            sLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, pStack), vec31.x, vec31.y - 0.7, vec31.z, 1, vec3.x, vec3.y, vec3.z, 0.0D);
        }

    }
}
