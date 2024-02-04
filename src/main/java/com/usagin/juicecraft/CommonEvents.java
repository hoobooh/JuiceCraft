package com.usagin.juicecraft;

import com.usagin.juicecraft.Init.EntityInit;
import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.client.models.alte.AlteEntityModel;
import com.usagin.juicecraft.client.models.sora.SoraEntityModel;
import com.usagin.juicecraft.client.renderer.entities.AlteEntityRenderer;
import com.usagin.juicecraft.client.renderer.entities.SoraEntityRenderer;
import com.usagin.juicecraft.friends.Alte;
import com.usagin.juicecraft.friends.Sora;
import com.usagin.juicecraft.network.*;
import com.usagin.juicecraft.particles.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = JuiceCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event){
        event.put(EntityInit.SORA.get(), Sora.getSoraAttributes().build());
        event.put(EntityInit.ALTE.get(), Alte.getAlteAttributes().build());
    }
    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityInit.SORA.get(),SoraEntityRenderer::new);
        event.registerEntityRenderer(EntityInit.ALTE.get(), AlteEntityRenderer::new);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(SoraEntityModel.LAYER_LOCATION, SoraEntityModel::createBodyLayer);
        event.registerLayerDefinition(AlteEntityModel.LAYER_LOCATION, AlteEntityModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event){
        Minecraft.getInstance().particleEngine.register(ParticleInit.SUGURIVERSE_LARGE.get(), SuguriverseParticleLarge.SugPartProvider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.SLEEPY.get(), SleepyParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.DICEONE.get(), SleepyParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.DICETWO.get(), SleepyParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.DICETHREE.get(), SleepyParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.DICEFOUR.get(), SleepyParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.DICEFIVE.get(), SleepyParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.DICESIX.get(), SleepyParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.GLITCH_PARTICLE.get(), GlitchParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.ALTE_ENERGY_PARTICLE.get(), EnergyParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.ALTE_LIGHTNING_PARTICLE.get(), AlteLightningParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.ALTE_SELFDESTRUCT_PARTICLE.get(), AlteSelfDestructParticle.SelfDestructProvider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.ALTE_GUNFLASH.get(), AlteGunFlashParticle.GunFlashProvider::new);

    }
    @SubscribeEvent
    public static void registerPacketHandler(FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            CombatSettingsPacketHandler.register();
            DialogueResultPacketHandler.register();
            SpecialDialoguePacketHandler.register();
            SetFarmingPacketHandler.register();
            SetWanderingPacketHandler.register();
            PlaySoundPacketHandler.register();
            UpdateSkillPacketHandler.register();
            ItemPickupPacketHandler.register();
        });
    }

}
