package com.usagin.juicecraft.Init;

import com.usagin.juicecraft.enemies.Harbinger;
import com.usagin.juicecraft.friends.Alte;
import com.usagin.juicecraft.friends.Sora;
import com.usagin.juicecraft.miscentities.SoraChargeEntity;
import com.usagin.juicecraft.miscentities.SoraShieldEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static final RegistryObject<EntityType<Sora>> SORA = ENTITIES.register("sora", () -> EntityType.Builder.of(Sora::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("sora"));
    public static final RegistryObject<EntityType<SoraShieldEntity>> SORA_SHIELD_ENTITY = ENTITIES.register("sora_shield", () -> EntityType.Builder.of(SoraShieldEntity::new, MobCategory.MISC).fireImmune().sized(0.5F, 0.5F).build("sora_shield"));
    public static final RegistryObject<EntityType<SoraChargeEntity>> SORA_CHARGE_ENTITY = ENTITIES.register("sora_charge", () -> EntityType.Builder.of(SoraChargeEntity::new, MobCategory.MISC).fireImmune().sized(0.5F, 0.5F).build("sora_charge"));

    public static final RegistryObject<EntityType<Alte>> ALTE = ENTITIES.register("alte", () -> EntityType.Builder.of(Alte::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("alte"));
    public static final RegistryObject<EntityType<Harbinger>> HARBINGER = ENTITIES.register("harbinger", () -> EntityType.Builder.of(Harbinger::new, MobCategory.MONSTER).fireImmune().sized(1.5F, 3F).build("harbinger"));
}
