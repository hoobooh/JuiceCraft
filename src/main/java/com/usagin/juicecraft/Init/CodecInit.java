package com.usagin.juicecraft.Init;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.data.loot.ChestLootModifier;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraftforge.common.loot.IGlobalLootModifier.LOOT_CONDITIONS_CODEC;

public class CodecInit {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> CODREG = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, JuiceCraft.MODID);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> CHEST = CODREG.register("chest_codec", ChestLootModifier.CODEC);
}
