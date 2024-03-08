package com.usagin.juicecraft.Init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class ParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);
    public static final RegistryObject<SimpleParticleType> SUGURIVERSE_LARGE = PARTICLES.register("suguriverse_large", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SLEEPY = PARTICLES.register("sleepy", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> DICEONE = PARTICLES.register("dice_one", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> DICETWO = PARTICLES.register("dice_two", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> DICETHREE = PARTICLES.register("dice_three", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> DICEFOUR = PARTICLES.register("dice_four", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> DICEFIVE = PARTICLES.register("dice_five", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> DICESIX = PARTICLES.register("dice_six", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GLITCH_PARTICLE = PARTICLES.register("glitchparticle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ALTE_ENERGY_PARTICLE = PARTICLES.register("alteenergyparticle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ALTE_LIGHTNING_PARTICLE = PARTICLES.register("altelightningparticle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ALTE_SELFDESTRUCT_PARTICLE = PARTICLES.register("alteselfdestructparticle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ALTE_IMPACT_PARTICLE = PARTICLES.register("alteimpactparticle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GENERIC_LIGHTNING_PARTICLE = PARTICLES.register("genericlightningparticle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> RANDOM_ENERGY_PARTICLE = PARTICLES.register("randomenergyparticle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ALTE_GUNFLASH = PARTICLES.register("altegunflashparticle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SORA_ENERGY_PARTICLE = PARTICLES.register("soraenergyparticle", () -> new SimpleParticleType(true));
}
