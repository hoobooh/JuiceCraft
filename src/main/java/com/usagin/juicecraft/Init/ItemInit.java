package com.usagin.juicecraft.Init;

import com.usagin.juicecraft.items.SweetItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> ORANGE = ITEMS.register("orange", () -> new SweetItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationMod(1F).build())));
    public static final RegistryObject<Item> GOLDEN_ORANGE = ITEMS.register("golden_orange", () -> new SweetItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(3).saturationMod(3F).effect(new MobEffectInstance(MobEffects.LUCK, 2400, 1), 1.0F).effect(new MobEffectInstance(MobEffects.UNLUCK, 2400, 0), 1.0F).alwaysEat().build())));
    public static final RegistryObject<Item> SUMIKA_MEMORY = ITEMS.register("sumikas_memory", () ->  new Item(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> ACTIVATOR = ITEMS.register("activator", () -> new Item(new Item.Properties().stacksTo(1).durability(24000)));

    //SWEETS

    public static final RegistryObject<Item> PUDDING = ITEMS.register("pudding", () -> new SweetItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(3).saturationMod(3F).effect(new MobEffectInstance(MobEffects.HEAL, 1, 20),1F).build())));
    public static final RegistryObject<Item> REDBEANICECREAM = ITEMS.register("redbeanicecream", () -> new SweetItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(6).saturationMod(6F).build())));
    public static final RegistryObject<Item> CANDY = ITEMS.register("candy", () -> new SweetItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationMod(1F).build())));
    public static final RegistryObject<Item> ALTESCOOKING = ITEMS.register("altescooking", () -> new SweetItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(-5).saturationMod(0F).effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 1),1F).build())));
    public static final RegistryObject<Item> SAKISCOOKIE = ITEMS.register("sakiscookie", () -> new SweetItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(8).saturationMod(3F).effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 600, 2),1F).build())));
    public static final RegistryObject<Item> RAWSEAGULL = ITEMS.register("rawseagull", () -> new SweetItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(3).saturationMod(1F).effect(new MobEffectInstance(MobEffects.HUNGER, 400, 1),1F).build())));
    public static final RegistryObject<Item> COOKEDSEAGULL = ITEMS.register("cookedseagull", () -> new SweetItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(6).saturationMod(3F).effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 1),0.17F).build())));

}
