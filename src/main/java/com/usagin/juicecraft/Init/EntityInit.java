package com.usagin.juicecraft.Init;

import com.usagin.juicecraft.friends.Alte;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static final RegistryObject<EntityType<Sora>> SORA = ENTITIES.register("sora", () -> EntityType.Builder.of(Sora::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("sora"));

    public static final RegistryObject<EntityType<Alte>> ALTE = ENTITIES.register("alte", () -> EntityType.Builder.of(Alte::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("alte"));
}
