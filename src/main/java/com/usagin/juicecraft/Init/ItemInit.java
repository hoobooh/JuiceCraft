package com.usagin.juicecraft.Init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> ORANGE = ITEMS.register("orange", () -> new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationMod(1F).build())));
    public static final RegistryObject<Item> GOLDEN_ORANGE = ITEMS.register("golden_orange", () -> new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(3).saturationMod(3F).effect(new MobEffectInstance(MobEffects.LUCK, 2400, 1), 1.0F).effect(new MobEffectInstance(MobEffects.UNLUCK, 2400, 0), 1.0F).alwaysEat().build())));
    public static final RegistryObject<Item> SUMIKA_MEMORY = ITEMS.register("sumikas_memory", () ->  new Item(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<Item> ACTIVATOR = ITEMS.register("activator", () -> new Item(new Item.Properties().stacksTo(1)));

}
