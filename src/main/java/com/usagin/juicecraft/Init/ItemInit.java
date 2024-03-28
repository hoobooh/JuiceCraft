package com.usagin.juicecraft.Init;

import com.usagin.juicecraft.items.*;
import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BedItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.usagin.juicecraft.Init.BlockInit.*;
import static com.usagin.juicecraft.JuiceCraft.MODID;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> ORANGE = ITEMS.register("orange", () -> new SweetItem(new JuiceCraftItem.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationMod(1F).build())));
    public static final RegistryObject<Item> GOLDEN_ORANGE = ITEMS.register("golden_orange", () -> new SweetItem(new JuiceCraftItem.Properties().food((new FoodProperties.Builder()).nutrition(3).saturationMod(3F).effect(new MobEffectInstance(MobEffects.LUCK, 2400, 1), 1.0F).effect(new MobEffectInstance(MobEffects.UNLUCK, 2400, 0), 1.0F).alwaysEat().build())));
    public static final RegistryObject<Item> SUMIKA_MEMORY = ITEMS.register("sumikas_memory", () -> new JuiceCraftItem(new JuiceCraftItem.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> ACTIVATOR = ITEMS.register("activator", () -> new JuiceCraftItem(new JuiceCraftItem.Properties().stacksTo(1).durability(24000).defaultDurability(24000)));

    //SWEETS

    public static final RegistryObject<Item> PUDDING = ITEMS.register("pudding", () -> new SweetItem(new JuiceCraftItem.Properties().food((new FoodProperties.Builder()).nutrition(3).saturationMod(3F).effect(new MobEffectInstance(MobEffects.HEAL, 1, 20), 1F).build())));
    public static final RegistryObject<Item> REDBEANICECREAM = ITEMS.register("redbeanicecream", () -> new SweetItem(new JuiceCraftItem.Properties().food((new FoodProperties.Builder()).nutrition(6).saturationMod(6F).build())));
    public static final RegistryObject<Item> CANDY = ITEMS.register("candy", () -> new SweetItem(new JuiceCraftItem.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationMod(1F).build())));
    public static final RegistryObject<Item> ALTESCOOKING = ITEMS.register("altescooking", () -> new SweetItem(new JuiceCraftItem.Properties().food((new FoodProperties.Builder()).nutrition(-5).saturationMod(0F).effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 1), 1F).build())));
    public static final RegistryObject<Item> SAKISCOOKIE = ITEMS.register("sakiscookie", () -> new SweetItem(new JuiceCraftItem.Properties().food((new FoodProperties.Builder()).nutrition(8).saturationMod(3F).effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 600, 2), 1F).build())));
    public static final RegistryObject<Item> RAWSEAGULL = ITEMS.register("rawseagull", () -> new SweetItem(new JuiceCraftItem.Properties().food((new FoodProperties.Builder()).nutrition(3).saturationMod(1F).effect(new MobEffectInstance(MobEffects.HUNGER, 400, 1), 1F).build())));
    public static final RegistryObject<Item> COOKEDSEAGULL = ITEMS.register("cookedseagull", () -> new SweetItem(new JuiceCraftItem.Properties().food((new FoodProperties.Builder()).nutrition(6).saturationMod(3F).effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 1), 0.17F).build())));

    //SPAWN EGGS
    public static final RegistryObject<Item> ALTE_SPAWN_EGG = ITEMS.register("alte_spawn_egg", () -> new ForgeSpawnEggItem(EntityInit.ALTE, ChatFormatting.LIGHT_PURPLE.getColor(), ChatFormatting.DARK_PURPLE.getColor(), new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> SORA_SPAWN_EGG = ITEMS.register("sora_spawn_egg", () -> new ForgeSpawnEggItem(EntityInit.SORA, ChatFormatting.YELLOW.getColor(), ChatFormatting.GREEN.getColor(), new JuiceCraftItem.Properties()));


    //INGREDIENTS
    public static final RegistryObject<Item> WHITE_SEAGULL_FLUFF = ITEMS.register("white_seagull_fluff", () -> new FriendFluffItem(new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> ORANGE_SEAGULL_FLUFF = ITEMS.register("orange_seagull_fluff", () -> new FriendFluffItem(new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> YELLOW_SEAGULL_FLUFF = ITEMS.register("yellow_seagull_fluff", () -> new FriendFluffItem(new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> BLACK_SEAGULL_FLUFF = ITEMS.register("black_seagull_fluff", () -> new FriendFluffItem(new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> RED_SEAGULL_FLUFF = ITEMS.register("red_seagull_fluff", () -> new FriendFluffItem(new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> BLUE_SEAGULL_FLUFF = ITEMS.register("blue_seagull_fluff", () -> new FriendFluffItem(new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> GRAY_SEAGULL_FLUFF = ITEMS.register("gray_seagull_fluff", () -> new FriendFluffItem(new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> PINK_SEAGULL_FLUFF = ITEMS.register("pink_seagull_fluff", () -> new FriendFluffItem(new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> GREEN_SEAGULL_FLUFF = ITEMS.register("green_seagull_fluff", () -> new FriendFluffItem(new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> PURPLE_SEAGULL_FLUFF = ITEMS.register("purple_seagull_fluff", () -> new FriendFluffItem(new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> CYAN_SEAGULL_FLUFF = ITEMS.register("cyan_seagull_fluff", () -> new FriendFluffItem(new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> BROWN_SEAGULL_FLUFF = ITEMS.register("brown_seagull_fluff", () -> new FriendFluffItem(new JuiceCraftItem.Properties()));


    //BLOCK ITEMS

    public static final RegistryObject<Item> ALTE_PLUSHIE = ITEMS.register("alte_plushie", () -> new FriendBlockItem(BlockInit.ALTE_PLUSHIE.get(),new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> SORA_PLUSHIE = ITEMS.register("sora_plushie", () -> new FriendBlockItem(BlockInit.SORA_PLUSHIE.get(),new JuiceCraftItem.Properties()));

    //BED ITEMS

    public static final RegistryObject<Item> YELLOW_FRIEND_BED = ITEMS.register("yellow_friend_bed", () -> new FriendBedItem(YELLOW_FRIEND_BED_BLOCK.get(),new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> WHITE_FRIEND_BED = ITEMS.register("white_friend_bed", () -> new FriendBedItem(WHITE_FRIEND_BED_BLOCK.get(),new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> ORANGE_FRIEND_BED = ITEMS.register("orange_friend_bed", () -> new FriendBedItem(ORANGE_FRIEND_BED_BLOCK.get(),new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> BLACK_FRIEND_BED = ITEMS.register("black_friend_bed", () -> new FriendBedItem(BLACK_FRIEND_BED_BLOCK.get(),new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> RED_FRIEND_BED = ITEMS.register("red_friend_bed", () -> new FriendBedItem(RED_FRIEND_BED_BLOCK.get(),new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> BLUE_FRIEND_BED = ITEMS.register("blue_friend_bed", () -> new FriendBedItem(BLUE_FRIEND_BED_BLOCK.get(),new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> GRAY_FRIEND_BED = ITEMS.register("gray_friend_bed", () -> new FriendBedItem(GRAY_FRIEND_BED_BLOCK.get(),new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> PINK_FRIEND_BED = ITEMS.register("pink_friend_bed", () -> new FriendBedItem(PINK_FRIEND_BED_BLOCK.get(),new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> GREEN_FRIEND_BED = ITEMS.register("green_friend_bed", () -> new FriendBedItem(GREEN_FRIEND_BED_BLOCK.get(),new JuiceCraftItem.Properties()));
    public static final RegistryObject<Item> PURPLE_FRIEND_BED = ITEMS.register("purple_friend_bed", () -> new FriendBedItem(PURPLE_FRIEND_BED_BLOCK.get(),new JuiceCraftItem.Properties()));

    public static final RegistryObject<Item> CYAN_FRIEND_BED = ITEMS.register("cyan_friend_bed", () -> new FriendBedItem(CYAN_FRIEND_BED_BLOCK.get(),new JuiceCraftItem.Properties()));

    public static final RegistryObject<Item> BROWN_FRIEND_BED = ITEMS.register("brown_friend_bed", () -> new FriendBedItem(BROWN_FRIEND_BED_BLOCK.get(),new JuiceCraftItem.Properties()));
}
